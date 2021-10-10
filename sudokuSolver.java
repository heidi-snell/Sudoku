
import java.util.Arrays;

public class sudokuSolver {
	public static void main(String[] args) {

		// input given board to solve
		int[][] board = { { 0, 0, 0, 0, 0, 9, 0, 0, 7 }, { 0, 3, 0, 1, 0, 2, 6, 4, 9 }, { 0, 0, 0, 0, 5, 0, 1, 0, 0 },

				{ 0, 5, 0, 0, 7, 0, 4, 0, 0 }, { 2, 0, 0, 0, 0, 6, 0, 0, 0 }, { 1, 0, 8, 0, 0, 0, 0, 6, 0 },

				{ 0, 0, 0, 0, 0, 0, 8, 3, 0 }, { 7, 1, 0, 0, 0, 8, 0, 0, 2 }, { 0, 0, 9, 0, 0, 0, 0, 0, 0 } };

		// check validity of input
		if (hasRowDuplicates(board) == true) {
			System.out.println("Invalid input, duplicate in a row.");
			return;
		}
		if (hasColumnDuplicates(board) == true) {
			System.out.println("Invalid input, duplicate in a column.");
			return;
		}
		if (hasFamilyDuplicates(board) == true) {
			System.out.println("Invalid input, duplicate in a family.");
			return;
		}

		int[][] solvedBoard = backtrackingMethod(board);

		System.out.println("Found a solution!");
		display(solvedBoard);

	}

	static void display(int[][] board) {

		String printableBoard = new String();

		for (int row = 0; row < board.length; ++row) {
			for (int col = 0; col < board[0].length; ++col) {
				printableBoard = printableBoard + board[row][col] + " ";

				if (col == 2 || col == 5) {
					printableBoard += "| ";
				}
			}
			printableBoard += "\n";

			if (row == 2 || row == 5) {
				printableBoard += "\n";

			}
		}

		System.out.print("\n\n\n");
		System.out.print(printableBoard);

	}

	static boolean hasRowDuplicates(int[][] board) {
		// check for duplicates in rows

		// don't use spot 0 so that spots 1 to 9 align with values 1 to 9
		boolean[] valueExists = new boolean[10];
		Arrays.fill(valueExists, false);

		for (int row = 0; row < board[0].length; row++) {
			for (int col = 0; col < board.length; col++) {

				try {

					if (board[row][col] == 0) {
						// do nothing
					} else if (valueExists[board[row][col]] == true) {
						return true;
					} else {
						valueExists[board[row][col]] = true;
					}
				}

				catch (IndexOutOfBoundsException e) {
					System.out.println("error: index out of bounds exception!");
				}

			}
			Arrays.fill(valueExists, false);
		}
		return false;
	}

	static boolean hasColumnDuplicates(int[][] board) {
		// check for duplicates in columns

		boolean[] valueExists = new boolean[10];
		Arrays.fill(valueExists, false);

		for (int col = 0; col < board[0].length; col++) {
			for (int row = 0; row < board.length; row++) {
				if (board[row][col] == 0) {
					// do nothing
				} else if (valueExists[board[row][col]] == true) {
					return true;
				} else {
					valueExists[board[row][col]] = true;
				}
			}
			Arrays.fill(valueExists, false);
		}
		return false;
	}

	static boolean hasFamilyDuplicates(int[][] board) {
		// check for duplicates within each 3x3 family

		// dummy bloc for testing
		boolean[] valueExists = new boolean[10];
		Arrays.fill(valueExists, false);

		for (int rowSection = 0; rowSection <= 2; rowSection++) {
			for (int colSection = 0; colSection <= 2; colSection++) {
				for (int rowRemainder = 0; rowRemainder <= 2; rowRemainder++) {
					for (int colRemainder = 0; colRemainder <= 2; colRemainder++) {
						if (board[rowSection * 3 + rowRemainder][colSection * 3 + colRemainder] == 0) {
							// do nothing
						} else if (valueExists[board[rowSection * 3 + rowRemainder][colSection * 3
								+ colRemainder]] == true) {
							return true;
						} else {
							valueExists[board[rowSection * 3 + rowRemainder][colSection * 3 + colRemainder]] = true;
						}
					}
				}
				Arrays.fill(valueExists, false);
			}
		}
		return false;
	}

	// solve using backtracking algorithm
	static int[][] backtrackingMethod(int[][] board) {
		boolean[][] isGiven = new boolean[board.length][board.length];

		for (boolean[] row : isGiven) {
			Arrays.fill(row, false);
		}

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (board[row][col] != 0) {
					isGiven[row][col] = true; // set all given values to true
				}
			}
		}

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (isGiven[row][col] == true) {
					// do nothing
				} else {
					while (board[row][col] <= 9) {
						board[row][col] = board[row][col] + 1;

						display(board);

						if (board[row][col] > 9) { // if value is 10, backtrack
							board[row][col] = 0;

							if (col == 0) {
								--row;
								col = board.length - 1;
							} else {
								--col;
							}

							while (isGiven[row][col] == true) { // go back to last changeable cell
								if (col == 0) {
									--row;
									col = board.length - 1;
								} else {
									--col;
								}
							}

							if (col == 0) { // go back one extra cell
								--row;
								col = board.length - 1;
							} else {
								--col;
							}

							break;
						}

						if (hasRowDuplicates(board) == false && hasColumnDuplicates(board) == false
								&& hasFamilyDuplicates(board) == false) {
							break; // do nothing, this value is valid, leave while loop
						}

						else if (board[row][col] >= 9) { // if value is 9 and not valid, reset to 0 and backtrack
							board[row][col] = 0;
							if (col == 0) {
								--row;
								col = board.length - 1;
							} else {
								--col;
							}

							while (isGiven[row][col] == true) { // go back to last changeable cell
								if (col == 0) {
									--row;
									col = board.length - 1;
								} else {
									--col;
								}
							}

							if (col == 0) {
								--row;
								col = board.length - 1;
							} else {
								--col;
							}

							break;

						}
					}

				}
			}

		}

		return board;

	}
}
