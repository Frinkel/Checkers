package dam.model;

import dam.view.SimDam;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Crown {
	public static TriangleMesh mesh;
	
	
	public static void makeCrownMesh() {
		mesh = new TriangleMesh();
		float r = (float) (SimDam.size / SimDam.n*0.4);
		float h = r;
		float pf = ((float)Math.sqrt((r*r)/2)); //cos(pi/4)
		float ps = ((float)Math.sqrt(2+(Math.sqrt(2)))/2)*r; //cos(pi/6)
		float pt = ((float)Math.sqrt(2-(Math.sqrt(2)))/2)*r; 			  //cos(pi(3)
		
		mesh.getTexCoords().addAll(0,0);
		mesh.getPoints().addAll(
				//basepoints
				0, 0, 0, //center - 0
				0, r, 0, //n -1
				pt, ps, 0, //nne -2
				pf, pf, 0, //ne-3
				ps, pt, 0, //nee -4
				r, 0, 0, //e- 5
				ps, -pt , 0, //see -6
				pf, -pf, 0, //se -7
				pt, -ps, 0, //sse -8
				0, -r, 0, //s -9
				-pt, -ps, 0, //ssv -10
				-pf, -pf, 0, //sv -11
				-ps, -pt, 0, //svv -12
				-r, 0, 0, // v -13
				-ps, pt, 0, //nvv -14
				-pf, pf, 0, // nv -15
				-pt, ps, 0, // nnv -16
				
				//highebase
				0, 0, -h/2, //center - 17
				0, r, -h/2, //n -18
				pt, ps, -h/2, //nne -19
				pf, pf, -h/2, //ne-20
				ps, pt, -h/2, //nee -21
				r, 0, -h/2, //e- 22
				ps, -pt , -h/2, //see -23
				pf, -pf, -h/2, //se -24
				pt, -ps, -h/2, //sse -25
				0, -r, -h/2, //s -26
				-pt, -ps, -h/2, //ssv -27
				-pf, -pf, -h/2, //sv -28
				-ps, -pt, -h/2, //svv -29
				-r, 0, -h/2, // v -30
				-ps, pt, -h/2, //nvv -31
				-pf, pf, -h/2, // nv -32
				-pt, ps, -h/2,  // nnv -33
				
				//top
				pt, ps, -h, //nne -34
				ps, pt, -h, //nee -35
				ps, -pt , -h, //see -36
				pt, -ps, -h, //sse -37
				-pt, -ps, -h, //ssv -38
				-ps, -pt, -h, //svv -39
				-ps, pt, -h, //nvv -40
				-pt, ps, -h  // nnv -41
			);
		
		mesh.getFaces().addAll(
				//base
				0,0, 1,0, 2,0,
				0,0, 2,0, 3,0,
				0,0, 3,0, 4,0,
				0,0, 4,0, 5,0,
				0,0, 5,0, 6,0,
				0,0, 6,0, 7,0,
				0,0, 7,0, 8,0,
				0,0, 8,0, 9,0,
				0,0, 9,0, 10,0,
				0,0, 10,0, 11,0,
				0,0, 11,0, 12,0,
				0,0, 12,0, 13,0,
				0,0, 13,0, 14,0,
				0,0, 14,0, 15,0,
				0,0, 15,0, 16,0,
				0,0, 16,0, 1,0,
				
				//sides
				1,0, 18,0, 33,0,
				1,0, 18,0, 2,0,
				2,0, 19,0, 18,0,
				2,0, 19,0, 3,0,
				3,0, 20,0, 19,0,
				3,0, 20,0, 4,0,
				4,0, 21,0, 20,0,
				4,0, 21,0, 5,0,
				5,0, 22,0, 21,0,
				5,0, 22,0, 6,0,
				6,0, 23,0, 22,0,
				6,0, 23,0, 7,0,
				7,0, 24,0, 23,0,
				7,0, 24,0, 8,0,
				8,0, 25,0, 24,0,
				8,0, 25,0, 9,0,
				9,0, 26,0, 25,0,
				9,0, 26,0, 10,0,
				10,0, 27,0, 26,0,
				10,0, 27,0, 11,0,
				11,0, 28,0, 27,0,
				11,0, 28,0, 12,0,
				12,0, 29,0, 28,0,
				12,0, 29,0, 13,0,
				13,0, 30,0, 29,0,
				13,0, 30,0, 14,0,
				14,0, 31,0, 30,0,
				14,0, 31,0, 15,0,
				15,0, 32,0, 31,0,
				15,0, 32,0, 16,0,
				16,0, 33,0, 32,0,
				16,0, 33,0, 1,0,
				//pointybits
				18,0, 19,0, 34,0,
				34,0, 19,0, 20,0,
				20,0, 21,0, 35,0,
				35,0, 21,0, 22,0,
				22,0, 23,0, 36,0,
				36,0, 23,0, 24,0,
				24,0, 25,0, 37,0,
				37,0, 25,0, 26,0,
				26,0, 27,0, 38,0,
				38,0, 27,0, 28,0,
				28,0, 29,0, 39,0,
				39,0, 29,0, 30,0,
				30,0, 31,0, 40,0,
				40,0, 31,0, 32,0,
				32,0, 33,0, 41,0,
				41,0, 33,0, 18,0
			);
	}
		public static MeshView MakeCrown(Color c) {
			MeshView crown = new MeshView(mesh);
			PhongMaterial mat = new PhongMaterial();
			mat.setDiffuseColor(c);
			crown.setDrawMode(DrawMode.FILL);
			crown.setMaterial(mat);
			crown.setCullFace(CullFace.NONE);
			crown.setTranslateZ(-1);
			return crown;
		}
}
