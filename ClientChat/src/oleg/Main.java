package oleg;

import oleg.uilayer.ConsoleInterface;

public class Main {

    public static void main(String[] args) {
        //Баг: при неверных данных авторизации, программа завершается, а должна заново спросить ЛиП
        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.entryDialog();

        //написал метод isExit, но он не работает, в случае, если мы выбрали авторизацию или регистрацию.
        //Видимо, break в 17-й строке завершает только код блока 15-18 строка класса ConsoleInterface,
        // но как расостранить break во вне блока кода не знаю
    }
}
