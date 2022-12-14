package battleship;

import java.util.Objects;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static final int WAR_WIDTH = 11;
    static final int WAR_LENGTH = 11;
    static final int INITIATE_CHARACTER = 64;
    static final String[][] battleFieldPlayerA = new String[WAR_WIDTH][WAR_LENGTH];
    static final String[][] battleFieldPlayerB = new String[WAR_WIDTH][WAR_LENGTH];
    static int[][] enteredCoordinates = new int[6][6];

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

    /**
     *  Main method loading the board and starting the game play.
     * @param args  This is the arguments list to kick-start the program.
     */
    public static void main(String[] args) {
        setupGameBoard();
        setupGame();
        startGame();
    }

    /**
     *  This method runs the game after the board has been loaded with ships and their position.
     */
    private static void startGame() {
        System.out.println();
        System.out.println("The game starts!");
        printGameBoard(battleFieldPlayerB);
        System.out.println();
        System.out.println("Take a shot!");
        receiveCoordinates(false);
    }

    /**
     * This method sets up the game board.
     */
    private static void setupGameBoard() {
        for (int i = 0; i < WAR_WIDTH; i++) {
            battleFieldPlayerA[i][0] = (i == 0 ? "  " : (char) (INITIATE_CHARACTER + i) + " ");
            battleFieldPlayerB[i][0] = (i == 0 ? "  " : (char) (INITIATE_CHARACTER + i) + " ");
            for (int j = 1; j < WAR_LENGTH; j++) {
                battleFieldPlayerA[i][j] = (i == 0 ? j + "" : "~");
                battleFieldPlayerB[i][j] = (i == 0 ? j + "" : "~");
            }
        }
    }

    /**
     *  This method is used to print the battlefield
     * @param battleField   It takes a two-dimensional array representing the battlefield to be printed.
     */
    private static void printGameBoard(String[][] battleField) {
        for (int i = 0; i < WAR_WIDTH; i++) {
            System.out.print(battleField[i][0]);
            for (int j = 1; j < WAR_LENGTH; j++) {
                System.out.print(battleField[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * This method sets up the game board with ships and their locations on the battlefield.
     */
    private static void setupGame() {
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        airCraftCarrier = true;
        receiveCoordinates(true);
        printGameBoard(battleFieldPlayerA);

        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        battleShip = true;
        receiveCoordinates(true);
        printGameBoard(battleFieldPlayerA);

        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        submarine = true;
        receiveCoordinates(true);
        printGameBoard(battleFieldPlayerA);

        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        cruiser = true;
        receiveCoordinates(true);
        printGameBoard(battleFieldPlayerA);

        System.out.println("Enter the coordinates of the Destroyer (2 cells)");
        destroyer = true;
        receiveCoordinates(true);
        printGameBoard(battleFieldPlayerA);
    }

    /**
     * This method receives the coordinates and manages the routing between
     * if system is to receive a single or double coordinates depending on the state of the game.
     * Receives double coordinates during board setup and receives single coordinate during game play.
     * @param doubleSingleCoordinate
     */
    private static void receiveCoordinates(boolean doubleSingleCoordinate) {
        if (readCoordinate(doubleSingleCoordinate)) {
            while (!plotCoordinates(firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY)) {
                readCoordinate(true);
            }
        } else {
            while (!plotCoordinates(firstCoordinateX, firstCoordinateY))
                if (!gameEnded()) {
                    readCoordinate(false);
                } else {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    break;
                }
        }
    }

    /**
     * This checks if all ships on the battlefield are sunk and determines
     * if the game is still on or has ended based in this result.
     * @return It returns a boolean value using above logic.
     */
    private static boolean gameEnded() {
        boolean gameEndStatus = true;
        for (int i = 0; i < 4; i++) {
            if (enteredCoordinates[i][5] == 0) {
                gameEndStatus = false;
                break;
            } else gameEndStatus = true;
        }
        return gameEndStatus;
    }

    /**
     *  This method does the actual reading of coordinates from the stream.
     * @param doubleSingleCoordinate    It takes a parameter indicating
     *                                  the number of coordinates to read from the stream.
     * @return Returns a boolean based on the success or the failure of the reading.
     */
    private static boolean readCoordinate(boolean doubleSingleCoordinate) {
        boolean coordinateDouble;
        System.out.println();
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
        System.out.println();
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

    /**
     * @param firstCoordinate  Is the int representation for the row. It is between uppercase
     *                         CHARACTERS A to J. This is converted to integer and must lie
     *                         between 65 and 74. The range represent uppercase CHARACTER A
     *                         to uppercase CHARACTER J. This is achieved by subtracting 64
     *                         from final integer representation.
     */
    private static void breakCoordinates(String firstCoordinate) {
        firstCoordinateX = firstCoordinate.charAt(0) - INITIATE_CHARACTER;
        firstCoordinateY = Integer.parseInt(firstCoordinate.substring(1));
    }

    /**
     *  This does not take any parameter. It validates the length of the coordinates received based on different ship size and prompts appropriate message depending
     *  on the coordinates received and the state of the game board at every instance.
     *
     *  It calls check length to know how long the coordinates are.
     */
    private static void validateLengthOfCoordinates() {
        boolean lengthStatus = false;
        if (airCraftCarrier) {
            lengthStatus = checkLength(AIRCRAFT_CARRIER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Aircraft Carrier! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(AIRCRAFT_CARRIER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            enteredCoordinates[0] = new int[]{1, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY, 0};
            airCraftCarrier = false;
        } else if (battleShip) {
            lengthStatus = checkLength(BATTLESHIP_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Battleship! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(BATTLESHIP_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            enteredCoordinates[1] = new int[]{2, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY, 0};
            battleShip = false;
        } else if (submarine) {
            lengthStatus = checkLength(SUBMARINE_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Submarine! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(SUBMARINE_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            enteredCoordinates[2] = new int[]{3, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY, 0};
            submarine = false;
        } else if (cruiser) {
            lengthStatus = checkLength(CRUISER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Cruiser! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(CRUISER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            enteredCoordinates[3] = new int[]{4, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY, 0};
            cruiser = false;
        } else if (destroyer) {
            lengthStatus = checkLength(DESTROYER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            while (!lengthStatus) {
                System.out.println("Error! Wrong length of the Destroyer! Try again:");
                receiveCoordinates(true);
                lengthStatus = checkLength(DESTROYER_SIZE, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY);
            }
            enteredCoordinates[4] = new int[]{5, firstCoordinateX, firstCoordinateY, secondCoordinateX, secondCoordinateY, 0};
            destroyer = false;
        }
    }

    /**
     * @param size  Is the expected size of the length to be plotted on the board.
     * @param firstCoordinateX  First X coordinate value
     * @param firstCoordinateY Second X coordinate value
     * @param secondCoordinateX First Y coordinate value
     * @param secondCoordinateY Second Y coordinate value
     * @return It returns a boolean value true or false depending on feasibility of the coordinates given.
     */
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

    /**
     *
     * @param firstCoordinateX  First X coordinate value
     * @param firstCoordinateY  Second X coordinate value
     * @param secondCoordinateX First Y coordinate value
     * @param secondCoordinateY Second Y coordinate value
     * @return  It returns a boolean value true or false depending on feasibility of the coordinates given
     */
    private static boolean plotCoordinates(int firstCoordinateX, int firstCoordinateY, int secondCoordinateX, int secondCoordinateY) {
        boolean fixPointStatus = false;
        if (firstCoordinateX == secondCoordinateX) {
            fixPointStatus = fixPoint(firstCoordinateY, secondCoordinateY, firstCoordinateX, 'X');
        } else if (firstCoordinateY == secondCoordinateY) {
            fixPointStatus = fixPoint(firstCoordinateX, secondCoordinateX, firstCoordinateY, 'Y');
        }
        return fixPointStatus;
    }

    /**
     * @param firstCoordinateX  First X coordinate value
     * @param firstCoordinateY  First Y coordinate value
     * @return  It returns a boolean value true or false depending on feasibility of the coordinates given
     */
    private static boolean plotCoordinates(int firstCoordinateX, int firstCoordinateY) {
        boolean fixPointStatus = false;
        return fixPointStatus = fixPoint(firstCoordinateX, firstCoordinateY);
    }

    /**
     * @param firstCoordinate   First X coordinate value
     * @param secondCoordinate  First Y coordinate value
     * @param fixedAxisValue    Integer showing the value to be fixed/pivot of the coordinates
     * @param fixedAxis         String showing the pivot axis
     * @return                  It returns a boolean value true or false depending on success of the plot
     */
    private static boolean fixPoint(int firstCoordinate, int secondCoordinate, int fixedAxisValue, char fixedAxis) {
        int startIndex;
        int endIndex;
        startIndex = firstCoordinate;
        endIndex = secondCoordinate;
        if (firstCoordinate > secondCoordinate) {
            startIndex = secondCoordinate;
            endIndex = firstCoordinate;
        }

        if (endIndex < battleFieldPlayerA.length - 1 && startIndex > 1) {
            if (fixedAxis == 'X') {
                if (Objects.equals(battleFieldPlayerA[fixedAxisValue][startIndex - 1], "O") || Objects.equals(battleFieldPlayerA[fixedAxisValue][endIndex + 1], "O")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }

            if (fixedAxis == 'Y') {
                if (Objects.equals(battleFieldPlayerA[startIndex - 1][fixedAxisValue], "O") || Objects.equals(battleFieldPlayerA[endIndex + 1][fixedAxisValue], "O")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
        }


        for (int i = startIndex; i <= endIndex; i++) {
            if (fixedAxis == 'X') {
                battleFieldPlayerA[fixedAxisValue][i] = "O";
            }

            if (fixedAxis == 'Y') {
                battleFieldPlayerA[i][fixedAxisValue] = "O";
            }
        }
        return true;
    }

    /**
     * @param xCoordinate   First X coordinate value
     * @param yCoordinate   First Y coordinate value
     * @return              It returns a boolean value true or false depending on success of the plot
     */
    private static boolean fixPoint(int xCoordinate, int yCoordinate) {
        if (xCoordinate < 1 || xCoordinate > 10 || yCoordinate < 1 || yCoordinate > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        if (Objects.equals(battleFieldPlayerA[xCoordinate][yCoordinate], "O") || Objects.equals(battleFieldPlayerA[xCoordinate][yCoordinate], "X")) {
            battleFieldPlayerA[xCoordinate][yCoordinate] = "X";
            battleFieldPlayerB[xCoordinate][yCoordinate] = "X";
            printGameBoard(battleFieldPlayerB);
            if (sankShip()) {
                System.out.println("You sank a ship! Specify a new target:");
            } else {
                System.out.println("You hit a ship! Try again:");
            }
            return false;
        } else if (Objects.equals(battleFieldPlayerA[xCoordinate][yCoordinate], "~")) {
            battleFieldPlayerA[xCoordinate][yCoordinate] = "M";
            battleFieldPlayerB[xCoordinate][yCoordinate] = "M";
            printGameBoard(battleFieldPlayerB);
            System.out.println("You missed! Try again:");
        }
        return false;
    }

    /**
     *  Method to determine state of ships on the battlefield. Determines if a ship on the field is sank or not.
     * @return  It returns a boolean value true or false depending on state of the board.
     */
    private static boolean sankShip() {
        boolean sankAnyShip = false;
        for (int i = 0; i < 4; i++) {
            boolean completeShipHit = false;

            int x1 = enteredCoordinates[i][1];
            int y1 = enteredCoordinates[i][2];
            int x2 = enteredCoordinates[i][3];
            int y2 = enteredCoordinates[i][4];

            if (enteredCoordinates[i][5] == 1) {
                continue;
            }

            /*
                Check order of loop indices
             */
            int start;
            int end;

            if (x1 == x2) {
                start = y1;
                end = y2;
                if (y1 > y2) {
                    start = y2;
                    end = y1;
                }

                for (int k = start; k <= end; k++) {
                    if (!Objects.equals(battleFieldPlayerA[x1][k], "X")) {
                        completeShipHit = false;
                        break;
                    }
                    completeShipHit = true;
                }
                if (completeShipHit && enteredCoordinates[i][5] == 0) {
                    enteredCoordinates[i][5] = 1;
                    sankAnyShip = true;
                    break;
                }
            }

            if (y1 == y2) {
                start = x1;
                end = x2;
                if (x1 > x2) {
                    start = x2;
                    end = x1;
                }
                for (int k = start; k <= end; k++) {
                    if (!Objects.equals(battleFieldPlayerA[k][y1], "X")) {
                        completeShipHit = false;
                        break;
                    }
                    completeShipHit = true;
                }
                if (completeShipHit && enteredCoordinates[i][5] == 0) {
                    enteredCoordinates[i][5] = 1;
                    sankAnyShip = true;
                    break;
                }
            }

        }
        return sankAnyShip;
    }
}