package task12;

import java.awt.*;

public class Task12 {

	private HilbertCurve curve;

	public Task12(){
//		int n = 0;
//		double ratio = 0.0;

		int n = 2;
		double ratio = 1.0;

		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(Color.BLACK);

//		Scanner scanner = new Scanner(System.in);
//		String[] input = scanner.nextLine().split(" ");
//		n = Integer.parseInt(input[0]);
//		if(input.length == 2){
//			ratio = Double.parseDouble(input[1]);
//		} else {
//			ratio = 1.0;
//		}

		curve = new HilbertCurve(n);
		StdDraw.setXscale(0, Math.pow(2, n));
		StdDraw.setYscale(0, Math.pow(2, n));
		hilbertRecursive(n, ratio);
	}

	public void hilbertRecursive(int n, double ratio){
		if (n == 0) return;
		curve.turn(-90);
		drawRecursive(n-1, ratio);
		curve.forward(ratio);
		curve.turn(90);
		hilbertRecursive(n-1, ratio);
		curve.forward(ratio);
		hilbertRecursive(n-1, ratio);
		curve.turn(90);
		curve.forward(ratio);
		drawRecursive(n-1, ratio);
		curve.turn(-90);
	}

	public void drawRecursive(int n, double ratio){
		ratio *= ratio;
		if (n == 0) return;
		curve.turn(90);
		hilbertRecursive(n-1, ratio);
		curve.forward(ratio);
		curve.turn(-90);
		drawRecursive(n-1, ratio);
		curve.forward(ratio);
		drawRecursive(n-1, ratio);
		curve.turn(-90);
		curve.forward(ratio);
		hilbertRecursive(n-1, ratio);
		curve.turn(90);
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
			x = 0.5;
			y = Math.pow(2, n) - 0.5;
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

}
