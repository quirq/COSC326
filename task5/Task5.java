package task5;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Task5 extends JPanel {

    private  ArrayList<String> parameters;
    private  ArrayList<Square> squares = new ArrayList<>();
    public  final int WINDOW_SIZE = 600;
    private  double totalScale = 0.0;
    private  int currSquareDraw = 0;



    public Task5(){
        parameters = new ArrayList<>();
        int squareLayer = 0;

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            parameters.add(scanner.nextLine());
        }

        for(String s : parameters){
            String[] inputParameters = s.split(" ");
            double scale = Double.parseDouble(inputParameters[0]);
            int r = Integer.parseInt(inputParameters[1]);
            int g = Integer.parseInt(inputParameters[2]);
            int b = Integer.parseInt(inputParameters[3]);
            totalScale += scale;
            squares.add(new Square(scale, r, g, b, squareLayer));
            squareLayer++;
        }

        squares.add(null);

        JFrame frame = new JFrame("YGQA(A-M) Quilt");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE+22));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    private void drawSquaresByLayer(Graphics g, int i, int x, int y){
        double doubleSquareSize = ((squares.get(i).getScale() / totalScale) * (WINDOW_SIZE));
        int intSquareSize = (int) doubleSquareSize;

        if(currSquareDraw == squares.get(i).getDrawLayer()){
            g.setColor(squares.get(i).getColor());
            g.fillRect(x - intSquareSize/2, y - intSquareSize/2, intSquareSize, intSquareSize);
        }

        if(squares.get(i + 1) != null){
            drawSquaresByLayer(g, i+1, x - intSquareSize/2, y - intSquareSize/2);
            drawSquaresByLayer(g, i+1, x - intSquareSize/2, y + intSquareSize/2);
            drawSquaresByLayer(g, i+1, x + intSquareSize/2, y - intSquareSize/2);
            drawSquaresByLayer(g, i+1, x + intSquareSize/2, y + intSquareSize/2);
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        while(currSquareDraw < squares.size()){
            drawSquaresByLayer(g, 0, WINDOW_SIZE/2, WINDOW_SIZE/2);
            currSquareDraw++;
        }
        currSquareDraw = 0;
    }

    public  void main(String[] args) {
        new Task5();
    }

    private class Square {

        private double scale;
        private Color col;
        private int squareDraw;

        public Square(double scale, int r, int g, int b, int squareDraw){
            this.scale = scale;
            this.col = new Color(r, g, b);
            this.squareDraw = squareDraw;
        }

        public double getScale(){
            return scale;
        }

        public Color getColor(){
            return col;
        }

        public int getDrawLayer(){
            return squareDraw;
        }

    }

}
