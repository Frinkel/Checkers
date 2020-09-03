package dam.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import dam.model.Tile;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class MiniMaxAI {
	
	int n = 8;
	
	//For ArrayList
	ArrayList<Tile> move_list = new ArrayList<>();
	int xx;
	int yy;
	
	int[][] board = new int[8][8];
	
	//Create list
	ArrayList<int[]> posMoves = new ArrayList<>();
	
	//Recursive
	public static int d = 4;
	int moves;
	
	
	public MiniMaxAI(Tile[][] arrayAI) {
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {

				
				board[j][i] = arrayAI[j][i].state;
				
				
				if(arrayAI[j][i].king == true) {
					if(this.board[j][i] == 1) {
						this.board[j][i] = 3;
					}
					if(this.board[j][i] == 2) {
						this.board[j][i] = 4;
					}
				}
				
				
			}
		}
		
		//INIT AI
		minmax(board, d, true);
		
		//Print number of iterations the AI have been through
		//System.out.println("Number of moves: " + moves);
		
		
	}
	
	
	
	private int minmax(int[][] bArray, int depth, boolean maximizer) {
		
		//Get board array
		AIboard board = new AIboard(bArray);
		
		//Base case
		if(depth == 0) {
			
			//Return static evaluations
			return getPieces(board.getBoard(), false) - getPieces(board.getBoard(), true);
		}
		
		//Maximizer
		if(maximizer) {
			
			//System.out.println("MAX");
			
			int maxEval = -1000;
			int eval = 0;
			
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					
					if(board.getState(j, i) == 1 || board.getState(j, i) == 3) {
						
						//Black piece moves
						if(j + 1 < 8 && i + 1 < 8 && board.getState(j + 1, i + 1) == 0) {
							
							//Make move
							board.move(j, i, j + 1, i + 1);
							
							moves++;
							
							//Recursive
							eval = minmax(board.getBoard(), depth - 1, false);
							maxEval = Math.max(maxEval, eval);
							
							if(depth == d) {
								
								//Create array
								int[] move = new int[5];
								
								//Add values to array
								move[0] = eval; move[1] = j; move[2] = i; move[3] = j + 1; move[4] = i + 1;
								
								posMoves.add(move);
								
							}
							
							//Reset board
							board = new AIboard(bArray);
							
							
							
						}
						
						if(j - 1 >= 0 && i + 1 < 8 && board.getState(j - 1, i + 1) == 0) {
							
							//Make move
							board.move(j, i, j - 1, i + 1);
							
							moves++;
							
							//Recursive
							eval = minmax(board.getBoard(), depth - 1, false);
							maxEval = Math.max(maxEval, eval);
							
							if(depth == d) {
								
								//Create array
								int[] move = new int[5];
								
								//Add values to array
								move[0] = eval; move[1] = j; move[2] = i; move[3] = j - 1; move[4] = i + 1;
								
								posMoves.add(move);
							}
							
							board = new AIboard(bArray);
							

						}
						
						
						//Black piece kill
						if(j + 1 < 8 && i + 1 < 8 && (board.getState(j + 1, i + 1) == 2 || board.getState(j + 1, i + 1) == 4)) {
							
							//Get the new tile
			    			int nx = j + 2;
			    			int ny = i + 2;
							
							if(nx < 8 && nx >= 0 && ny < 8 && ny >= 0 && board.getState(nx, ny) == 0) {
								
								//Make move
								board.move(j, i, nx, ny);
								board.remove(nx - 1, ny - 1);
								
								moves++;
								
								//combo(board, j, i);
								
								//Recursive
								eval = minmax(board.getBoard(), depth - 1, false);
								maxEval = Math.max(maxEval, eval);
								
								if(depth == d) {
									
									//Create array
									int[] move = new int[5];
									
									//Add values to array
									move[0] = eval; move[1] = j; move[2] = i; move[3] = nx; move[4] = ny;
									
									posMoves.add(move);
								}
								
								board = new AIboard(bArray);
								
							}
						}
						
						//Black piece kill
						if(j - 1 >= 0 && i + 1 < 8 && (board.getState(j - 1, i + 1) == 2 || board.getState(j - 1, i + 1) == 4)) {
							
							//Get the new tile
			    			int nx = j - 2;
			    			int ny = i + 2;
			    			
							
							if(nx < 8 && nx >= 0 && ny < 8 && ny >= 0 && board.getState(nx, ny) == 0) {
								
								//Make moves
								board.move(j, i, nx, ny);
								board.remove(nx + 1, ny - 1);
								
								moves++;
								
								//combo(board, j, i);
								
								//Recursive
								eval = minmax(board.getBoard(), depth - 1, false);
								maxEval = Math.max(maxEval, eval);
								
								if(depth == d) {
									
									//Create array
									int[] move = new int[5];
									
									//Add values to array
									move[0] = eval; move[1] = j; move[2] = i; move[3] = nx; move[4] = ny;
									
									posMoves.add(move);
								}
								
								board = new AIboard(bArray);
							}
						}
						
						//------------------------------------------------------------------------------- Black king piece
						//Black king - extra moves
						if(board.getState(j, i) == 3 && j - 1 >= 0 && i - 1 >= 0 && board.getState(j - 1, i - 1) == 0) {
							
							board.move(j, i, j - 1, i - 1);
							
							moves++;
							//board.printBoard();
							//Recursive
							eval = minmax(board.getBoard(), depth - 1, false);
							maxEval = Math.max(maxEval, eval);
							
							if(depth == d) {
								
								//Create array
								int[] move = new int[5];
								
								//Add values to array
								move[0] = eval; move[1] = j; move[2] = i; move[3] = j - 1; move[4] = i - 1;
								
								posMoves.add(move);
							}
							
							board = new AIboard(bArray);
							
							
						}
						
						//Black king - extra moves
						if(board.getState(j, i) == 3 && j + 1 < 8 && i - 1 >= 0 && board.getState(j + 1, i - 1) == 0) {
							
							board.move(j, i, j + 1, i - 1);
							
							moves++;
							//board.printBoard();
							//Recursive
							eval = minmax(board.getBoard(), depth - 1, false);
							maxEval = Math.max(maxEval, eval);
							
							if(depth == d) {
								
								//Create array
								int[] move = new int[5];
								
								//Add values to array
								move[0] = eval; move[1] = j; move[2] = i; move[3] = j + 1; move[4] = i - 1;
								
								posMoves.add(move);
							}
							
							board = new AIboard(bArray);
							
						}
						
						//Black king piece kill
						if(board.getState(j, i) == 3 && j - 1 >= 0 && i - 1 >= 0 && (board.getState(j - 1, i - 1) == 2 || board.getState(j - 1, i - 1) == 4)) {
							
							//Get the new tile
			    			int nx = j - 2;
			    			int ny = i - 2;
			    			//System.out.println("BLACK KING 1");
							if(nx >= 0 && ny >= 0 && board.getState(nx, ny) == 0) {
								
								
								board.move(j, i, nx, ny);
								board.remove(nx + 1, ny + 1);
								
								
								moves++;
								
								//Recursive
								eval = minmax(board.getBoard(), depth - 1, false);
								maxEval = Math.max(maxEval, eval);
								
								if(depth == d) {
									//Create array
									int[] move = new int[5];
									
									//Add values to array
									move[0] = eval; move[1] = j; move[2] = i; move[3] = j - 2; move[4] = i - 2;
									
									posMoves.add(move);
									
								}
								
								board = new AIboard(bArray);
								
								
							}
						}
						
						//Black king piece kill
						if(board.getState(j, i) == 3 && j + 1 < 8 && i - 1 >= 0 && (board.getState(j + 1, i - 1) == 2 || board.getState(j + 1, i - 1) == 4)) {
							
							//Get the new tile
			    			int nx = j + 2;
			    			int ny = i - 2;
			    			
							if(nx < 8 && ny >= 0 && board.getState(nx, ny) == 0) {
								
								board.move(j, i, nx, ny);
								board.remove(nx - 1, ny + 1);
								
								moves++;
								
								//Recursive
								eval = minmax(board.getBoard(), depth - 1, false);
								maxEval = Math.max(maxEval, eval);
								
								if(depth == d) {
									//Create array
									int[] move = new int[5];
									
									//Add values to array
									move[0] = eval; move[1] = j; move[2] = i; move[3] = j + 2; move[4] = i - 2;
									
									posMoves.add(move);
									
								}
								
								board = new AIboard(bArray);
								
							}
						}
					}
				}
			}
			
			return maxEval;
			
			
		} else {
			
			//System.out.println("MIN");
			
			int minEval = 1000;
			int eval = 0;
			
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					
					
					if(board.getState(j, i) == 2 || board.getState(j, i) == 4) {
						
						//White piece moves
						if(j - 1 >= 0 && i - 1 >= 0 && board.getState(j - 1, i - 1) == 0) {
							
							board.move(j, i, j - 1, i - 1);
							
							moves++;
							
							//Recursive
							eval = minmax(board.getBoard(), depth - 1, true);
							minEval = Math.min(minEval, eval);
							
							
							board = new AIboard(bArray);
							
							
						}
						
						//White piece move
						if(j + 1 < 8 && i - 1 >= 0 && board.getState(j + 1, i - 1) == 0) {
							
							board.move(j, i, j + 1, i - 1);
							
							moves++;

							//Recursive
							eval = minmax(board.getBoard(), depth - 1, true);
							minEval = Math.min(minEval, eval);
							
							
							board = new AIboard(bArray);
							
						}
						
						//White piece kill
						if(j - 1 >= 0 && i - 1 >= 0 && (board.getState(j - 1, i - 1) == 1 || board.getState(j - 1, i - 1) == 3)) {
							
							//Get the new tile
			    			int nx = j - 2;
			    			int ny = i - 2;
			    			
							if(nx >= 0 && ny >= 0 && board.getState(nx, ny) == 0) {
								
								board.move(j, i, nx, ny);
								board.remove(nx + 1, ny + 1);
								
								//board.printBoard();
								
								moves++;
								
								//Recursive
								eval = minmax(board.getBoard(), depth - 1, true);
								minEval = Math.min(minEval, eval);
								
								board = new AIboard(bArray);
								
								
							}
						}
						
						//White piece kill
						if(j + 1 < 8 && i - 1 >= 0 && (board.getState(j + 1, i - 1) == 1 || board.getState(j + 1, i - 1) == 3)) {
							
							//Get the new tile
			    			int nx = j + 2;
			    			int ny = i - 2;
							
							if(nx < 8 && ny >= 0 && board.getState(nx, ny) == 0) {
								
								board.move(j, i, nx, ny);
								board.remove(nx - 1, ny + 1);
								
								moves++;
								
								//Recursive
								eval = minmax(board.getBoard(), depth - 1, true);
								minEval = Math.min(minEval, eval);
								
								board = new AIboard(bArray);
								
							}
						}
						
						
						
						//----------------------------------------------------------------------------------- White king piece
						//White king piece moves
						if(board.getState(j, i) == 4 && j + 1 < 8 && i + 1 < 8 && board.getState(j + 1, i + 1) == 0) {
							
							//Make move
							board.move(j, i, j + 1, i + 1);
							
							moves++;
							
							//Recursive
							eval = minmax(board.getBoard(), depth - 1, true);
							minEval = Math.min(minEval, eval);
							
							
							//Reset board
							board = new AIboard(bArray);
						}
						
						//White king piece moves
						if(board.getState(j, i) == 4 && j - 1 >= 0 && i + 1 < 8 && board.getState(j - 1, i + 1) == 0) {
							
							//Make move
							board.move(j, i, j - 1, i + 1);
							
							moves++;
							
							//Recursive
							eval = minmax(board.getBoard(), depth - 1, true);
							minEval = Math.min(minEval, eval);
							
							board = new AIboard(bArray);
						}
						
						
						//White king piece kill
						if(board.getState(j, i) == 4 && j + 1 < 8 && i + 1 < 8 && (board.getState(j + 1, i + 1) == 1 || board.getState(j + 1, i + 1) == 3)) {
							
							//Get the new tile
			    			int nx = j + 2;
			    			int ny = i + 2;
							
							if( nx < 8 && nx >= 0 && ny < 8 && ny >= 0 && board.getState(nx, ny) == 0) {
								
								
								//Make move
								board.move(j, i, nx, ny);
								board.remove(nx - 1, ny - 1);
								
								moves++;
								
								//Recursive
								eval = minmax(board.getBoard(), depth - 1, true);
								minEval = Math.min(minEval, eval);
								
								
								board = new AIboard(bArray);
								
								
							}
						}
						
						//White king piece kill
						if(board.getState(j, i) == 4 && j - 1 >= 0 && i + 1 < 8 && (board.getState(j - 1, i + 1) == 1 || board.getState(j - 1, i + 1) == 3)) {
							
							//Get the new tile
			    			int nx = j - 2;
			    			int ny = i + 2;
							
							if( nx < 8 && nx >= 0 && ny < 8 && ny >= 0 && board.getState(nx, ny) == 0) {
								
								//Make moves
								board.move(j, i, nx, ny);
								board.remove(nx + 1, ny - 1);
								
								moves++;
								
								//Recursive
								eval = minmax(board.getBoard(), depth - 1, true);
								minEval = Math.min(minEval, eval);
								
								board = new AIboard(bArray);
							}
						}	
					}
				}
			}
			
			return minEval;
			
		}
	}
	
	
	public int[] getMoveDecision() {
		
		for(int j = posMoves.size() - 1; j >= 0; j--) {
			for(int i = posMoves.size() - 1; i >= 0; i--) {
				if(posMoves.get(j)[0] < posMoves.get(i)[0]) {
					
					//Remove the lowest scores
					posMoves.remove(j);
					break;
				}
			}
		}
		
		//If there is more than 1 possible move, get a random move
		if(posMoves.size() > 1) {
			Random random = new Random();
			return(posMoves.get(random.nextInt(posMoves.size())));
		
		//If there is only 1 move, get that move
		} else {
			return posMoves.get(0);
		}
	}
	
	
	
	//Get number of pieces
	private int getPieces(int[][] js, boolean white) {
		int pieces = 0;
		
		for(int i = 0; i < 8; i++) {
			
			for(int[] p : js) {
				
				//Get black pieces
				if(p[i] == 1 && white == false) {
					pieces += 1;
				}
				
				//Get white pieces
				if(p[i] == 2 && white == true) {
					pieces += 1;
				}
			}
		}
		
		return pieces;
	}
	
	//For combos
	private AIboard combo(AIboard board, int x, int y) {
		
		AIboard cBoard = new AIboard(board.getBoard());
		
		//NORMAL OR KING
		if((cBoard.getState(x, y) == 1 || cBoard.getState(x, y) == 3)) {
			
			if(x + 2 < 8 && y + 2 < 8 && (cBoard.getState(x + 1, y + 1) == 2 || cBoard.getState(x + 1, y + 1) == 4) && cBoard.getState(x + 2, y + 2) == 0) {
				int[] combo_kills = new int[20];
				System.out.println("COMBO1: " + x + ", " + y + " - " + (x + 2) + ", " + (y+2));
				cBoard.move(x, y, x + 2 , y + 2);
				
				cBoard.printBoard();
				combo(cBoard, x + 2, y + 2);
				cBoard = new AIboard(board.getBoard());
				
			}
			
			if(x - 2 >= 0 && y + 2 < 8 && (cBoard.getState(x - 1, y + 1) == 2 || cBoard.getState(x - 1, y + 1) == 4) && cBoard.getState(x - 2, y + 2) == 0) {
				int[] combo_kills = new int[20];
				System.out.println("COMBO2: " + x + ", " + y + " - " + (x - 2) + ", " + (y + 2));
				cBoard.move(x, y, x - 2 , y + 2);
				
				cBoard.printBoard();
				combo(cBoard, x - 2, y + 2);
				cBoard = new AIboard(board.getBoard());
				
			}
		}
		
		
		//KING
		if(board.getState(x, y) == 3) {
			
			if(x - 2 >= 0 && y - 2 >= 0 && (cBoard.getState(x - 1, y - 1) == 2 || cBoard.getState(x - 1, y - 1) == 4) && cBoard.getState(x - 2, y - 2) == 0) {
				int[] combo_kills = new int[20];
				
			}
			
			if(x + 2 < 8 && y - 2 <= 0 && (cBoard.getState(x + 1, y - 1) == 2 || cBoard.getState(x + 1, y - 1) == 4) && cBoard.getState(x + 2, y - 2) == 0) {
				int[] combo_kills = new int[20];
				
			}
		}
		
		
		
		return board;
		
	}
	
	
}
	
	



	
	






