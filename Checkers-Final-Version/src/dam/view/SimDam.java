package dam.view;

import java.io.File;

import dam.controller.Controller;
import dam.controller.MiniMaxAI;
import dam.controller.camControl;
import dam.model.Crown;
import dam.model.Piece;
import dam.model.Piece3d;
import dam.model.Tile;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SimDam extends Application {
	
	public Scene nameMenu, settingsMenu, mainMenu, victory;
	public static Scene playScene;
	public static int n = 8;
	
	public static String p1_name;
	public static String p2_name;
	public static int p1Pieces;
	public static int p2Pieces;
	public Stage stage;
	public Label l1, l2, l3, l4, l5, l6, l7;
	public TextField tf3;
	
	public File file1;
	public static Image img1;
	public BackgroundImage bi;
	
	public Controller controller;
	
	
	public BorderPane bp1, bp2, bp3, bp4;
	public static GridPane gPane;
	public GridPane gridPane;
	public VBox vb3, vb4;
	
	public Tile t;
	public Node pieceBlack, pieceWhite;
	
	public Button btn_mainMenu;
	public CheckBox cb1, cb2, cb3, cb4;
	public Slider aiSlide;
	
	public Boolean soundPlay = false;
	public Boolean timer = false;
	public Boolean cancel = false;
	public Boolean AI_on = false;
	private String btnStyle = "-fx-border-color: BLACK; -fx-border-width: 2; -fx-border-radius: 5;" + "-fx-background-color: transparent; /* 12 */-fx-text-fill: #FFFFFF";
	public static int size = 500;
	public static double tSize = size/n;
	public int time;
	public static boolean XD = true;
	
	public String normalFont = "";
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override 
	public void start(Stage stage) {
		
		//Get the stage
		this.stage = stage;
		
		//Create an instance of the controller
		controller = new Controller();
		controller.simDam = this;
		
		//Get the piece colors
		pieceBlack = controller.pieceBlack;
		pieceWhite = controller.pieceWhite;

		//Creating a Grid Pane 
		gridPane = new GridPane(); 
		gPane = gridPane;
		gridPane.setPickOnBounds(false);
		gridPane.setStyle("-fx-background-color: TRANSPARENT;");
		
		for(int i = 0; i < n; i++) {
		    for(int j = 0; j < n; j++) {
		    	  			    	  
		    	t = new Tile((i+j) % 2 == 0, i, j, false);
		    	
		    	//Init pieces
		    	if((i+j) % 2 == 0 && j <= 2) {
		    		
		    		if(XD) { 
		    			pieceBlack = new Piece3d(true);
		    		} else {
		    			pieceBlack = new Piece(25, 25, tSize*0.4, Color.BLACK);
		    		}
		    		
		    		pieceBlack.setPickOnBounds(false);
			   	  	t.getChildren().add(pieceBlack);
			   	  	t.state = 1;
			   	  	
			   	  	controller.p1_pieces++;	  
		    	}
			  
			 	if((i+j) % 2 == 0 && j >= n - 3) {
			 		
				 	if(XD) { 
				 		pieceWhite = new Piece3d(false);
				 	} else { 
				 		pieceWhite = new Piece(25, 25, tSize*0.4, Color.BEIGE);
				 	}
				    pieceBlack.setPickOnBounds(false);
				  	t.getChildren().add(pieceWhite);
				  	t.state = 2;
					
				  	//Increment
				  	
				  	controller.p2_pieces++;
		    	  
		    	}
		    	
		    	  
		    	  
		 		gridPane.add(t, i, j);
		    	  
		    }
		}
	      
	      
		//Get the controller method
		
		//Reset turn
	    Controller.turn = true;
		  
		  
		//Setting size for the pane  
		gridPane.setMinSize(size, size); 
		
		//Get the background image
	    file1 = new File("media/wood.jpg");
	    img1 = new Image (file1.toURI().toString());
	    bi = new BackgroundImage(img1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		
		//------------------------------------------------------------------Main Menu
		mainMenu();
	    
	    //---------------------------------------------------Name Menu
	    nameMenu();
	    
	    //Settings menu
	    settingsMenu();
	    	    
	    //-----------------------------------------Main Scene
	    mainScene();
	    
	    //----------------------- Victory Scene
	    victoryScene();	    
	    
	    //Property to resize window, false
	    stage.setResizable(false);
		
		//Setting title to the Stage 
		stage.setTitle("Dam");
		  
		//Adding scene to the stage - default to "scene" else "mainMenu"
		stage.setScene(playScene);
		if(p1_name == null && p2_name == null) {
			stage.setScene(mainMenu);
		}
		     
		//Displaying the contents of the stage 
		stage.show(); 
		
		
		//Button Handlers
		
		//Set actions for mainMenu button
		btn_mainMenu.setOnAction(e -> {
			tf3.setText("Fx. 180 seconds");
			cancel = true;
		
			stage.setScene(mainMenu);
			
			//Check if the sound is on
			if(soundPlay) {
				cb1.setSelected(true);
			}
			if(timer) {
				cb2.setSelected(true);
			}
			if(XD) {
				cb3.setSelected(true);
			}
			if(AI_on) {
				cb4.setSelected(true);
			}
			
		});
	    
    
	}
	
	
	public void nameMenu() {
		//-----------------------------------------Name Menu
	    bp2 = new BorderPane();
	    VBox vb1 = new VBox(10);
	    VBox vb2 = new VBox(10);
	    bp2.setLeft(vb1);
	    bp2.setRight(vb2);
	    Label vs = new Label("VS");
	    vs.setFont(new Font(normalFont, 30));
	    vs.setStyle("-fx-text-fill: #FFFFFF;");
	    bp2.setCenter(vs);
	    bp2.setMinSize(size+size/3, size+size/3);
	    nameMenu = new Scene(bp2);
	    bp2.setBackground(new Background(bi));
	      
	    l1 = new Label("Player 1: ");
	    l1.setStyle("-fx-text-fill: #FFFFFF;");
	    l1.setFont(new Font("Blackadder ITC", 30));
	    l2 = new Label("Player 2: ");
	    l2.setStyle("-fx-text-fill: #FFFFFF;");
	    l2.setFont(new Font("Blackadder ITC", 30));
	    TextField tf1 = new TextField();
	    TextField tf2 = new TextField();
	    tf1.setStyle("-fx-focus-color: transparent;");
	    tf2.setStyle("-fx-focus-color: transparent;");
	    vb1.setAlignment(Pos.CENTER_RIGHT);
	    vb2.setAlignment(Pos.CENTER_LEFT);
	    VBox.setMargin(l1, new Insets(0, 50, 0, 0));
	    VBox.setMargin(l2, new Insets(0, 0, 0, 60));
	    VBox.setMargin(tf1, new Insets(0, 25, 0, 25));
	    VBox.setMargin(tf2, new Insets(0, 25, 0, 25));
	    
	    Button btn_go = new Button("Go!");
	    btn_go.setStyle(btnStyle);
	    bp2.setBottom(btn_go);
	    BorderPane.setAlignment(btn_go, Pos.CENTER);
	    BorderPane.setMargin(btn_go, new Insets(0, 0, 10, 0));
	    BorderPane.setMargin(vs, new Insets(52, 0, 0, 0));
	    
	    vb1.getChildren().addAll(l1, tf1);
	    vb2.getChildren().addAll(l2, tf2);
	    
	    btn_go.setOnAction(e -> {    	
	    	//Get custom names for players
	    	p1_name = tf1.getText();
	    	p2_name = tf2.getText();
	    	
	    	//Default names for players
	    	if(tf1.getText().isEmpty()) {
	    		p1_name = "Player 1";
	    	}
	    	if(tf1.getText().isEmpty()) {
	    		p2_name = "Player 2";
	    	}
	    	
	    	//Check if sound is enabled
		    if(cb1.isSelected()) {
	    		soundPlay = true;
	    	} else {
	    		soundPlay = false;
	    	}
		    
		    
		    //Get timer
		    if(tf3.getText().equals("")) {
	    		controller.seconds1 = controller.maxTime;
			    controller.seconds2 = controller.maxTime;
			    if(cb2.isSelected()) {
			    	timer = true;
			    	l6.setText(p2_name + " time " + controller.seconds2);
			    	l7.setText(p1_name + " time " + controller.seconds1);
			    	controller.doTime();
			    }else {
			    	timer = false;
			    }
	    	}else {
		    	String s = tf3.getText();
		    	s = s.replaceAll("[^0-9]", "");
		    	time = Integer.parseInt(s);
		    	controller.seconds1 = time;
			    controller.seconds2 = time;
			    
			    
			    //Check if timer is enabled and activate timer
			    if(cb2.isSelected()) {
			    	
			    	timer = true;
			    	cancel = false;
			    	l6.setText(p2_name + " time " + controller.seconds2);
			    	l7.setText(p1_name + " time " + controller.seconds1);
			    	controller.doTime();
			    }else {
			    	timer = false;
			    }
	    	}
		    
		    //Check if 3d is enabled
		    if(cb3.isSelected()) XD = true;
		    else XD= false;
		    
		    //Check if AI is enabled
		    if(cb4.isSelected()){
		    	AI_on = true;
		    	MiniMaxAI.d = (int) aiSlide.getValue();
		    }else {
		    	AI_on = false;
		    }
		    
		    //Change scene
		    stage.setScene(playScene);
	    	start(stage);
	    	
    	});
	}
	
	public void settingsMenu() {
		Button btn_back = new Button("Back");
	    BorderPane bp5 = new BorderPane();
	    settingsMenu = new Scene(bp5);
	    bp5.setPrefSize(size+size/3, size+size/3);
	    VBox vb5 = new VBox();
	    HBox hb1 = new HBox();
	    HBox hb2 = new HBox();
	    bp5.setCenter(vb5);
	    bp5.setBottom(btn_back);
	    btn_back.setStyle(btnStyle);
	    BorderPane.setAlignment(btn_back, Pos.CENTER);
	    BorderPane.setMargin(btn_back, new Insets(0, 0, 10, 0));
	    
	    vb5.setAlignment(Pos.CENTER_LEFT);
	    BorderPane.setMargin(vb5, new Insets(0, 0, 0, size/2-25));
	    vb5.setSpacing(10);
	    hb1.setSpacing(5);
	    
	    //Checkbox for sound
	    cb1 = new CheckBox("Sound");
	    cb1.setStyle(btnStyle); 
	    cb1.setFont(new Font("normalFont Chancery", 15));
	    
	    tf3 = new TextField("Fx. 180 seconds");
	    tf3.setFont(new Font(normalFont, 9.3));
	    
	    //Checkbox for timer
	    cb2 = new CheckBox("Timer");
	    cb2.setStyle(btnStyle);
	    cb2.setFont(new Font(normalFont, 15));
	    tf3.setStyle(btnStyle);
	    hb1.getChildren().addAll(cb2, tf3);
	    vb5.getChildren().addAll(cb1, hb1);
	    tf3.setOnMouseClicked(e -> tf3.setText(""));
	    tf3.setMaxWidth(82);
	    
	    //Checkbox for 3d
	    cb3 = new CheckBox("3D");
	    cb3.setStyle(btnStyle);
	    cb3.setFont(new Font(normalFont, 15));
	    vb5.getChildren().add(cb3);
	    
	    //Checkbox for AI
	    cb4 = new CheckBox("AI");
	    cb4.setStyle(btnStyle);
	    cb4.setFont(new Font(normalFont, 15));
	    aiSlide = new Slider();
	    aiSlide.setPrefHeight(10);
	    aiSlide.setShowTickMarks(true);
	    aiSlide.setShowTickLabels(true);
	    aiSlide.setMin(2);
	    aiSlide.setValue(4);
	    aiSlide.setMax(6);
	    aiSlide.setMinorTickCount(0);
	    aiSlide.setMajorTickUnit(2);
	    aiSlide.setSnapToTicks(true);
	    aiSlide.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n == 2) return "Easy";
                if (n == 4) return "Medium";
                return "Hard";
            }

            @Override
            public Double fromString(String string) {
                switch (string) {
                    case "Easy":
                        return 0d;
                    case "Medium":
                        return 1d;
                    case "Hard":
                        return 2d;
                    default:
                        return 1d;
                }
            }
        });
	    
	    hb2.setSpacing(5);
	    hb2.getChildren().addAll(cb4, aiSlide);
	    vb5.getChildren().add(hb2);
	    
	    bp1.setBackground(new Background(bi));
	    bp5.setBackground(new Background(bi));
	    
	  //Set the actions for back button
	    btn_back.setOnAction(e -> stage.setScene(mainMenu));
	}

	public void mainMenu() {
		bp1 = new BorderPane();  
	    bp1.setMinSize(size+size/3, size+size/3);
	    mainMenu = new Scene(bp1);
	    Button btn_play = new Button("Play");
	    btn_play.setStyle("-fx-border-color: transparent;-fx-border-width: 0; -fx-background-radius: 0;" + "-fx-background-color: transparent; /* 12 */-fx-text-fill: #FFFFFF");
	    btn_play.setFont(new Font(normalFont, 80));
	    
	    Button btn_settings = new Button("Settings");
	    btn_settings.setStyle(btnStyle);
	    
	    bp1.setCenter(btn_play);
	    
	    bp1.setBottom(btn_settings);
	    BorderPane.setAlignment(btn_settings, Pos.CENTER);
	    BorderPane.setMargin(btn_settings, new Insets(0, 0, 10, 0));
	    
	  //Set actions for play button
	    btn_play.setOnAction(e -> {
	    	
	    	bp2.setTop(btn_mainMenu);
	    	BorderPane.setAlignment(btn_mainMenu, Pos.CENTER);
		    BorderPane.setMargin(btn_mainMenu, new Insets(5, 0, 0, 0));
	    	stage.setScene(nameMenu);
	    });
	    
	  //Set actions for settings button
	    btn_settings.setOnAction(e -> {
	    	stage.setScene(settingsMenu);
	    });
	}
		
	public void mainScene() {
		bp3 = new BorderPane();
	    vb3 = new VBox();
	    vb4 = new VBox();
	    btn_mainMenu = new Button("Main Menu");
	    btn_mainMenu.setStyle(btnStyle);
	    l3 = new Label(p2_name + " has " + controller.p1_pieces + " pieces left");
	    p1Pieces = controller.p1_pieces;
	    p2Pieces = controller.p2_pieces;
	    l4 = new Label(p1_name + " has " + controller.p2_pieces + " pieces left");
	    
	    playScene = new Scene(bp3, size+size/3, size+size/3, true);
	    
	    controller.Control();
	    
	    l6 = new Label("");
	    l7 = new Label("");
	    
	    l3.setStyle("-fx-text-fill: #FFFFFF;");
	    l4.setStyle("-fx-text-fill: #FFFFFF;");
	    l6.setStyle("-fx-text-fill: #FFFFFF;");
	    l7.setStyle("-fx-text-fill: #FFFFFF;");
	    
	    //VBox 3
	    vb3.getChildren().addAll(btn_mainMenu, l3, l6);
	    vb3.setAlignment(Pos.CENTER);
	    vb3.setBackground(new Background(bi));
	    VBox.setMargin(l3, new Insets(5, 0, 0, 0));
	    
	    //VBox 4
	    vb4.getChildren().addAll(l7, l4);
	    vb4.setAlignment(Pos.CENTER);
	    vb4.setBackground(new Background(bi)); 
	    bp3.setPrefWidth(500);
	    bp3.setTop(vb3);
	    bp3.setCenter(gridPane);
	    bp3.setBottom(vb4);
	    gridPane.setAlignment(Pos.CENTER);
	    
	    if(XD) {
	    	bp3.setCenter(Sp3D.makeSp3D(size, n));
	    	playScene.setOnKeyPressed(camControl.camCon);
	    	Crown.makeCrownMesh();
	    }else bp3.setCenter(gridPane);
	    bp3.setBackground(new Background (bi));
	    BorderPane.setMargin(l4, new Insets(5, 0, 5, 0));
	    BorderPane.setMargin(vb3, new Insets(5, 0, 0, 0));
	    BorderPane.setMargin(vb4, new Insets(0, 0, 5, 0));
	    BorderPane.setAlignment(l4, Pos.CENTER);
	}
	
	public void victoryScene() {
		bp4 = new BorderPane();
	    bp4.setBackground(new Background(bi));
	    bp4.setPrefSize(size+size/3, size+size/3);
	    victory = new Scene(bp4);
	    l5 = new Label();
	    l5.setStyle("-fx-border-color: transparent;-fx-border-width: 0; -fx-background-radius: 0;"
	    		+ "-fx-background-color: transparent; /* 12 */-fx-text-fill: #FFFFFF");
	    l5.setFont(new Font(normalFont, 40));
	    Button btn_rematch = new Button("Rematch");
	    btn_rematch.setStyle(btnStyle);
	   
	    VBox vb4 = new VBox();
	    vb4.getChildren().addAll(l5, btn_rematch);
	    vb4.setSpacing(40);
	    bp4.setCenter(vb4);
	    vb4.setAlignment(Pos.CENTER);
	    
	    //Set actions for rematch button
	    btn_rematch.setOnAction(e -> {
	    	
	    	if(!AI_on) {
	    		String temp = p1_name;
	    		p1_name = p2_name;
	    		p2_name = temp;
	    	}
	    	
	    	//Activate timer
		    controller.seconds1 = time;
		    controller.seconds2 = time;
		    controller.doTime();
	    	
	    	stage.setScene(playScene);
	    	start(stage);
	    });
	}
}