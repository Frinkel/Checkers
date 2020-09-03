package dam.model;


import dam.view.SimDam;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Tile extends StackPane {
	int x;
	int y;
	public int state;
	public String color;
	public boolean king = false;
	
	EventHandler<MouseEvent>  eventHandler = new EventHandler<MouseEvent>() {
		public void handle (MouseEvent e){
			setStyle("-fx-background-color: yellow;");
		}	
	};
	
	EventHandler<MouseEvent>  mouseleave = new EventHandler<MouseEvent>() {
		public void handle (MouseEvent e){
			setStyle("-fx-background-color:" + color );
		}	
	};
			
	public Tile(boolean color, int x, int y, boolean king){
		this.x = x;
		this.y = y;
		this.king = king;
		
		if(color == true){
			 setStyle("-fx-background-color: SADDLEBROWN;"); 
			 this.color = " SADDLEBROWN;";
		}else{ 
			setStyle("-fx-background-color: PERU;"); 
			this.color = " PERU;";
		}
				
		setMouseTransparent(false);
		setPrefSize(SimDam.size/SimDam.n, SimDam.size/SimDam.n);
		

	}
}	