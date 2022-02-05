package oleg.exceptions;

public class ExitUI extends Exception {
    public String toString() {
        return "Вы нажали \"Exit\". Программа завершила работу";
    }
    //из ExitUI нужно обратиться к переменной ConsoleInterface.invalidResult и поменять её значение на false
    //Пытаюсь сделать переменную static, она не даёт
}
