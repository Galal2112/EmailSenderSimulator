package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public int readIntegerFromStdin(String text) {
        try {
            System.out.print(text);
            String input = reader.readLine();
            return Integer.parseInt(input);
        } catch (IOException | NumberFormatException e) {
            return readIntegerFromStdin(text);
        }
    }

}
