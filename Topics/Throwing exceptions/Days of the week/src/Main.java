import java.util.*;

public class Main {

    public static String getDayOfWeekName(int number) {
        // write your code here
        // You can experiment here, it won’t be checked
        String dayOfWeek = "";
        switch (number) {
            case 1:
                dayOfWeek = "Mon";
                break;

            case 2:
                dayOfWeek = "Tue";
                break;

            case 3:
                dayOfWeek = "Wed";
                break;

            case 4:
                dayOfWeek = "Thu";
                break;

            case 5:
                dayOfWeek = "Fri";
                break;

            case 6:
                dayOfWeek = "Sat";
                break;

            case 7:
                dayOfWeek = "Sun";
                break;

            default:
                throw new IllegalArgumentException();
        }
        return dayOfWeek;
    }


    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayNumber = scanner.nextInt();
        try {
            System.out.println(getDayOfWeekName(dayNumber));
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }
}