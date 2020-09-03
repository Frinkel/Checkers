package dam.controller;

import dam.view.SimDam;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class camControl {
	public static Rotate rotateX; 
	public static Rotate rotateZ;
	public static Rotate rotate2X;
	public static Rotate rotate2Z;
	public static Timeline setFrontWhite;
	public static Timeline setFrontBlack;
	public static Timeline setTop;
	public static Timeline setSide;
	public static double angle;
	
	public static EventHandler<KeyEvent> camCon;
	public static EventHandler<ScrollEvent> zoom;
	//Camera controls
	public static void setCamera(SubScene sub1, SubScene sub2, StackPane table, StackPane skybox, double size, int n) { 
			PerspectiveCamera camtable = new PerspectiveCamera();
			PerspectiveCamera camSky = new PerspectiveCamera();
			double pivot = SimDam.size/2;
			double pivot2 = SimDam.size/2;
			rotateX = new Rotate(-60, pivot, pivot, 0, new Point3D(1,0,0));
			rotateZ = new Rotate(90, pivot, pivot, pivot, new Point3D(0,0,1));
			rotate2X = new Rotate(-60, pivot2, pivot2, -pivot2, new Point3D(1,0,0));
			rotate2Z = new Rotate(90, pivot2, pivot2, -pivot2, new Point3D(0,0,1));
			rotate2X.angleProperty().bind(rotateX.angleProperty());
			rotate2Z.angleProperty().bind(rotateZ.angleProperty());
			table.getTransforms().addAll(rotateX, rotateZ);
			skybox.getTransforms().addAll(rotate2X, rotate2Z);
			camtable.setTranslateZ(-1000);
			camSky.setTranslateZ(size);
			camCon = new EventHandler<KeyEvent>() {
				public void handle(KeyEvent kE) {
					switch(kE.getCode()) {
						case W:
							camtable.setTranslateY(camtable.getTranslateY()-10);
							break;
						case S:
							camtable.setTranslateY(camtable.getTranslateY()+10);
							break;
						case A:
							camtable.setTranslateX(camtable.getTranslateX()-10);
							break;
						case D:
							camtable.setTranslateX(camtable.getTranslateX()+10);
							break;
						case Z:
							camtable.setTranslateZ(camtable.getTranslateZ()+10);
							break;
						case X:
							camtable.setTranslateZ(camtable.getTranslateZ()-10);
							break;
						case I:
							rotateX.setAngle(rotateX.getAngle()+10);
							break;
						case K:
							rotateX.setAngle(rotateX.getAngle()-10);
							break;
						case J:
							rotateZ.setAngle(rotateZ.getAngle()+10);
							break;
						case L:
							rotateZ.setAngle(rotateZ.getAngle()-10);
							break;
						case B:
							setFrontWhite.play();
							break;
					}
				}
				
			};	

			sub1.setCamera(camtable);
			sub2.setCamera(camSky);
			sub2.setOnKeyPressed(camCon);
			sub2.setFocusTraversable(true);
			
			//Set camera in front of white
			 setFrontWhite = new Timeline();
			 setFrontWhite.setDelay(Duration.millis(500));
			 setFrontWhite.getKeyFrames().add(new KeyFrame(Duration.millis(500),
			  new KeyValue (rotateZ.angleProperty(), angle)));
			 setFrontWhite.getKeyFrames().add(new KeyFrame(Duration.millis(500),
					  new KeyValue (rotateX.angleProperty(), -50)));

			//Set camera in front of black
			 setFrontBlack = new Timeline();
			 setFrontBlack.setDelay(Duration.millis(500));
			 setFrontBlack.getKeyFrames().add(new KeyFrame(Duration.millis(500),
			  new KeyValue (rotateZ.angleProperty(), 180)));
			 setFrontBlack.getKeyFrames().add(new KeyFrame(Duration.millis(500),
					  new KeyValue (rotateX.angleProperty(), -50)));
			
			//set camera on overview
			setTop = new Timeline();
			setTop.setDelay(Duration.millis(500));
			 setTop.getKeyFrames().add(new KeyFrame(Duration.millis(500),
					  new KeyValue (rotateX.angleProperty(), 0)));
			 
				//set camera on side
				setSide = new Timeline();
				setSide.setDelay(Duration.millis(500));
				 setSide.getKeyFrames().add(new KeyFrame(Duration.millis(500),
						  new KeyValue (rotateX.angleProperty(), -50)));
				 setSide.getKeyFrames().add(new KeyFrame(Duration.millis(500),
						  new KeyValue (rotateZ.angleProperty(), 90)));
				 
				 
				 
		zoom = new EventHandler<ScrollEvent>(){		
			public void handle(ScrollEvent e){
				camtable.setTranslateZ(camtable.getTranslateZ()+e.getDeltaY());
			}	
		};
		sub1.setOnScroll(zoom);
	
	}
	
	
	
	
		
	public static void setFront(){
		if(Controller.turn) setFrontWhite.play();
		else setFrontBlack.play();
		
	}
	
	
	
}