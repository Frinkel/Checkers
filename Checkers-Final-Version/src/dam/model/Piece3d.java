package dam.model;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import dam.view.SimDam;
import javafx.geometry.Point3D;


public class Piece3d extends Cylinder{
	public Color col;
	boolean team;
	public Piece3d(boolean team) {
		super(SimDam.size/SimDam.n*0.4, 10);
		setPickOnBounds(false);
		setTranslateZ(-5);
		this.team = team;
		Point3D axis = new Point3D(1,0,0);
		setRotationAxis(axis);
		setRotate(90);
		PhongMaterial mat = new PhongMaterial();
		if(team) mat.setDiffuseColor(Color.web("#211A21"));
		else mat.setDiffuseColor(Color.BLANCHEDALMOND);
		setMaterial(mat);
	}
}