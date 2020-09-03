package dam.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import dam.model.ButtonRotate;
import dam.model.CamMode;
import dam.model.Crown;
import dam.model.Piece;
import dam.model.Piece3d;
import dam.model.Tile;
import dam.view.SimDam;
import dam.view.Table;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Controller {
	
	//Files
	File filewhite = new File("media/whitecrown.png");
	public Image WhiteCrown = new Image (filewhite.toURI().toString());
	
	File fileblack = new File("media/blackcrown.png");
	public Image BlackCrown = new Image (fileblack.toURI().toString());
	
	Media media = new Media(new File("media/minecraft.mp3").toURI().toString());  
	
	Media click = new Media(new File("media/move.mp3").toURI().toString());  

	
	//For selecting
	boolean sel = false;
	int selx;
	int sely;
	
	//For pieces
	public Piece pieceBlack = new Piece(25, 25, 20, Color.BLUE);
	public Piece pieceWhite = new Piece(25, 25, 20, Color.RED);
	Tile tn;
	String temp_col;
	Node temp_cir;
	
	public int p1_pieces = 0;
	public int p2_pieces = 0;
	
	//For Timers
	public int maxTime = 180;
	public int tempTime;
	public int seconds1 = maxTime, seconds2 = maxTime;
	public Timer time;
	
	//For turn changing
	public static boolean turn = false;
	int turn_state = 2;
	int inv_turn_state = 1;
	
	//For ArrayList
	ArrayList<Tile> tile_list = new ArrayList<>();
	int nx;
	int ny;
	int xx;
	int yy;
	
	//For combo
	public boolean combo_state = false;
	
	//For 3d
	public static int j;
	
	//Get the SimDam
	public SimDam simDam;
	
	
	
	
	public void Control() {
		//3d
		j = SimDam.XD ? 0 : 0;
		tempTime = simDam.time;
		
		
			
			 SimDam.gPane.setOnMouseClicked((MouseEvent t) -> {
				PickResult res = t.getPickResult();
				if(res.getIntersectedNode() instanceof Tile || res.getIntersectedNode() instanceof Piece3d || res.getIntersectedNode() instanceof Piece || res.getIntersectedNode() instanceof MeshView) {
					Node node;
					if(res.getIntersectedNode() instanceof Tile) node = res.getIntersectedNode();
					else  node = res.getIntersectedNode().getParent();
					
				
					//---------------------------------------------------------------------------------------- SELECT STATE
				//If a piece isn't selected, and a tile with a piece on it is clicked
				if(sel == false && ((Tile)node).state == turn_state && combo_state == false) {
			    	
					//Select new tile
			    	selx = GridPane.getColumnIndex(node);
			    	sely = GridPane.getRowIndex(node);
			    	
					tn = ((Tile)node);
					temp_col = tn.color;
					temp_cir = (Node) tn.getChildren().get(j);
					
					((Tile)node).setStyle("-fx-background-color: yellow;");
			    	
			    	//A piece is now selected
			    	sel = true;
			    	
			    	
			    	//Get move options
					move_options(node, "yellow");
			    	
					
			    	
			    	
			    //If a piece is selected, and a tile without a piece on it is clicked
				} else if((sel == true && ((Tile)node).state == 0)) {
					
					//Get x and y position of the clicked node
					xx = GridPane.getColumnIndex(node);
					yy = GridPane.getRowIndex(node);
					
					//---------------------------------------------------------------------------------------- KILL STATE + COMBO KILL
					//Check if the piece can eliminate an enemy piece
					if((Math.abs(xx - selx) == 2 && Math.abs(yy - sely) == 2 && ((Tile)node).state == 0 && tn.king) ||
							(tn.state == 1 && yy - sely == 2 && Math.abs(xx - selx) == 2 && ((Tile)node).state == 0) || 
							(tn.state == 2 && yy - sely == -2 && Math.abs(xx - selx) == 2 && ((Tile)node).state == 0)) {
						
						
						
						
						//Get the x and y of the tile inbetween cursor and selected tile
						nx = (GridPane.getColumnIndex(((Tile)node)) - selx) / 2 + selx;
						ny = (GridPane.getRowIndex(((Tile)node)) - sely) / 2 + sely;
						
						
						if(((Tile)getNodeFromGridPane(simDam.gPane, nx, ny)).state == inv_turn_state) {
							
							//Remove eliminated piece
							scale(((Tile)getNodeFromGridPane(simDam.gPane, nx, ny)).getChildren().get(j), getNodeFromGridPane(simDam.gPane, nx, ny));
							((Tile)getNodeFromGridPane(simDam.gPane, nx, ny)).state = 0;
							((Tile)getNodeFromGridPane(simDam.gPane, nx, ny)).king = false;
							
							//Swap tiles
							swap_tiles(tn, GridPane.getColumnIndex(((Tile)node)),  GridPane.getRowIndex(((Tile)node)));
							
							
							
							
							//Get new tile id
							tn = ((Tile)getNodeFromGridPane(simDam.gPane, GridPane.getColumnIndex(((Tile)node)),  GridPane.getRowIndex(((Tile)node))));
							
							//Play kill sound
							if(simDam.soundPlay) {
								MediaPlayer killSound = new MediaPlayer(media);
								killSound.play();
							}
							
							
							//Deduct total pieces, when piece is eliminated
							switch(inv_turn_state) {
								case(1):
									
									//Decrement player 2 total pieces
									p2_pieces -= 1;
									
									//Update label for player 2
									simDam.l3.setText(simDam.p2_name + " has " + p2_pieces + " pieces left");
									if(SimDam.XD) {
										Table.point1.setText("" + p2_pieces);
									}
									
								break;
								
								case(2):
									
									//Decrement player 1 total pieces
									p1_pieces -= 1;
									
									//Update label for player 1
									simDam.l4.setText(simDam.p1_name + " has " + p1_pieces + " pieces left");
									if(SimDam.XD) {
										Table.point2.setText("" + p1_pieces);
									}
									
								break;
							}
							
							
							//Check if one of the players have lost all of their pieces
							if(p1_pieces == 0) {
								simDam.cancel = true;
								simDam.l5.setText(simDam.p2_name + " wins!"); 
								simDam.bp4.setBottom(simDam.btn_mainMenu);
								BorderPane.setAlignment(simDam.btn_mainMenu, Pos.CENTER);
								BorderPane.setMargin(simDam.btn_mainMenu, new Insets(0, 0, 20, 0));
								simDam.stage.setScene(simDam.victory);
								simDam.stage.show();								
							} else if(p2_pieces == 0) {
								simDam.cancel = true;
								simDam.l5.setText(simDam.p1_name + " wins!");
								simDam.bp4.setBottom(simDam.btn_mainMenu);
								BorderPane.setAlignment(simDam.btn_mainMenu, Pos.CENTER);
								BorderPane.setMargin(simDam.btn_mainMenu, new Insets(0, 0, 20, 0));
								simDam.stage.setScene(simDam.victory);
							}
							

							//Check if the piece should be a king
							if( yy == simDam.n - 1 && tn.state == 1){
								((Tile)node).king = true;
								if(SimDam.XD) {
									tn.getChildren().remove(0);
									tn.getChildren().add(Crown.MakeCrown(Color.web("#211A21")));
									
								}
								else {((Piece) tn.getChildren().get(0)).setFill(new ImagePattern(BlackCrown));}
							}
							
							
							if(yy == 0 && tn.state == 2){
								((Tile)node).king = true;
								
								if(SimDam.XD) {
									tn.getChildren().remove(0);
									tn.getChildren().add(Crown.MakeCrown(Color.BLANCHEDALMOND));
								
								} else {
									((Piece) tn.getChildren().get(0)).setFill(new ImagePattern(WhiteCrown));
									}
							}
							
							animate((((Tile) node).getChildren()).get(j), xx - selx, yy - sely);
							
							//Check if you can kill more pieces, and switch state if so
							if(check_neighbor(xx, yy) == true) {
								
								//Update combo
								combo_state = true;
								
								//Update selected tile
								tn = (Tile) getNodeFromGridPane(simDam.gPane, (GridPane.getColumnIndex(((Tile)node))), GridPane.getRowIndex(((Tile)node)));
						    	selx = GridPane.getColumnIndex(node);
						    	sely = GridPane.getRowIndex(node);
						    	
						    	move_options(node, "yellow");
								
								
							} else {
								
								//AI
								if(simDam.AI_on) {
									AIMovement();
								}
								
								move_options_reset((Tile)node);
								
								combo_state = false;
								
								//A piece is no longer selected
								sel = false;
								
								//End turn
								turn = !turn;
								
								//rotate camera
								if(SimDam.XD && ButtonRotate.buttState == CamMode.ROTATE) {
									camControl.setFront();
								}
								
								//Change which piece that can be selected
								if(turn == true) {
									turn_state = 2;
									inv_turn_state = 1;
									
								} else {
									turn_state = 1;
									inv_turn_state = 2;	
								}
								
							}
						}
					}
					
					
					
					 
					//---------------------------------------------------------------------------------------- MOVE STATE
					if(combo_state == false && ((Math.abs(xx - selx) == 1 && Math.abs(yy - sely) == 1 && ((Tile)node).state == 0 && tn.king) ||
							(tn.state == 1 && yy - sely == 1 && Math.abs(xx - selx) == 1 && ((Tile)node).state == 0) || 
							(tn.state == 2 && yy - sely == -1 && Math.abs(xx - selx) == 1 && ((Tile)node).state == 0))) {
						
						//Check if the piece should be a king
						if(((Tile)node).state == 0 && yy == simDam.n - 1 && tn.state == 1){
							((Tile)node).king = true;
							if(SimDam.XD) {
								temp_cir = Crown.MakeCrown(Color.web("#211A21"));
							}
							else {((Piece) tn.getChildren().get(0)).setFill(new ImagePattern(BlackCrown));}
						}
						
						if(((Tile)node).state == 0 && yy == 0 && tn.state == 2){
							((Tile)node).king = true;
							if(SimDam.XD) {
								temp_cir = Crown.MakeCrown(Color.BLANCHEDALMOND);
							}else {((Piece) tn.getChildren().get(0)).setFill(new ImagePattern(WhiteCrown));}
						}
						
						//Swap tiles
						
						swap_tiles(tn, GridPane.getColumnIndex(((Tile)node)), GridPane.getRowIndex(((Tile)node)));
						animate((((Tile) node).getChildren()).get(j), xx - selx, yy - sely);
						
						
						//AI
						if(simDam.AI_on) {
							AIMovement();
						}
						
			        	//A piece is no longer selected
						sel = false;
						
						//Change turn
						turn = !turn;
						
						//rotate camera
						if(SimDam.XD && ButtonRotate.buttState == CamMode.ROTATE) {
							camControl.setFront();
						}
						
						//Change which piece that can be selected
						if(turn == true) {
							turn_state = 2;
							inv_turn_state = 1;
							
						} else {
							turn_state = 1;
							inv_turn_state = 2;
							
						}
					
					}
				
					//---------------------------------------------------------------------------------------- RESELECT STATE
				//If a piece is selected and a tile with a piece is clicked
				} else if(sel == true && ((Tile)node).state == turn_state && combo_state == false) {
					
					if(((Tile)node) != tn) {
						
						
						//Reset move options
						move_options_reset(tn);
						
						//Reset previous tile
						tn.state = turn_state;
						tn.setStyle("-fx-background-color:" + temp_col);
						tn.color = temp_col;
						
						//Select new tile
						selx = GridPane.getColumnIndex(node);
				    	sely = GridPane.getRowIndex(node);
				    	
						tn = ((Tile)node);
						temp_col = tn.color;
						temp_cir = (Node) tn.getChildren().get(j);
						
						//Get new move options
						move_options(tn, "yellow");
						
					}
				    
					//Set the color of the selected tile
					((Tile)node).setStyle("-fx-background-color: yellow;");
			    	
			    }
			}});
	}
	
	
	public void doTime() {
		time = new Timer();
		if(simDam.timer) {
			time.schedule(new TimerTask() {
	
				@Override
				public void run() {
					Platform.runLater(new Runnable() {
	
						@Override
						public void run() {
							while(seconds1 != 0 && seconds2 != 0) {
								if(turn) {
									seconds1--;
								}else {
									seconds2--;
								}
								simDam.l6.setText(simDam.p2_name + " time " + String.format("%02d:%02d", seconds2/60, seconds2%60));
								simDam.l7.setText(simDam.p1_name + " time " + String.format("%02d:%02d", seconds1/60, seconds1%60));
								if(SimDam.XD) {
								Table.time2.setText(String.format("%02d:%02d", seconds2/60, seconds2%60));
								Table.time1.setText(String.format("%02d:%02d", seconds1/60, seconds1%60));
								}
								break;
							}
							if(simDam.cancel) {
								time.cancel();
								simDam.cancel = false;
							}
							if(seconds1 == 0) {
								time.cancel();
								simDam.l5.setText(simDam.p2_name + " wins!"); 
								simDam.bp4.setBottom(simDam.btn_mainMenu);
								BorderPane.setAlignment(simDam.btn_mainMenu, Pos.CENTER);
								BorderPane.setMargin(simDam.btn_mainMenu, new Insets(0, 0, 20, 0));
								simDam.stage.setScene(simDam.victory);
								simDam.stage.show();
								
							} else if(seconds2 == 0) {
								time.cancel();
								simDam.l5.setText(simDam.p1_name + " wins!");
								simDam.bp4.setBottom(simDam.btn_mainMenu);
								BorderPane.setAlignment(simDam.btn_mainMenu, Pos.CENTER);
								BorderPane.setMargin(simDam.btn_mainMenu, new Insets(0, 0, 20, 0));
								simDam.stage.setScene(simDam.victory);
							}
						}
					});
				}
				
			}, 1000, 1000);
		}
	}
	
	
	//AI movement control
	public void AIMovement() {
		
		if(turn == true) {
			
			//Get an array of the tiles
			Tile[][] arrayAI = new Tile[8][8];
			
			for(int i = 0; i < 8; i++) {
				
				for(int j = 0; j < 8; j++) {
					arrayAI[j][i] = ((Tile)getNodeFromGridPane(simDam.gPane, j, i));
				}
			}
			
			//Create AI
			MiniMaxAI AI = new MiniMaxAI(arrayAI);
			
			//Get move decision
			int[] decision = AI.getMoveDecision();
			
			System.out.println("AI moves Piece (" + decision[1] + ", " + decision[2] + ") to Tile (" + decision[3] + ", " + decision[4] + "). Score = " + decision[0]);
			
				
			if(Math.abs(decision[3] - decision[1]) == 2 && Math.abs(decision[4] - decision[2]) == 2) {
				((Tile)getNodeFromGridPane(simDam.gPane, (decision[1] -  decision[3])/2 + decision[3], (decision[4] + ((decision[2] - decision[4])/2)))).getChildren().clear();
				((Tile)getNodeFromGridPane(simDam.gPane, (decision[1] -  decision[3])/2 + decision[3], (decision[4] + ((decision[2] - decision[4])/2)))).state = 0;
				
				//Decrement player 1 total pieces
				p1_pieces -= 1;
				
				//Update label for player 1
				simDam.l4.setText(simDam.p1_name + " has " + p1_pieces + " pieces left");
				if(SimDam.XD) {
					Table.point2.setText("" + p1_pieces);
				}
				
				//Check if one of the players have lost all of their pieces
				if(p1_pieces == 0) {
					simDam.cancel = true;
					simDam.l5.setText(simDam.p2_name + " wins!"); 
					simDam.bp4.setBottom(simDam.btn_mainMenu);
					BorderPane.setAlignment(simDam.btn_mainMenu, Pos.CENTER);
					BorderPane.setMargin(simDam.btn_mainMenu, new Insets(0, 0, 20, 0));
					simDam.stage.setScene(simDam.victory);
					simDam.stage.show();								
				}
				
			}
			
			//Reset old tile
			Tile t = (Tile) getNodeFromGridPane(simDam.gPane, decision[1], decision[2]);
			t.state = 0;
			Node _cir = (Node) t.getChildren().get(j);
			t.getChildren().clear();
			t.setStyle("-fx-background-color:" + temp_col);
			t.color = temp_col;
			
			//Init new tile
			tn = (((Tile)getNodeFromGridPane(simDam.gPane, decision[3], decision[4])));
			tn.getChildren().add(_cir);
			tn.state = 1;
			
			//Properties for a king
			if(t.king) {
				//t.king = !t.king;
				tn.king = true;
			}
			t.king = false;
			
			//Check if the piece should be a king
			if(tn.state == 1 && decision[4] == simDam.n - 1){
				tn.king = true;
				
				if(simDam.XD) {
					tn.getChildren().remove(0);
					tn.getChildren().add(Crown.MakeCrown(Color.web("#211A21")));
				} else {
					((Shape) tn.getChildren().get(0)).setFill(new ImagePattern(BlackCrown));
				}
			}
			
			
			//t.king = false;
			
			//Change turn
			turn = !turn;
			
			//Change which piece that can be selected
			if(turn == true) {
				turn_state = 2;
				inv_turn_state = 1;
				
			} else {
				turn_state = 1;
				inv_turn_state = 2;
				
			}
		}
	}
	
	
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
	    for (Node nod : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(nod) == col && GridPane.getRowIndex(nod) == row) {
	            return nod;
	        }
	    }
	    return null;
	}
	
	
	//For swapping pieces on two tiles, given by an old tile and the coordinates to a new tile
	private void swap_tiles(Tile t, int x, int y) {
		
		//Swap tiles
		
		//Reset old tile
		t.state = 0;
		t.getChildren().remove(j);
		t.setStyle("-fx-background-color:" + temp_col);
		t.color = temp_col;
		
		//Reset move options
		move_options_reset(t);
		
		//Init new tile
		(((Tile)getNodeFromGridPane(simDam.gPane, x, y)).getChildren()).add(temp_cir);
		((Tile)getNodeFromGridPane(simDam.gPane, x, y)).state = turn_state;
		
		//Properties for a king
		if(t.king) {
			t.king = !t.king;
			((Tile)getNodeFromGridPane(simDam.gPane, x, y)).king = true;
		}
		
		t.king = false;
		
	}
	
	
	private void move_options(Node t, String c) {
		//Predictions
    	
    	//Clear old tile list
		tile_list.clear();
		
		//Get x and y position of the clicked node
		xx = GridPane.getColumnIndex(t);
		yy = GridPane.getRowIndex(t);
		
		if(tn.state == 1 || tn.king) {
			if(xx + 1 < simDam.n && yy + 1 < simDam.n) {
				tile_list.add(((Tile)getNodeFromGridPane(simDam.gPane, xx + 1, yy + 1)));
			}
			if(xx - 1 >= 0 && yy + 1 < simDam.n) {
				tile_list.add(((Tile)getNodeFromGridPane(simDam.gPane, xx - 1, yy + 1)));
			}
			
		}
		
		if(tn.state == 2 || tn.king) {
			if(xx + 1 < simDam.n && yy - 1 >= 0) {
				tile_list.add(((Tile)getNodeFromGridPane(simDam.gPane, xx + 1, yy - 1)));
			}
			if(xx - 1 >= 0 && yy - 1 >= 0) {
				tile_list.add(((Tile)getNodeFromGridPane(simDam.gPane, xx - 1, yy - 1)));
			}
		}
    	
		
    	for(int i = 0; i < tile_list.size(); i++) {
			
    		if(tile_list.get(i).state == 0 && combo_state == false) {
				
				//Get the new tile
				nx = (GridPane.getColumnIndex(tile_list.get(i)));
				ny = (GridPane.getRowIndex(tile_list.get(i)));
				
				getNodeFromGridPane(simDam.gPane, nx, ny).setStyle("-fx-background-color: " + c);
			}
    		
    		
    		
    		if(tile_list.get(i).state == inv_turn_state) {
    			
    			//Get the new tile
				int nx1 = (GridPane.getColumnIndex(tile_list.get(i)) - xx) * 2 + xx;
				int ny1 = (GridPane.getRowIndex(tile_list.get(i)) - yy) * 2 + yy;
				
				if( nx1 < simDam.n && nx1 >= 0 && ny1 < simDam.n && ny1 >= 0 && ((Tile)getNodeFromGridPane(simDam.gPane, nx1, ny1)).state == 0) {
					getNodeFromGridPane(simDam.gPane, nx1, ny1).setStyle("-fx-background-color: " + c);
				}
    		}
    		
    	}
	}
	
	
	//Used to reset move options
	private void move_options_reset(Tile t) {
		
		//Reset predictions
		for(int i = 0; i < tile_list.size(); i++) {
			
			if(tile_list.get(i).state == 0) {
				
				//Get the new tile
				nx = (GridPane.getColumnIndex(tile_list.get(i)));
				ny = (GridPane.getRowIndex(tile_list.get(i)));
				
				getNodeFromGridPane(simDam.gPane, nx, ny).setStyle("-fx-background-color: " + ((Tile)(getNodeFromGridPane(simDam.gPane, nx, ny))).color);
				
			}
			
			//if(tile_list.get(i).state == inv_turn_state) {
    			
    			//Get the new tile
				int nx1 = (GridPane.getColumnIndex(tile_list.get(i)) - GridPane.getColumnIndex(t)) * 2 + GridPane.getColumnIndex(t);
				int ny1 = (GridPane.getRowIndex(tile_list.get(i)) - GridPane.getRowIndex(t)) * 2 + GridPane.getRowIndex(t);
				
				if( nx1 < simDam.n && nx1 >= 0 && ny1 < simDam.n && ny1 >= 0 && ((Tile)getNodeFromGridPane(simDam.gPane, nx1, ny1)).state == 0) {
					getNodeFromGridPane(simDam.gPane, nx1, ny1).setStyle("-fx-background-color: " + ((Tile)getNodeFromGridPane(simDam.gPane, nx1, ny1)).color);
				}
    		//}
				
    	}
		
		getNodeFromGridPane(simDam.gPane, GridPane.getColumnIndex(((Tile)t)),  GridPane.getRowIndex(((Tile)t))).setStyle("-fx-background-color: " + ((Tile)(getNodeFromGridPane(simDam.gPane, GridPane.getColumnIndex(((Tile)t)), GridPane.getRowIndex(((Tile)t))))).color);
		
	}
	
	
	
	private boolean check_neighbor(int xx, int yy) {
		
		//Clear old tile list
		tile_list.clear();
		
		if(tn.state == 1 || tn.king) {
			if(xx + 1 < simDam.n && yy + 1 < simDam.n) {
				tile_list.add(((Tile)getNodeFromGridPane(simDam.gPane, xx + 1, yy + 1)));
			}
			if(xx - 1 > 0 && yy + 1 < simDam.n) {
				tile_list.add(((Tile)getNodeFromGridPane(simDam.gPane, xx - 1, yy + 1)));
			}
		}
		
		if(tn.state == 2 || tn.king) {
			if(xx + 1 < simDam.n && yy - 1 > 0) {
				tile_list.add(((Tile)getNodeFromGridPane(simDam.gPane, xx + 1, yy - 1)));
			}
			if(xx - 1 > 0 && yy - 1 > 0) {
				tile_list.add(((Tile)getNodeFromGridPane(simDam.gPane, xx - 1, yy - 1)));
			}
		}
		
		
		
		//Check if the piece can jump over any tile
		boolean temp = false;
		
		for(int i = 0; i < tile_list.size(); i++) {
			if(tile_list.get(i).state == inv_turn_state) {
				
				//Get the new tile
				nx = (GridPane.getColumnIndex(tile_list.get(i)) - xx) * 2 + xx;
				ny = (GridPane.getRowIndex(tile_list.get(i)) - yy) * 2 + yy;
								
				Tile tt = ((Tile)getNodeFromGridPane(simDam.gPane, nx, ny));
				
				if(nx < simDam.n && nx >= 0 && ny < simDam.n && ny >= 0 && tt.state == 0) {
					
					temp = true;
				}
			} 
		}
		
		if(temp == true) {
			return true;
		}
		
		return false;
	}
	
	void animate(Node piece, int x, int y) {
		 double moveX = SimDam.tSize * -x;
		 double moveY = SimDam.tSize * -y;
		 double z;
		 z = SimDam.XD ? -5 : 0;
		 piece.setTranslateX(moveX);
		 piece.setTranslateY(moveY);
		 //piece.setTranslateZ(0);
		 Timeline timeline = new Timeline();
		 timeline.setCycleCount(1);
		 timeline.setAutoReverse(false);
		 timeline.setDelay(Duration.millis(0));
		 timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), new KeyValue (piece.translateXProperty(), 0)));
		 timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), new KeyValue (piece.translateYProperty(), 0)));
		 timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), new KeyValue (piece.translateZProperty(), z, Interpolator.TANGENT(Duration.millis(50), -40))));
		 timeline.setOnFinished(new EventHandler<ActionEvent>(){
			 public void handle(ActionEvent e) {
				 piece.setTranslateX(0);
				 piece.setTranslateY(0);
				 piece.setTranslateZ(z);
				 if(simDam.soundPlay) {
						MediaPlayer move = new MediaPlayer(click);
						move.play();
				 }
			 }
		 });
		 timeline.play();
	}
	
	void fade(Node piece, Node tile) {
		FadeTransition ft = new FadeTransition(Duration.millis(500), piece);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				((Tile) tile).getChildren().remove(j);				
			}
		});
	}

	void scale(Node piece, Node tile) {
		ScaleTransition ft = new ScaleTransition(Duration.millis(500), piece);
		ft.setFromX(1);
		ft.setFromY(1);
		ft.setFromZ(1);
		ft.setToX(0);
		ft.setToY(0);
		ft.setToZ(0);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				((Tile) tile).getChildren().remove(j);
				
			}
		});
	}
	
};