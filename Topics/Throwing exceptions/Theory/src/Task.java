// You can experiment here, it won’t be checked

import java.util.Scanner;

public class Task {
    public static void main(String[] args) {
        // put your code here

        Scanner scanner = new Scanner(System.in);
        int inputFigure = scanner.nextInt();
        if (inputFigure < 1 || inputFigure > 7) {
            throw new IllegalArgumentException();
        }

        switch (inputFigure) {
            case 1:
                System.out.println("Mon");
                break;

            case 2:
                System.out.println("Tue");
                break;

            case 3:
                System.out.println("Wed");
                break;

            case 4:
                System.out.println("Thu");
                break;

            case 5:
                System.out.println("Fri");
                break;

            case 6:
                System.out.println("Sat");
                break;

            case 7:
                System.out.println("Sun");
                break;
        }

    }
}
