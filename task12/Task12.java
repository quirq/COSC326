package task12;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Task12 {

	private HilbertCurve curve;
	private DrawingPanel panel;
	private double globalRatio;
	private static final int LEFT = 90, RIGHT = -90;

	private enum DIRECTION {UP, RIGHT, DOWN, LEFT}

	public Task12(){
		int n = 0;
		double ratio = 0.0;
		double maxScale = 0.0;

		//Take in parameter n, and optional parameter r
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()){
			String[] input = scanner.nextLine().split(" ");
			n = Integer.parseInt(input[0]);
			if(input.length == 2){
				ratio = Double.parseDouble(input[1]);
			} else {
				ratio = 1.0;
			}

			maxScale = Math.pow(2, n) - 0.7;

			StdDraw.setPenRadius(0.01);
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.setXscale(0, maxScale);
			StdDraw.setYscale(0, maxScale);
		    StdDraw.enableDoubleBuffering();
			curve = new HilbertCurve(n);

			hilbertRecursive(n, ratio);

			StdDraw.show();
			StdDraw.save("hilbertCurve.png");
			StdDraw.closeWindow();
			panel = new DrawingPanel();
		}

	}

	public void hilbertRecursive(int n, double ratio){
		if (n == 0) return;
		globalRatio = Math.pow(2, n-1);
		curve.turn(RIGHT);
		hilbertRecursive2(n-1, ratio);
		curve.forward(globalRatio);
		curve.turn(LEFT);
		hilbertRecursive(n-1, ratio);
		curve.forward(globalRatio);
		hilbertRecursive(n-1, ratio);
		curve.turn(LEFT);
		curve.forward(globalRatio);
		hilbertRecursive2(n-1, ratio);
		curve.turn(RIGHT);
	}

	public void hilbertRecursive2(int n, double ratio){
		if (n == 0) return;
		globalRatio = Math.pow(2, n-1);
		curve.turn(LEFT);
		hilbertRecursive(n-1, ratio);
		curve.forward(globalRatio);
		curve.turn(RIGHT);
		hilbertRecursive2(n-1, ratio);
		curve.forward(globalRatio);
		hilbertRecursive2(n-1, ratio);
		curve.turn(RIGHT);
		curve.forward(globalRatio);
		hilbertRecursive(n-1, ratio);
		curve.turn(LEFT);
	}

	public static void main(String[] args) {
		new Task12();
	}

	private class HilbertCurve{

		private double angle;
		private double x;
		private double y;

		public HilbertCurve(int n){
			angle = 0;
			x = 0.1;
			//Scales starting position of y to ensure curve is in centre of window
			y = Math.pow(2, n) - 0.8;
		}

		public void turn(int degrees){
			angle += degrees;
		}

		public void forward(double amount){
			double oldx = x;
			double oldy = y;
			x += amount * Math.cos(Math.toRadians(angle));
			y += amount * Math.sin(Math.toRadians(angle));
			StdDraw.line(oldx, oldy, x, y);
		}

	}

	//Class to redraw hilbert curve with scaling
	private class DrawingPanel extends JPanel implements ComponentListener {

		JFrame frame;
		Image hilbertCurve;

		public DrawingPanel(){
			try {
				hilbertCurve = ImageIO.read(new File("hilbertCurve.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			//Setup frame and JPanel
			this.addComponentListener(this);
			frame = new JFrame();
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(512, 512));
			frame.setResizable(true);
			frame.getContentPane().add(this);
			frame.setTitle("Hilbert Curve");
			frame.pack();
			frame.setVisible(true);


		}

		//Redraw image when resizing the JFrame
		public void paint(Graphics g){
			g.drawImage(hilbertCurve, 0,0, frame.getWidth() - 15, frame.getHeight() - 40, null);
		}

		//Repaint image when JFrame resized
		@Override
		public void componentResized(ComponentEvent e) {
			repaint();
		}

		@Override
		public void componentMoved(ComponentEvent e) {}

		@Override
		public void componentShown(ComponentEvent e) {}

		@Override
		public void componentHidden(ComponentEvent e) {}
	}

}
