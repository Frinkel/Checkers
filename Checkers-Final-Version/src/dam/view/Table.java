package dam.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import dam.model.ButtonRotate;
import dam.model.Timer;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;

public class Table{
	Box floor;
	SubScene sub;
	public static Label point1, point2, time1, time2;
	
	public static StackPane makeTable(double rSize, int n) {
		double mid = rSize/2;	
	
		//box
		Box box = new Box(rSize+1, rSize+1, rSize/n+1);
		PhongMaterial material = new PhongMaterial();
		try {
			material.setDiffuseMap(new Image(new FileInputStream("media/mat/wood.jpeg")));
		}catch(FileNotFoundException e) {
			System.out.print("file not found");
		}	
		box.setMaterial(material);
		//sp.setStyle("-fx-background-color: PERU;");
		box.setTranslateZ((rSize/n)/2);
		box.setTranslateX(-0.5);
		box.setTranslateY(-0.5);
	
	
	
		//table
		double tableW = rSize*2.5;
		double tableH = rSize/n*1.5;
		double tableL = rSize*2;
		double tableX = -tableW/2 + rSize/2;
		double tableY = -rSize/2;
		double tableZ = rSize/n*1.5;
		Box table = new Box(tableW, tableL, tableH);
		table.setTranslateX(tableX);
		table.setTranslateY(tableY);
		table.setTranslateZ(tableZ);
		PhongMaterial tablemat = new PhongMaterial();
	
		try {
			tablemat.setDiffuseMap(new Image(new FileInputStream("media/mat/table.png")));
		}catch(FileNotFoundException e) {
			System.out.print("file not found");
		}	
		table.setMaterial(tablemat);
		double legside = rSize/4;
		Box leg1 = makeLeg(tableX+4, tableY+5, tableZ*1.5, rSize, legside,tablemat);
		Box leg2 = makeLeg(tableW+tableX-legside, tableY, tableZ*1.5, rSize,legside, tablemat);
		Box leg3 = makeLeg(tableX, -tableY+rSize-legside, tableZ*1.5, rSize,legside, tablemat);
		Box leg4 = makeLeg(tableW+tableX-legside, -tableY+rSize-legside, tableZ*1.5, rSize,legside, tablemat);
		
		
		//Paper
		StackPane paper;
			paper = paper(rSize, n, tableZ, tableH);
	
		//clock
		StackPane clock= clocks(rSize/n, rSize);
		StackPane sp = new StackPane();
		
		
		//Button
		StackPane button = ButtonRotate.makeButton(rSize/n);
		
		
		sp.setAlignment(Pos.TOP_LEFT);
		sp.getChildren().addAll(box,  leg1, leg2, leg3, leg4, table, paper, clock, button);
		sp.setPickOnBounds(false);
		return sp;
	}
	
	public static Box makeLeg(double tablex, double tabley, double tablez, double size, double side, Material mat){
			Box leg = new Box(side, side, size*3);
			leg.setTranslateZ(size*3/2+ tablez);
			leg.setTranslateX(tablex);
			leg.setTranslateY(tabley);
			leg.setMaterial(mat);
			return leg;
		}
	
	//ScorePaper
	public static StackPane paper(double size,double n, double tableZ, double tableH) {
		double paperW = size*0.7;
		double paperL = size*0.5;
		Font f = new Font(20);
		try {
		f = Font.loadFont(new FileInputStream("media/mat/Daniel-Bold.otf"), 30);
		}catch (FileNotFoundException e) {
		System.out.println("font not");
		}
		Label name1 = new Label(SimDam.p1_name);
		name1.setTranslateX(-paperW/3);
		name1.setTranslateY(-paperL/4);
		name1.setFont(f);
		Label name2 = new Label(SimDam.p2_name);
		name2.setTranslateX(paperL/3);
		name2.setTranslateY(-paperL/4);
		name2.setFont(f);
		point1 = new Label(""+ SimDam.p1Pieces);
		point1.setTranslateX(-paperW/3);
		point1.setFont(f);
		point2 = new Label(""+SimDam.p2Pieces);
		point2.setTranslateX(paperL/3);
		point2.setFont(f);
		StackPane scorepaper = new StackPane();
		Rectangle paper = new Rectangle(paperW, paperL);
		scorepaper.setRotate(-98);
		scorepaper.setTranslateY(-size/2);
		scorepaper.setTranslateX(size/10);
		scorepaper.setTranslateZ(tableZ-tableH/2-1);
		paper.setFill(Skybox.makeFill("media/mat/paper.png"));
		scorepaper.getChildren().addAll(paper, name1, name2, point1, point2);
		scorepaper.setPickOnBounds(false);
		return scorepaper;
	}
	
	public static StackPane clocks(double h, double bSize) {
		TriangleMesh mesh = Timer.makeTimerMesh((float) h);
		MeshView cB1 = Timer.makeTimer(mesh);
		MeshView cB2 = Timer.makeTimer(mesh);
		cB2.setTranslateX(-h*4);
		StackPane sp = new StackPane();
		time1 = new Label("00:00");
		time2 = new Label("00:00");
		time1.setStyle("-fx-background-color: DARKOLIVEGREEN; -fx-text-fill: #FF0000;");
		time2.setStyle("-fx-background-color: DARKOLIVEGREEN; -fx-text-fill: #FF0000;");
		time1.setRotate(187);
		time2.setRotate(173);
		Rotate r = new Rotate(45, new Point3D(1,0,0));
		time2.getTransforms().add(r);
		time1.getTransforms().add(r);
		time1.setTranslateZ(-h*3/4-10);
		time2.setTranslateZ(-h*3/4-10);
		time2.setTranslateX(-h*4);
		cB1.setRotate(7);
		cB2.setRotate(-6);
		//ime1.setTranslateY(-h/2);
		Font f = new Font(20);
		try {
		f = Font.loadFont(new FileInputStream("media/mat/digital-7.ttf"), h/1.2);
		}catch (FileNotFoundException e) {
		System.out.println("font not");
		}
		
		time1.setFont(f);
		time2.setFont(f);
		time1.setEffect(new Glow(0.8));
		time2.setEffect(new Glow(0.8));
		sp.setTranslateZ(h*0.8);
		sp.setRotate(90);
		sp.setTranslateX(-bSize*1.5);
		sp.setTranslateY(-bSize/3+h/1.5);
		sp.getChildren().addAll(cB1, cB2,time1, time2);
		sp.setPickOnBounds(false);
		return sp;
	}
		
		
	
	}