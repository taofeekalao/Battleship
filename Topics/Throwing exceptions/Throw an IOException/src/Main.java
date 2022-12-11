import java.io.IOException;
import java.util.Scanner;

public class Main {

    // change this method
    public static void method() {
        Scanner scanner = new Scanner(System.in);
        String number = String.valueOf(scanner.nextInt());
    }

    /* Do not change code below */
    public static void main(String[] args) {
        try {
            method();
        } catch (Exception e) {
            System.out.println(e.getClass());
        }
    }
}