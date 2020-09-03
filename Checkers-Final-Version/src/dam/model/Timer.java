package dam.model;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Timer {
	public static TriangleMesh makeTimerMesh(float h){
		TriangleMesh mesh = new TriangleMesh();
		mesh.getTexCoords().addAll(0,0);
		mesh.getPoints().addAll(
				0, 0, 0,
				h*3, 0, 0,
				0, -h, 0,
				h*3, -h, 0,
				0, 0, -h,
				h*3, 0, -h
				);
		
		mesh.getFaces().addAll(
				0,0, 4,0, 2,0,
				1,0, 3,0, 5,0,
				2,0, 4,0, 3,0,
				3,0, 4,0, 5,0,
				0,0, 4,0, 1,0,
				1,0, 4,0, 5,0
				);
		return mesh;
	}
	
	public static MeshView makeTimer(TriangleMesh mesh) {
		MeshView timer = new MeshView(mesh);
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.PINK.darker().darker().darker().darker().darker().darker());
		timer.setMaterial(mat);
		timer.setCullFace(CullFace.NONE);
		timer.setDrawMode(DrawMode.FILL);
		return timer;
	}
}