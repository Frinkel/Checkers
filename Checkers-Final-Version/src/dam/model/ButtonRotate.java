package dam.model;

import dam.controller.camControl;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;



public class ButtonRotate {
	
	
	public static CamMode buttState = CamMode.SIDE;
	
	public static StackPane makeButton(double tSize) {
	Cylinder top = new Cylinder(tSize/2.3, tSize/2);
	top.getTransforms().add(new Rotate(90, new Point3D(1,0,0)));
	top.setMaterial(new PhongMaterial(Color.DARKRED));
	top.setTranslateZ(tSize/3);

	Box bottom = new Box(tSize, tSize, tSize/2);	
	bottom.setMaterial(new PhongMaterial(Color.BLACK));
	bottom.setTranslateZ(tSize/2);
	
	Label text = new Label("Side View");
	text.setTextFill(Color.BLUE);
	text.setEffect(new Glow(10));
	text.setPickOnBounds(false);
	ScaleTransition textAni = new ScaleTransition(Duration.millis(500), text);
	textAni.setAutoReverse(true);
	textAni.setCycleCount(2);
	textAni.setByY(tSize/20);
	textAni.setByX(tSize/20);
	
	
	
	text.setTranslateZ(tSize/3-tSize/4-1);
	
	//animation
	 Timeline timeline = new Timeline();
timeline.setCycleCount(2);
	 timeline.setAutoReverse(true);
	 timeline.setDelay(Duration.millis(50));
	 timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500),
	  new KeyValue (top.translateZProperty(), top.getTranslateZ()+tSize/6)));
	 timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500),
			  new KeyValue (text.translateZProperty(), text.getTranslateZ()-tSize/6)));
	 
	

	 
	
	StackPane sp = new StackPane();
	sp.getChildren().addAll(bottom,top,text);
	EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			
			timeline.play();
			textAni.play();
			text.setRotate(-camControl.rotateZ.getAngle());
			switch(buttState) {
			case SIDE:
				text.setText("Rotate");
				buttState = CamMode.ROTATE;
				camControl.setFront();
				break;
			case ROTATE:
				camControl.setTop.play();
				text.setText("Top Veiw");
				buttState = CamMode.TOP;
				break;
			case TOP:
				camControl.setSide.play();
				text.setText("Side Veiw");
				buttState = CamMode.SIDE;
				break;
			}
			}
		};
	top.setOnMouseClicked(handler);
	text.setOnMouseClicked(handler);
	sp.setPickOnBounds(false);
	return sp;
	}
}