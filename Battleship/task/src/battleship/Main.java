package battleship;

import java.util.Objects;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static final int WAR_WIDTH = 11;
    static final int WAR_LENGTH = 11;
    static final int INITIATE_CHARACTER = 64;
    static final String[][] battleField = new String[WAR_WIDTH][WAR_LENGTH];

    static final int AIRCRAFT_CARRIER_SIZE = 5;
    static final int BATTLESHIP_SIZE = 4;
    static final int SUBMARINE_SIZE = 3;
    static final int CRUISER_SIZE = 3;
    static final int DESTROYER_SIZE = 2;

    static boolean airCraftCarrier = false;
    static boolean battleShip = false;
    static boolean submarine = false;
    static boolean cruiser = false;
    static boolean destroyer = false;

    static String firstCoordinate;
    static String secondCoordinate;
    static int firstCoordinateY;
    static int firstCoordinateX;
    static int secondCoordinateY;
    static int secondCoordinateX;

    public static void main(String[] args) {
        setupGameBoard();
        setupGame();
        startGame();
    }

    private static void startGame() {
        System.out.println("The game starts!");
        printGameBoard();
        System.out.println("Take a shot!");
        receiveCoordinates(false);
        printGameBoard();
    }

    private static void setupGameBoard() {
        for (int i = 0; i < WAR_WIDTH; i++) {
            battleField[i][0] = (i == 0 ? " " : (char) (INITIATE_CHARACTER + i) + " ");
            for (int j = 1; j < WAR_LENGTH; j++) {
                battleField[i][j] = (i == 0 ? j + " " : "~ ");
            }
        }
    }

    private static void printGameBoard() {
        for (int i = 0; i < WAR_WIDTH; i++) {
            System.out.print(battleField[i][0]);
            for (int j = 1; j < WAR_LENGTH; j++) {
                System.out.print(battleField[i][j]);
            }
            System.out.println();
        }
    }

    private static void setupGame() {
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        airCraftCarrier = true;
        receiveCoordinates(true);
        printGameBoard();

        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        battleShip = true;
        receiveCoordinates(true);
        printGameBoard();

        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        submarine = true;
        receiveCoordinates(true);
        printGameBoard();

        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        cruiser = true;
        receiveCoordinates(true);
        printGameBoard();

        System.out.println("Enter the coordinates of the Destroyer (2 cells)");
        destroyer = true;
        receiveCoordinates(true);
        printGameBoard();
    }

    private static void receiveCoordinates(boolean doubleSingleCoordinate) {
        if (readCoordinate(doubleSingleCoordinate)) {
            while (!plotCoordinates(firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY)) {
                readCoordinate(true);
            }
        } else {
            while (!plotCoordinates(firstCoordinateX, firstCoordinateY))
                readCoordinate(false);
        }
    }

    private static boolean readCoordinate(boolean doubleSingleCoordinate) {
        boolean coordinateDouble;
        firstCoordinate = scanner.next();
        if (doubleSingleCoordinate) {
            secondCoordinate = scanner.next();
            breakCoordinates(firstCoordinate, secondCoordinate);
            coordinateDouble = true;
            validateLengthOfCoordinates();
        } else {
            breakCoordinates(firstCoordinate);
            coordinateDouble = false;
        }
        return coordinateDouble;
    }

    /**
     * @param firstCoordinate  is the int representation for the row. It is between uppercase
     *                         CHARACTERS A to J. This is converted to integer and must lie
     *                         between 65 and 74. The range represent uppercase CHARACTER A
     *                         to uppercase CHARACTER J. This is achieved by subtracting 64
     *                         from final integer representation.
     * @param secondCoordinate is the representation for the columns. It is between integers
     *                         1 and 10.
     */
    private static void breakCoordinates(String firstCoordinate, String secondCoordinate) {
        firstCoordinateX = firstCoordinate.charAt(0) - INITIATE_CHARACTER;
        firstCoordinateY = Integer.parseInt(firstCoordinate.substring(1));
        secondCoordinateX = secondCoordinate.charAt(0) - INITIATE_CHARACTER;
        secondCoordinateY = Integer.parseInt(secondCoordinate.substring(1));

        if (firstCoordinateX < 1 || firstCoordinateX > 10) {
            System.out.println("Error! X out of range");
        }
        if (firstCoordinateY < 1 || firstCoordinateY > 10) {
            System.out.println("Error! Y out of range");
        }
        if (secondCoordinateX < 1 || secondCoordinateX > 10) {
            System.out.println("Error! X out of range");
        }
        if (secondCoordinateY < 1 || secondCoordinateY > 10) {
            System.out.println("Error! Y out of range");
        }
    }

    private static void breakCoordinates(String firstCoordinate) {
        firstCoordinateX = firstCoordinate.charAt(0) - INITIATE_CHARACTER;
        firstCoordinateY = Integer.parseInt(firstCoordinate.substring(1));
    }

    private static void validateLengthOfCoordinates() {
        boolean lengthStatus = false;
        if (airCraftCarrier) {
            lengthStatus = checkLength(AIRCRAFT_CARRIER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Aircraft Carrier! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(AIRCRAFT_CARRIER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            airCraftCarrier = false;
        } else if (battleShip) {
            lengthStatus = checkLength(BATTLESHIP_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Battleship! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(BATTLESHIP_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            battleShip = false;
        } else if (submarine) {
            lengthStatus = checkLength(SUBMARINE_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Submarine! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(SUBMARINE_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            submarine = false;
        } else if (cruiser) {
            lengthStatus = checkLength(CRUISER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Cruiser! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(CRUISER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            cruiser = false;
        } else if (destroyer) {
            lengthStatus = checkLength(DESTROYER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Destroyer! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(DESTROYER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            destroyer = false;
        }
    }

    private static boolean checkLength(int size, int firstCoordinateX, int firstCoordinateY, int secondCoordinateX, int secondCoordinateY) {
        if (firstCoordinateX == secondCoordinateX && size != (abs(firstCoordinateY - secondCoordinateY) + 1)) {
            return false;
        } else if (firstCoordinateY == secondCoordinateY && size != (abs(firstCoordinateX - secondCoordinateX) + 1)) {
            return false;
        } else if (firstCoordinateY != secondCoordinateY && firstCoordinateX != secondCoordinateX) {
            return false;
        }
        return true;
    }

    private static boolean plotCoordinates(int firstCoordinateX, int firstCoordinateY, int secondCoordinateX, int secondCoordinateY) {
        boolean fixPointStatus = false;
        if (firstCoordinateX == secondCoordinateX) {
            fixPointStatus = fixPoint(firstCoordinateY, secondCoordinateY, firstCoordinateX, 'X');
        } else if (firstCoordinateY == secondCoordinateY) {
            fixPointStatus = fixPoint(firstCoordinateX, secondCoordinateX, firstCoordinateY, 'Y');
        }
        return fixPointStatus;
    }

    private static boolean plotCoordinates(int firstCoordinateX, int firstCoordinateY) {
        boolean fixPointStatus = false;
        return fixPointStatus = fixPoint(firstCoordinateX, firstCoordinateY);
    }

    private static boolean fixPoint(int firstCoordinate, int secondCoordinate, int fixedAxisValue, char fixedAxis) {
        int startIndex;
        int endIndex;
        startIndex = firstCoordinate;
        endIndex = secondCoordinate;
        if (firstCoordinate > secondCoordinate) {
            startIndex = secondCoordinate;
            endIndex = firstCoordinate;
        }

        if (endIndex < battleField.length - 1 && startIndex > 1) {
            if (fixedAxis == 'X') {
                if (Objects.equals(battleField[fixedAxisValue][startIndex - 1], "O ") || Objects.equals(battleField[fixedAxisValue][endIndex + 1], "O ")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }

            if (fixedAxis == 'Y') {
                if (Objects.equals(battleField[startIndex - 1][fixedAxisValue], "O ") || Objects.equals(battleField[endIndex + 1][fixedAxisValue], "O ")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
        }


        for (int i = startIndex; i <= endIndex; i++) {
            if (fixedAxis == 'X') {
                battleField[fixedAxisValue][i] = "O ";
            }

            if (fixedAxis == 'Y') {
                battleField[i][fixedAxisValue] = "O ";
            }
        }
        return true;
    }

    private static boolean fixPoint(int xCoordinate, int yCoordinate) {
        if (xCoordinate < 1 || xCoordinate > 10 || yCoordinate < 1 || yCoordinate > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        if (Objects.equals(battleField[xCoordinate][yCoordinate], "O ") ) {
            battleField[xCoordinate][yCoordinate] = "X ";
            System.out.println("You hit a ship!:");
            return true;
        } else {
            battleField[xCoordinate][yCoordinate] = "M ";
            System.out.println("You missed!");
            return true;
        }
    }
}