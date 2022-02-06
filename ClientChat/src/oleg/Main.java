package oleg;

import oleg.exceptions.ExitCommandException;
import oleg.uilayer.ConsoleInterface;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        ConsoleInterface consoleInterface = new ConsoleInterface();
        try {
            consoleInterface.entryDialog();
        }
        catch (ExitCommandException e) {
            exit(0);
        }
    }
}
