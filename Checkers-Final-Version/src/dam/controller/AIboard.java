package dam.controller;

import java.util.ArrayList;

import dam.model.Tile;

public class AIboard {
	
	int[][] board = new int[8][8];
	
	//Constructor
	public AIboard(Tile[][] AIboard) {
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				
				//int temp = AIboard[j][i].state;
				
				this.board[j][i] = AIboard[j][i].state;
				
			}
		}
	}
	
	public AIboard(int[][] intboard) {
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				this.board[j][i] = intboard[j][i];
			}
		}
	}
	
	
	public int[][] getBoard() {
		return board;
	}
	
	public int getState(int x, int y) {
		return this.board[x][y];
	}
	
	public void move(int x, int y, int x1, int y1) {
		
		int temp = this.board[x][y];
		this.board[x][y] = 0;
		
		this.board[x1][y1] = temp;
	}
	
	public void remove(int x, int y) {
		this.board[x][y] = 0;
	}
	
	public void printBoard() {
		
		//Visual board
		for(int i = 0; i < 8; i++) {
			
			for(int j = 0; j < 8; j++) {
				System.out.print(this.board[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void eliminate(int x, int y, int x1, int y1) {
		//move(x, y, x1, y1);
		System.out.println((x-x1));
	}
	
	
}
