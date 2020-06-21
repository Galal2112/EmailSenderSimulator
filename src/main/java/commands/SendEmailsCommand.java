package commands;

import database.DatabaseBlock;
import database.DatabaseConnectionHandler;

import java.sql.ResultSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SendEmailsCommand implements ICommand {
    private final int totalNumberOfTasks = Runtime.getRuntime().availableProcessors() * 10;
    private final ExecutorService executorService = Executors.newFixedThreadPool(totalNumberOfTasks);
    private final CountDownLatch countDownLatch = new CountDownLatch(totalNumberOfTasks);
    private static AtomicInteger sProgressFinish = new AtomicInteger(0);

    @Override
    public void execute() {
        try {
            sProgressFinish.set(0);
            int perPage = (int) Math.ceil(1000_000/(double)totalNumberOfTasks);
            for (int i = 0; i < totalNumberOfTasks; i++) {
                executorService.execute(new SendEmailRunnable(i, perPage, countDownLatch));
            }
             countDownLatch.await();
             System.out.println("All emails sent");
        } catch (Exception e) {
            executorService.shutdownNow();
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Send 1M test mail";
    }

    private static class SendEmailRunnable implements Runnable {
        private final int page;
        private final CountDownLatch countDownLatch;
        private final int itemsPerPage;

        SendEmailRunnable(int page, int itemsPerPage, CountDownLatch countDownLatch) {
            this.page = page;
            this.itemsPerPage = itemsPerPage;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                DatabaseBlock block = stmt -> {
                    int offset = page * itemsPerPage;
                    ResultSet rs = stmt.executeQuery("SELECT * FROM users OFFSET " + offset + " FETCH NEXT " + itemsPerPage + " ROWS ONLY ");
                    while (rs.next()) {
                        Thread.sleep(500);
                        System.out.print("\r" + sProgressFinish.incrementAndGet() + " emails sent");
                    }
                };
                DatabaseConnectionHandler.sharedInstance.executeBlock(block);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
