import java.util.Arrays;
import java.util.Scanner;
public class TicTacToe {

    Scanner scan = new Scanner(System.in);
    String[][] field = {
            {"---------"},
            {"|", " ", " ", " ", "|"},
            {"|", " ", " ", " ", "|"},
            {"|", " ", " ", " ", "|"},
            {"---------"}
    };

    public static void main(String[] args) {
        TicTacToe main = new TicTacToe();

        String cordX, cordY;
        String turn = "X";
        String info;
        boolean gameInProgress = true;

        /*
        Step 1 and 2 - get the sequence and create field.
         */
        System.out.print("Enter cells: ");
        main.fillFieldWithSequence(main.scan.nextLine().replace("_"," ").split(""));
        main.printField();

        /*
        Loop until the game ends.
         */
        while (gameInProgress) {
            boolean correctCords = false;
            while (!correctCords) {

                /*
                Step 3 - ask user about his next move.
                 */
                System.out.print("Enter the coordinates: ");
                cordX = main.scan.next();
                cordY = main.scan.next();

                /*
                Check if the user input is correct (is number) and change coordinates
                from array coordinates x, y (1,1 is up left and goes to right)
                to default coordinates a, b (1,1 is bottom left and and goes up).
                 */
                if (cordX.matches("\\d+") || cordY.matches("\\d+")) {
                    int x = Integer.parseInt(cordX); // 1
                    int y = Integer.parseInt(cordY); // 3
                    int a;
                    int b = x;
                    switch (y) {
                        case 1:
                            a = 3;
                            break;
                        case 2:
                            a = 2;
                            break;
                        case 3:
                            a = 1;
                            break;
                        default:
                            a = 0;
                            break;
                    }

                    /*
                    Step 4 - check exceptions:
                        a) 1 <= x (and y) <= 3,
                        b) if cell is occupied then reject,
                        c) count Xs and Os - if number of X is the same as O, then turn is X,
                           if X = O + 1 then turn is O, else check the field state.
                     */
                    if (x < 1 || x > 3 || y < 1 || y > 3) {
                        System.out.println("Coordinates should be from 1 to 3!");
                    } else if (main.field[a][b].equals("X") || main.field[a][b].equals("O")) {
                        System.out.println("The cell is occupied! Choose another one!");
                    } else {
                        //main.field[a][b] = turn;
                        //turn = (turn.equals("X")) ? "O" : "X";
                        int xQuantity = 0;
                        int oQuantity = 0;
                        for (String[] row : main.field) {
                            for (String cell : row) {
                                if (cell.equals("X")) {
                                    xQuantity++;
                                } else if (cell.equals("O")) {
                                    oQuantity++;
                                }
                            }
                        }
                        if (xQuantity == oQuantity) {
                            turn = "X";
                        } else if (xQuantity == oQuantity + 1) {
                            turn = "O";
                        } else {
                            main.checkFieldState();
                        }
                        /*
                        Step 5 - Cords correct > insert symbol and print field
                         */
                        correctCords = true;
                        main.field[a][b] = turn;
                        main.printField();
                    }

                } else {
                    System.out.println("You should enter numbers!");
                }
            }

            /*
            Step 6 - Check the state. If game is not finished loop repeats again.
            If any other state - print state and change gameInProgress to false which
            will stop the loop.
             */
            info = main.checkFieldState();
            if (!info.equals("Game not finished")) {
                System.out.println(info);
                gameInProgress = false;
            }

        }

    }

    void fillFieldWithSequence(String[] input) {
        int ind = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                field[i][j] = input[ind];
                ind++;
            }
        }
    }

    void printField() {
        for (String[] row : field) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    String checkFieldState() {
        boolean xInRow = false;
        boolean oInRow = false;
        boolean emptySpaces = false;
        int xQuantity = 0;
        int oQuantity = 0;

        /*
        Checks if any empties and counts X and O
         */
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++){
                switch (field[i][j]) {
                    case " ":
                        emptySpaces = true;
                        break;
                    case "X":
                        xQuantity++;
                        break;
                    case "O":
                        oQuantity++;
                    default:
                        break;
                }
            }
        }

        /*
        Checks if any 3 in a row
         */
        for (int i = 1; i <= 3; i++) {
            if (Arrays.toString(field[i]).equals("[|, X, X, X, |]")) {
                xInRow = true;
            }
            if (Arrays.toString(field[i]).equals("[|, O, O, O, |]")) {
                oInRow = true;
            }
            if (field[1][i].equals("X") && field[2][i].equals("X") && field[3][i].equals("X")) {
                xInRow = true;
            }
            if (field[1][i].equals("O") && field[2][i].equals("O") && field[3][i].equals("O")) {
                oInRow = true;
            }
        }
        if (field[1][1].equals("X") && field[2][2].equals("X") && field[3][3].equals("X")) {
            xInRow = true;
        }
        if (field[1][3].equals("X") && field[2][2].equals("X") && field[3][1].equals("X")) {
            xInRow = true;
        }
        if (field[1][1].equals("O") && field[2][2].equals("O") && field[3][3].equals("O")) {
            oInRow = true;
        }
        if (field[1][3].equals("O") && field[2][2].equals("O") && field[3][1].equals("O")) {
            oInRow = true;
        }

        /*
        Summarise all conditions and returns state of the field
         */
        if (xInRow && oInRow || Math.abs(xQuantity - oQuantity) > 1) {
            return "Impossible";
        } else if (!xInRow && !oInRow && emptySpaces) {
            return "Game not finished";
        } else if (!xInRow && !oInRow && !emptySpaces) {
            return "Draw";
        } else if (xInRow) {
            return "X wins";
        } else if (oInRow) {
            return "O wins";
        } else {
            return "Something goes wrong!";
        }
    }
}