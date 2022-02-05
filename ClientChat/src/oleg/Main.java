package oleg;

import oleg.exceptions.ExitUI;
import oleg.uilayer.ConsoleInterface;

public class Main {

    public static void main(String[] args) throws ExitUI {
        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.entryDialog();
    }
}
