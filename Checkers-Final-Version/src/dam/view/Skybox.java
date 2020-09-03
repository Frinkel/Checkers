package dam.view;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
public class Skybox {
	public static StackPane makeSkybox(double d) {
		double s = d;
		Rectangle wall1 = new Rectangle(s,s);
		wall1.setRotationAxis(new Point3D(1,0,0));
		wall1.setRotate(90);
		wall1.setTranslateY(s/2);
		wall1.setTranslateZ(-s/2);
		wall1.setFill(makeFill("media/mat/walldoor.jpg"));
		
		Rectangle wall2 = new Rectangle(s,s);
		wall2.setRotationAxis(new Point3D(1,0,0));
		wall2.setRotate(90);
		wall2.setTranslateY(-s/2);
		wall2.setTranslateZ(-s/2);
		wall2.setFill(makeFill("media/mat/wallwindow.jpg"));

		Rectangle wall3 = new Rectangle(s,s);
		wall3.setRotationAxis(new Point3D(0,1,0));
		wall3.setRotate(90);
		wall3.setTranslateX(-s/2);
		wall3.setTranslateZ(-s/2);
		wall3.setFill(makeFill("media/mat/wallwindow.jpg"));
		wall3.getTransforms().add(new Rotate(90, s/2, s/2 , 0));

		Rectangle wall4 = new Rectangle(s,s);
		wall4.setRotationAxis(new Point3D(0,1,0));
		wall4.setRotate(90);
		wall4.setTranslateX(s/2);
		wall4.setTranslateZ(-s/2);
		wall4.setFill(makeFill("media/mat/walldoor.jpg"));
		
		Rectangle floor = new Rectangle(s,s);
		floor.setFill(makeFill("media/mat/floor.jpg"));  
		floor.setTranslateZ(-2);
		Rectangle ciel = new Rectangle(s,s);
		ciel.setTranslateZ(-s);
		ciel.setFill(makeFill("media/mat/egg.jpeg"));
		
		StackPane sp = new StackPane();
		sp.setAlignment(Pos.CENTER);
		sp.getChildren().addAll(wall1, wall2, wall3, wall4, floor, ciel);
		return sp;
	}
	
	public static Paint makeFill(String file) {
		try {
		Image image = new Image(new FileInputStream(file));
		ImagePattern mat = new ImagePattern(image);
		return mat;
		}catch(FileNotFoundException e) {
			System.out.print("file not found");
			return Color.PINK;
		}
	}
}