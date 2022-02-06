package oleg.exceptions;

public class ExitCommandException extends Exception {
    public String toString() {
        return "Вы нажали \"Exit\". Программа завершила работу";
    }
}
