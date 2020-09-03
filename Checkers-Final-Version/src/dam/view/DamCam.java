package dam.view;

import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DamCam extends PerspectiveCamera{
	DamCam(){
		super();
		Point3D axis = new Point3D(0,0,0);
		setRotationAxis(axis);
		SimDam.gPane.setOnKeyPressed(camCon);
	}	
	EventHandler<KeyEvent> camCon = new EventHandler<KeyEvent>() {
			public void handle(KeyEvent kE) {
				if(kE.getCode() == KeyCode.A) {
					setTranslateY(getTranslateY()+10);
					System.out.print("jj");
				}
			}
		};
}