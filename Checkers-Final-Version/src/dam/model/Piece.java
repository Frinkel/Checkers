package dam.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends Circle {
	public int id;
	public Color col;
	
	public Piece(double x, double y, double rad, Color c) {
		super(x, y, rad, c);
		this.col = c;
	}
}