package sudoku;

import java.util.Arrays;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// input given board here
		int[][] board = { { 0,0,0, 0,0,9, 0,0,7 },
				{ 0,3,0, 1,0,2, 6,4,9 }, 
				{ 0,0,0, 0,5,0, 1,0,0 },

			        { 0,5,0, 0,7,0, 4,0,0 }, 
			        { 2,0,0, 0,0,6, 0,0,0 }, 
			        { 1,0,8, 0,0,0, 0,6,0 },

				{ 0,0,0, 0,0,0, 8,3,0 }, 
				{ 7,1,0, 0,0,8, 0,0,2 }, 
				{ 0,0,9, 0,0,0, 0,0,0 } };
 
		// check validity of input
		if (checkRows(board) == false) {
			System.out.println("Invalid input, duplicate in a row.");
			return;
		}
		if (checkColumns(board) == false) {
			System.out.println("Invalid input, duplicate in a column.");
			return;
		}
		if (checkFamily(board) == false) {
			System.out.println("Invalid input, duplicate in a family.");
			return;
		}

		int[][] solved = backtrack(board);

		// print board
		display(solved);

	}

	static void display(int[][] board) {

		String out = new String();

		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[0].length; ++j) {
				out = out + board[i][j] + " ";

				if (j == 2 || j == 5) {
					out = out + "| ";
				}
			}
			out = out + "\n";

			if (i == 2 || i == 5) {
				out = out + "\n";

			}
		}

		System.out.print("\n\n\n");
		System.out.print(out);

	}

	static boolean checkRows(int[][] board) {
		// check for duplicates in rows

		// dummy row for testing
		// don't use spot 0 so that spots 1 to 9 align with values 1 to 9
		boolean[] test = new boolean[10];
		Arrays.fill(test, false);

		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board.length; j++) {

				try { 

					if (board[i][j] == 0) {
						// do nothing
					} else if (test[board[i][j]] == true) {
						return false;
					} else {
						test[board[i][j]] = true;
					}
				} 

				catch (IndexOutOfBoundsException e) { 
					System.out.println("uwu"); 
				} 

			}
			Arrays.fill(test, false);
		}
		return true;
	}

	static boolean checkColumns(int[][] board) {
		// check for duplicates in columns

		boolean[] test = new boolean[10];
		Arrays.fill(test, false);

		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[j][i] == 0) {
					// do nothing
				} else if (test[board[j][i]] == true) {
					return false;
				} else {
					test[board[j][i]] = true;
				}
			}
			Arrays.fill(test, false);
		}
		return true;
	}

	static boolean checkFamily(int[][] board) {
		// check for duplicates within each 3x3 family

		// dummy bloc for testing
		boolean[] test = new boolean[10];
		Arrays.fill(test, false);

		for (int k = 0; k <= 2; k++) {
			for (int l = 0; l <= 2; l++) {
				for (int i = 0; i <= 2; i++) {
					for (int j = 0; j <= 2; j++) {
						if (board[k * 3 + i][l * 3 + j] == 0) {
							// do nothing
						} else if (test[board[k * 3 + i][l * 3 + j]] == true) {
							return false;
						} else {
							test[board[k * 3 + i][l * 3 + j]] = true;
						}
					}
				}
				Arrays.fill(test, false);
			}
		}
		return true;
	}

	// solve using backtracking algorithm
	static int[][] backtrack(int[][] board) {

		boolean[][] given = new boolean[board.length][board.length];
		for (boolean[] row : given) {
			Arrays.fill(row, false);
		}

		for (int i = 0; i < board.length; i++) { 
			for (int j = 0; j < board.length; j++) { 
				if (board[i][j] != 0) {
					given[i][j] = true; // set all given values to true
				}
			}
		}


		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (given[i][j] == true) {
					// do nothing
				} else {
					while (board[i][j] <= 9) { 
						board[i][j] = board[i][j] + 1;

						display(board); 

					

						if (board[i][j] > 9) { // if value is 10, backtrack
							board[i][j] = 0;

							if (j == 0) {
								--i;
								j = board.length - 1;
							} else {
								--j;
							}

							while (given[i][j] == true) { // go back to last changeable cell
								if (j == 0) {
									--i;
									j = board.length - 1;
								} else {
									--j;
								}
							}

							if (j == 0) {  // go back one extra cell
								--i;
								j = board.length - 1;
							} else {
								--j;
							}

							break;
						}
							
						

							if (checkRows(board) == true && checkColumns(board) == true && checkFamily(board) == true) {
								break; // do nothing, this value is valid, leave while loop
							}

							else if (board[i][j] >= 9) { // if value is 9 and not valid, reset to 0 and backtrack
								board[i][j] = 0;
								if (j == 0) {
									--i;
									j = board.length - 1;
								} else {
									--j;
								}

								while (given[i][j] == true) { // go back to last changeable cell
									if (j == 0) {
										--i;
										j = board.length - 1;
									} else {
										--j;
									}
								}

								if (j == 0) {
									--i;
									j = board.length - 1;
								} else {
									--j;
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
