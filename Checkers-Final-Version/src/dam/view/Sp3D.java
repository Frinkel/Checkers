package dam.view;


import dam.controller.camControl;
import javafx.geometry.Pos;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;

public class Sp3D {

	public static StackPane makeSp3D(double size, int n) {
		StackPane table = new StackPane();
		table.setPickOnBounds(false);
		table.setAlignment(Pos.TOP_LEFT);
		table.getChildren().addAll(Table.makeTable(size, n), SimDam.gPane);
		SimDam.gPane.setTranslateZ(-1);
	    SubScene subTable = new SubScene(table, size, size, true, SceneAntialiasing.BALANCED);
		
	    StackPane skybox = Skybox.makeSkybox(size);
 	    SubScene subSky = new SubScene(skybox, size, size, true, SceneAntialiasing.BALANCED);
 	    StackPane sp = new StackPane();
 	    sp.getChildren().addAll(subSky, subTable);
 	    
 	    camControl.setCamera(subTable, subSky, table, skybox, size, n);
 	    sp.setPickOnBounds(false);
 	    return sp;
	}
}