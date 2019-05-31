package task12;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

public class Task12 {

	private HilbertCurve curve;
	private DrawingPanel panel;
	private double globalRatio;
	private double maximum;
	private static final int LEFT = 90, RIGHT = -90;

	public Task12(String[] args){
		int n = 0;

		//Take in parameter n, and optional parameter r
		n = Integer.parseInt(args[0]);
		if(args.length == 2){
			globalRatio = Double.parseDouble(args[1]);
		} else {
			globalRatio = 1.0;
		}

		curve = new HilbertCurve(n);
		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setXscale(0, maximum);
		StdDraw.setYscale(0, maximum);
		StdDraw.enableDoubleBuffering();

		hilbertRecursive(n);

		StdDraw.show();
		StdDraw.save("hilbertCurve.png");
		StdDraw.closeWindow();
		panel = new DrawingPanel();


	}

	public void hilbertRecursive(int n){
		double scale = Math.pow(globalRatio, n-2);
		if(n <= 2) scale = 1;
		if(scale < 0) scale = 0;
		if (n == 0) return;
		curve.turn(-90);
		hilbertRecursive2(n-1);
		curve.forward(scale);
		curve.turn(90);
		hilbertRecursive(n-1);
		curve.forward(scale);
		hilbertRecursive(n-1);
		curve.turn(90);
		curve.forward(scale);
		hilbertRecursive2(n-1);
		curve.turn(-90);
	}

	public void hilbertRecursive2(int n){
		double scale = Math.pow(globalRatio, n-2);
		if(n <= 2) scale = 1;
		if(scale < 0) scale = 0;
		if (n == 0) return;
		curve.turn(90);
		hilbertRecursive(n-1);
		curve.forward(scale);
		curve.turn(-90);
		hilbertRecursive2(n-1);
		curve.forward(scale);
		hilbertRecursive2(n-1);
		curve.turn(-90);
		curve.forward(scale);
		hilbertRecursive(n-1);
		curve.turn(90);
	}

	public static void main(String[] args) {
		new Task12(args);
	}

	private class HilbertCurve{

		private double angle;
		private double x;
		private double y;

		public HilbertCurve(int n){
			angle = 0;
			x = 0.5;
			double total = Math.pow(2, n);
			if(globalRatio <= 1) {
				for (int i = 1; i < n - 2; i++) {
					double whiteSpace = 1 - Math.pow(globalRatio, i);
					total -= whiteSpace * Math.pow(2, n - i - 2);
				}
			} else {
				for (int i = 1; i < n - 2; i++) {
					double whiteSpace = Math.pow(globalRatio, i) - 1;
					total += whiteSpace * Math.pow(2, n - i - 1);
				}
			}
			maximum = total;
			y = total - 0.5;
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
			int width = frame.getWidth();
			if(width > frame.getHeight()){
				width = frame.getHeight();
			}
			g.drawImage(hilbertCurve, 0,0, width, width - 25, null);
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
