import java.util.*;

public class TicTacToe {
    private static final char USER = 'X';
    private static final char AI = 'O';

    public static void main(String[] args) {
        char[][] board = createBoard();
        int count = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            printBoard(board);
            System.out.println();

            if (count % 2 == 0) {
                boolean validMove = false;
                while (!validMove) {
                    System.out.print("Enter where you want to place (row,col): ");
                    String input = scanner.nextLine();
                    try {
                        String[] parts = input.split(",");
                        int row = Integer.parseInt(parts[0].trim());
                        int col = Integer.parseInt(parts[1].trim());

                        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                            if (placeX(board, row, col)) {
                                validMove = true;
                                count++;
                            } else {
                                System.out.println("Invalid move. Try again.");
                            }
                        } else {
                            System.out.println("Invalid input. Enter correct row and column (0-2).");
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input format. Please enter row,col.");
                    }
                }
            } else {
                placeO(board);
                count++;
            }

            if (checkWin(board, USER)) {
                printBoard(board);
                System.out.println("Congratulations! User wins!");
                break;
            } else if (checkWin(board, AI)) {
                printBoard(board);
                System.out.println("AI wins!");
                break;
            } else if (count == 9) {
                printBoard(board);
                System.out.println("It's a draw!");
                break;
            }
        }

        scanner.close();
    }

    public static char[][] createBoard() {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '_';
            }
        }
        return board;
    }

    public static void printBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("-----");
            }
        }
    }

    public static boolean checkWin(char[][] board, char player) {
        int[][] winningCombinations = {
                {0, 0, 0, 1, 0, 2},  // Rows
                {1, 0, 1, 1, 1, 2},
                {2, 0, 2, 1, 2, 2},
                {0, 0, 1, 0, 2, 0},  // Columns
                {0, 1, 1, 1, 2, 1},
                {0, 2, 1, 2, 2, 2},
                {0, 0, 1, 1, 2, 2},  // Diagonals
                {0, 2, 1, 1, 2, 0}
        };

        for (int[] combo : winningCombinations) {
            if (board[combo[0]][combo[1]] == player &&
                board[combo[2]][combo[3]] == player &&
                board[combo[4]][combo[5]] == player) {
                return true;
            }
        }
        return false;
    }

    public static boolean placeX(char[][] board, int row, int col) {
        if (board[row][col] == '_') {
            board[row][col] = USER;
            return true;
        } else {
            return false;
        }
    }

    public static void placeO(char[][] board) {
        List<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    availableMoves.add(new int[]{i, j});
                }
            }
        }

        if (!availableMoves.isEmpty()) {
            Random rand = new Random();
            int[] move = availableMoves.get(rand.nextInt(availableMoves.size()));
            board[move[0]][move[1]] = AI;
        }
    }
}
