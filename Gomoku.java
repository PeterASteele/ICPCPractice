import java.util.*;
import java.math.*;
import java.io.*;

//This problem was not solved by anyone in the real contest. Not even tourist. (although he was close)
//Sorry for the complete lack of readability and design. One day, I will re-solve this problem, and make it readable. 

//NEERC 2014 Problem G (Practice)
//http://codeforces.com/gym/100553/attachments

public class Gomoku {
	public static class Point implements Comparable<Point> {
		int x;
		int y;
		long score;

		public Point(int _x, int _y, long _score) {
			x = _x;
			y = _y;
			score = _score;
		}

		@Override
		public int compareTo(Point o) {
			return -1 * Long.compare(score, o.score);
		}

		public String toString() {
			return (y + 1) + " " + (x + 1) + " " + score;
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		Scanner input = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					board[i][j] = Cell.EMPTY;
				}
			}
			 sc.nextInt();
			 sc.nextInt();
			board[9][9] = Cell.AI;
			// System.out.println(10 + " " + 10);
			while (true) {
				long bestScore = Long.MIN_VALUE;
				int rowS = -1;
				int colS = -1;
				ArrayList<Point> pos = new ArrayList<Point>();
				for (int a = 0; a < SIZE; a++) {
					for (int b = 0; b < SIZE; b++) {
						if (board[a][b] == Cell.EMPTY) {
							if (board[a][b] == Cell.EMPTY) {
								board[a][b] = Cell.HUMAN;
								swap();
								resetScores();
								determineScores();
								swap();
								long sum = sumScores();
								pos.add(new Point(a, b, sum));
								board[a][b] = Cell.EMPTY;
							}
						}
					}
				}

				Collections.sort(pos);
				// System.out.println("ourmoves" + pos);
				int count = 0;
				for (Point p : pos) {

					count++;
					if (count > 4) {
						break;
					}
					int a = p.x;
					int b = p.y;
					if (board[a][b] == Cell.EMPTY) {

						board[a][b] = Cell.HUMAN;
						swap();
						resetScores();
						determineScores();
						swap();
						long sum = sumScores();
						if (sum > 1000000000000000L) {
							board[a][b] = Cell.EMPTY;
							rowS = a;
							colS = b;
							break;
						}
						// ///
						long bestScore2 = Long.MIN_VALUE;
						ArrayList<Point> pos3 = new ArrayList<Point>();
						for (int a2 = 0; a2 < SIZE; a2++) {
							for (int b2 = 0; b2 < SIZE; b2++) {
								if (board[a2][b2] == Cell.EMPTY) {
									board[a2][b2] = Cell.AI;

									resetScores();
									determineScores();

									sum = sumScores();
									if (bestScore2 < sum) {
										bestScore2 = sum;
									}
									pos3.add(new Point(a2, b2, sum));
									board[a2][b2] = Cell.EMPTY;
								}

							}
						}
						Collections.sort(pos3);
						// System.out.println("opponentPoss" + pos3);
						long bestScore6 = Long.MIN_VALUE;
						long startScore = pos3.get(0).score;
						int count10 = 0;
						for (Point q2 : pos3) {
							count10++;
							if (count10 > 3) {
								break;
							}
							if (q2.score < startScore - 2502) {
								break;
							}
							long bestScore3 = -1;
							board[q2.x][q2.y] = Cell.AI;
							if (Math.abs(bestScore2) > 1000000000000000L) {
								bestScore3 = Long.MAX_VALUE - 1;
								// System.out.println("Hopeless possibility at "
								// + a
								// + " " + b);
							} else {
								bestScore3 = Long.MAX_VALUE - 1;

								ArrayList<Point> pos2 = new ArrayList<Point>();
								for (int a2 = 0; a2 < SIZE; a2++) {
									for (int b2 = 0; b2 < SIZE; b2++) {
										if (board[a2][b2] == Cell.EMPTY) {
											board[a2][b2] = Cell.HUMAN;
											swap();
											resetScores();
											determineScores();
											swap();
											sum = sumScores();
											pos2.add(new Point(a2, b2, sum));
											board[a2][b2] = Cell.EMPTY;
										}

									}
								}
								Collections.sort(pos2);
								// System.out.println("ourmove2in");
								// System.out.println(pos2);
								// System.out.println("Should attempt evaluation soon");
								int count3 = 0;
								for (Point q : pos2) {
									count3++;
									if (count3 > 2) {
										break;
									}
									// System.out.println("Attempting an evaluation");
									int a3 = q.x;
									int b3 = q.y;

									if (board[a3][b3] == Cell.EMPTY) {

										board[a3][b3] = Cell.HUMAN;
										swap();
										resetScores();
										determineScores();
										swap();
										long sum3 = sumScores();

										// ///
										long bestScore4 = Long.MIN_VALUE;
										int rowS4 = -1;
										int colS4 = -1;
										for (int a2 = 0; a2 < SIZE; a2++) {
											for (int b2 = 0; b2 < SIZE; b2++) {
												if (board[a2][b2] == Cell.EMPTY) {
													board[a2][b2] = Cell.AI;

													resetScores();
													determineScores();

													sum = sumScores();
													if (sum > bestScore4) {
														// System.out.println("updating best2"
														// + " " + sum);
														bestScore4 = sum;
														rowS4 = a2;
														colS4 = b2;
													}
													board[a2][b2] = Cell.EMPTY;
												}

											}
										}
										if (bestScore3 > bestScore4) {
											bestScore3 = bestScore4;
											// System.out.println("Updating worst case to "
											// + bestScore3);
										}
										board[a3][b3] = Cell.EMPTY;
									}
								}

							}
							board[q2.x][q2.y] = Cell.EMPTY;
							if (bestScore6 < bestScore3) {
								bestScore6 = bestScore3;
								// System.out.println("Updating best case to  "
								// + bestScore6);
							}
						}
						sum = -1 * bestScore6;
						// System.out.println("SUM" + sum);
						if (sum > bestScore) {
							bestScore = sum;
							rowS = a;
							colS = b;
							// System.out.println("Trying " + " " + (b + 1) +
							// " "
							// + (a + 1) + " and " + sum);
						}
						board[a][b] = Cell.EMPTY;
					}
				}

				int humanRow = rowS;
				int humanCol = colS;
				if (board[humanRow][humanCol] != Cell.EMPTY) {
					PANIC();
				}
				System.out.println((humanCol + 1) + " " + (humanRow + 1));
				board[humanRow][humanCol] = Cell.HUMAN;
				resetScores();
				determineScores();
				long score = sumScores();
				if (Math.abs(score) > Math.pow(50, 9)) {
					// printBoard();
//					System.out.println("Nice!!!!!!");
					break;
				}
				// resetScores();
				// determineScores();
				//
				// printBoard();
				// input.nextLine();
				bestScore = Long.MIN_VALUE;

				 colS = sc.nextInt()-1;
				 rowS = sc.nextInt()-1;
				 if(colS < 0 || rowS < 0){
					 System.exit(0);
				 }
//				colS = -1;
//				rowS = -1;
//				for (int a = 0; a < SIZE; a++) {
//					for (int b = 0; b < SIZE; b++) {
//						if (board[a][b] == Cell.EMPTY) {
//							board[a][b] = Cell.AI;
//
//							resetScores();
//							determineScores();
//
//							addRandom();
//							long sum = sumScores();
//							if (sum > bestScore) {
//								bestScore = sum;
//								rowS = a;
//								colS = b;
//							}
//							board[a][b] = Cell.EMPTY;
//						}
//
//					}
//				}
//				System.out.println((colS + 1) + " " + (rowS + 1));
				board[rowS][colS] = Cell.AI;
				resetScores();
				determineScores();
				score = sumScores();
				if (Math.abs(score) > Math.pow(50, 9)) {
					printBoard();
					System.out.println("BAD");
					break;
				}
				// printBoard();
			}
		}

	private static boolean relevantAdj(int x, int y) {
		for (int a = -1; a <= 1; a++) {
			for (int b = -1; b <= 1; b++) {
				if (x + a >= SIZE || x + a < 0 || y + b >= SIZE || y + b < 0) {

				} else {
					if (board[x + a][y + b] == Cell.AI
							|| board[x + a][y + b] == Cell.HUMAN) {
						return true;
					}
				}

			}
		}
		return false;

	}

	private static void swap() {
		for (int a = 0; a < SIZE; a++) {
			for (int b = 0; b < SIZE; b++) {
				if (board[a][b] == Cell.AI) {
					board[a][b] = Cell.HUMAN;
				} else {
					if (board[a][b] == Cell.HUMAN) {
						board[a][b] = Cell.AI;
					}
				}
			}
		}
	}

	private static void printBoard() {
		for (int a = 0; a < SIZE; a++) {
			for (int b = 0; b < SIZE; b++) {
				// System.out.print(board[a][b] == Cell.AI ? "  X  |"
				// : board[a][b] == Cell.HUMAN ? "  O  |" :
				// String.format("%2d %2d|", b+1, a+1));
				System.out.print(board[a][b] == Cell.AI ? "X"
						: board[a][b] == Cell.HUMAN ? "O" : " ");
			}
			System.out.println();
		}

	}

	private static long sumScores() {
		long total = 0;
		for (int a = 0; a < SIZE; a++) {
			for (int b = 0; b < SIZE; b++) {
				total += score[a][b];
			}
		}
		return total;
	}

	static void printScores() {
		for (int i = score.length - 1; i >= 0; i--) {
			for (int j = 0; j < score[i].length; j++) {
				System.out.print(score[i][j] + "\t");
			}
			System.out.println();
		}
	}

	static void determineScores() {
		// Horizontals
		for (int row = 0; row < SIZE; row++) {
			for (int colStart = 0; colStart <= SIZE - 5; colStart++) {
				int aiCount = 0;
				int humanCount = 0;
				for (int col = colStart; col <= colStart + 4; col++) {
					Cell cell = board[row][col];
					if (cell == Cell.AI) {
						aiCount++;
					} else if (cell == Cell.HUMAN) {
						humanCount++;
					}
				}

				long offset = 0;

				if (aiCount > 0 && humanCount == 0) {
					offset = (long) Math.pow(50, 2 * aiCount - 1);
				} else if (aiCount == 0 && humanCount > 0) {
					offset = (long) -Math.pow(50, 2 * humanCount);
				}
				if (humanCount >= 5) {
					offset = Long.MIN_VALUE / 20;
				}
				if (aiCount >= 5) {
					offset = Long.MAX_VALUE / 20;
				}
				score[row][colStart] += offset;
			}
		}

		// Verticals
		for (int col = 0; col < SIZE; col++) {
			for (int rowStart = 0; rowStart <= SIZE - 5; rowStart++) {
				int aiCount = 0;
				int humanCount = 0;
				for (int row = rowStart; row <= rowStart + 4; row++) {
					Cell cell = board[row][col];
					if (cell == Cell.AI) {
						aiCount++;
					} else if (cell == Cell.HUMAN) {
						humanCount++;
					}
				}

				long offset = 0;
				if (aiCount > 0 && humanCount == 0) {
					offset = (long) Math.pow(50, 2 * aiCount - 1);
				} else if (aiCount == 0 && humanCount > 0) {
					offset = (long) -Math.pow(50, 2 * humanCount);
				}
				if (humanCount >= 5) {
					offset = Long.MIN_VALUE / 20;
				}
				if (aiCount >= 5) {
					offset = Long.MAX_VALUE / 20;
				}
				score[rowStart][col] += offset;
			}
		}

		// Diagonals
		for (int rowStart = 0; rowStart < SIZE; rowStart++) {
			for (int column = 0; column < SIZE; column++) {
				int aiCount = 0;
				int humanCount = 0;
				for (int row = rowStart, col = column; row <= rowStart + 4; row++, col++) {
					if (row >= SIZE || col >= SIZE) {
						break;
					}
					Cell cell = board[row][col];
					if (cell == Cell.AI) {
						aiCount++;
					} else if (cell == Cell.HUMAN) {
						humanCount++;
					}
				}
				long offset = 0;
				if (aiCount > 0 && humanCount == 0) {
					offset = (long) Math.pow(50, 2 * aiCount - 1);
				} else if (aiCount == 0 && humanCount > 0) {
					offset = (long) -Math.pow(50, 2 * humanCount);
				}
				if (humanCount >= 5) {
					offset = Long.MIN_VALUE / 20;
				}
				if (aiCount >= 5) {
					offset = Long.MAX_VALUE / 20;
				}
				score[rowStart][column] += offset;
			}
		}
		for (int rowStart = 0; rowStart < SIZE; rowStart++) {
			for (int column = 0; column < SIZE; column++) {
				int aiCount = 0;
				int humanCount = 0;
				for (int row = rowStart, col = column; row <= rowStart + 4; row++, col--) {
					if (row >= SIZE || col < 0) {
						break;
					}
					Cell cell = board[row][col];
					if (cell == Cell.AI) {
						aiCount++;
					} else if (cell == Cell.HUMAN) {
						humanCount++;
					}
				}
				long offset = 0;
				if (aiCount > 0 && humanCount == 0) {
					offset = (long) Math.pow(50, 2 * aiCount - 1);
				} else if (aiCount == 0 && humanCount > 0) {
					offset = (long) -Math.pow(50, 2 * humanCount);
				}
				if (humanCount >= 5) {
					offset = Long.MIN_VALUE / 20;
				}
				if (aiCount >= 5) {
					offset = Long.MAX_VALUE / 20;
				}
				score[rowStart][column] += offset;
			}
		}
		// long total = 0;
		// for(int a = 0; a < SIZE; a++){
		// for(int b = 0; b < SIZE; b++){
		// total += score[a][b];
		// }
		// }
		// return total;
	}

	static void addRandom() {
		score[0][0] += rng.nextInt(2500);
	}

	static void resetScores() {
		for (int i = 0; i < score.length; i++) {
			for (int j = 0; j < score[i].length; j++) {
				score[i][j] = 0;
			}
		}
	}

	static void PANIC() {
		System.out.println(-1 + " " + -1);
		System.exit(0);
	}

	enum Cell {
		EMPTY, AI, HUMAN
	};

	static final int SIZE = 19;
	static final Cell[][] board = new Cell[SIZE][SIZE];
	static final Random rng = new Random();
	static final long[][] score = new long[SIZE][SIZE];
}