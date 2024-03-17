import java.util.Scanner;

public class BandOfTheHour {
        //Constant variables
        private static final int MAX_ROWS = 10;
        private static final int MAX_POSITIONS = 8;
        private static final double MIN_WEIGHT = 45.0;
        private static final double MAX_WEIGHT = 200.0;
        private static final double MAX_ROW_WEIGHT = 100.0;
        //Array to store weight of musicians
        private double[][] weights;
        //Array to store number of positions in each row
        private int[] rowPositions;
        //Number of rows
        private int numRows;
        //Scanner for input
        private Scanner scanner;

        /*
        Constructor used to initialize scanner and set up initial configuration
         */
        public BandOfTheHour() { //start of the BandOfTheHour method
            scanner = new Scanner(System.in);
            initializeStadium();
        } //end of the BandOfTheHour method

        /*
        Method used to initialize stadium by setting the number of rows and the number of
        positions within each row
        */

        private void initializeStadium() { //start of the initializeStadium method
            System.out.println("Welcome to the Band of the Hour");
            System.out.println("--------------------------------");
            do {
                System.out.print("Please enter number of rows: ");
                numRows = scanner.nextInt();
                if (numRows < 1 || numRows > MAX_ROWS)
                    System.out.println("ERROR: Out of range, try again");
            } while (numRows < 1 || numRows > MAX_ROWS);

            weights = new double[numRows][MAX_POSITIONS];
            rowPositions = new int[numRows];

            for (int i = 0; i < numRows; i++) {
                char rowLabel = (char) ('A' + i);
                int numPositions;
                do {
                    System.out.print("Please enter number of positions in row " + rowLabel + ": ");
                    numPositions = scanner.nextInt();
                    if (numPositions < 1 || numPositions > MAX_POSITIONS)
                        System.out.println("ERROR: Out of range, try again");
                } while (numPositions < 1 || numPositions > MAX_POSITIONS);
                rowPositions[i] = numPositions;
            }

            System.out.println();
        } //end of the initializeStadium method

        /*
        Prints the current assignment of musicians to the console.
        For each row, this method prints the row label, the weight of each musician in the row,
        the total row weight, and the average row weight.
        */

        public void printAssignment() { //start of the printAssignment method
            for (int i = 0; i < numRows; i++) {
                char rowLabel = (char) ('A' + i);
                System.out.print(rowLabel + ":");
                double totalRowWeight = 0;
                for (int j = 0; j < rowPositions[i]; j++) {
                    System.out.printf("%6.1f", weights[i][j]);
                    totalRowWeight += weights[i][j];
                }
                double avgRowWeight = rowPositions[i] > 0 ? totalRowWeight / rowPositions[i] : 0;
                System.out.printf("  [%6.1f, %8.1f]%n", totalRowWeight, avgRowWeight);
            }
        } //end of the printAssignment method

        /*
         Adds a musician to a specified position in a row. Prompts for the row, position,
         and weight of the musician, validating each input against constraints.
         If a musician is added, the method updates to reflect this change.
         */

        public void addMusician() { //start of the addMusician method
            System.out.print("Please enter row letter: ");
            char row = scanner.next().toUpperCase().charAt(0);
            int rowIndex = row - 'A';
            if (rowIndex < 0 || rowIndex >= numRows) {
                System.out.println("ERROR: Out of range, try again");
                return;
            }

            System.out.print("Please enter position number (1 to " + rowPositions[rowIndex] + "): ");
            int position = scanner.nextInt();
            if (position < 1 || position > rowPositions[rowIndex]) {
                System.out.println("ERROR: Out of range, try again");
                return;
            }

            if (weights[rowIndex][position - 1] != 0) {
                System.out.println("ERROR: There is already a musician there.");
                return;
            }

            System.out.print("Please enter weight (" + MIN_WEIGHT + " to " + MAX_WEIGHT + "): ");
            double weight = scanner.nextDouble();
            if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
                System.out.println("ERROR: Out of range, try again");
                return;
            }
            // Calculate total weight before adding new weight
            double rowWeight = 0;
            for (int i = 0; i < rowPositions[rowIndex]; i++) {
                rowWeight += weights[rowIndex][i];
            }
            if (rowWeight + weight > MAX_ROW_WEIGHT * rowPositions[rowIndex]) {
                System.out.println("ERROR: That would exceed the average weight limit.");
                return;
            }

            weights[rowIndex][position - 1] = weight;
            System.out.println("****** Musician added.");
        } //end of the addMusician method

        /*
        Removes a musician from a specific position in a row. The row and position are removed.
        If the position is occupied, the method updates to mark the position as vacant.
        */

        public void removeMusician() { //start of the removeMusician method
            System.out.print("Please enter row letter: ");
            char row = scanner.next().toUpperCase().charAt(0);
            int rowIndex = row - 'A';
            if (rowIndex < 0 || rowIndex >= numRows) {
                System.out.println("ERROR: Out of range, try again");
                return;
            }

            System.out.print("Please enter position number (1 to " + rowPositions[rowIndex] + "): ");
            int position = scanner.nextInt();
            if (position < 1 || position > rowPositions[rowIndex]) {
                System.out.println("ERROR: Out of range, try again");
                return;
            }

            if (weights[rowIndex][position - 1] == 0) {
                System.out.println("ERROR: That position is vacant.");
                return;
            }

            weights[rowIndex][position - 1] = 0;
            System.out.println("****** Musician removed.");
        } //end of the removeMusician method

        /*
        Starts the application, allowing the addition or removal of musicians, print the current assignment,
        or exit the program. Handles user input and calls the appropriate methods based on the choices of input.
        */
        public void run() { //start of the run method
            char choice;
            do {
                System.out.print("\n(A)dd, (R)emove, (P)rint, e(X)it: ");
                choice = scanner.next().toUpperCase().charAt(0);
                switch (choice) {
                    case 'A':
                        addMusician();
                        break;
                    case 'R':
                        removeMusician();
                        break;
                    case 'P':
                        printAssignment();
                        break;
                    case 'X':
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("ERROR: Invalid option, try again");
                }
            } while (choice != 'X');
        } //end of the run method

        public static void main(String[] args) { //start of the main method
            BandOfTheHour bandOfTheHour = new BandOfTheHour();
            bandOfTheHour.run();
        } //end of the main method

    } //end of the BandOfTheHour class