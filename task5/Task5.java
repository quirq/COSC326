package task5;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Task5 extends JPanel {

    private static ArrayList<String> parameters;
    private static ArrayList<Square> squares = new ArrayList<>();
    public static final String PARAMETER_FILE = "task5/parameters.txt";
    public static final int WINDOW_SIZE = 600;
    private static double totalScale = 0.0;
    private static int drawLayer = 0;
    private static double firstSize;

    public static void main(String[] args) {
        parameters = new ArrayList<>();
        int squareLayer = 0;
        readParameters();
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
        firstSize = WINDOW_SIZE * totalScale;

        JFrame frame = new JFrame("YGQA(A-M) Quilt");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Task5());
        frame.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE + 25));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public Task5(){
        setOpaque(false);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        while(drawLayer < squares.size()){
            drawSquares(g, 0, WINDOW_SIZE/2, WINDOW_SIZE/2);
            drawLayer++;
        }

    }

    private void drawSquares(Graphics g, int i, int x, int y){
        double squareDimension = ((squares.get(i).getScale() / totalScale) * (WINDOW_SIZE));
        int squareSize = (int) squareDimension;

        if(drawLayer == squares.get(i).getDrawLayer()){
            g.setColor(squares.get(i).getColor());
            g.fillRect(x - squareSize/2, y - squareSize/2, squareSize, squareSize);
        }
        if(squares.get(i+1) != null){
            drawSquares(g, i+1, x - squareSize/2, y - squareSize/2);
            drawSquares(g, i+1, x + squareSize/2, y - squareSize/2);
            drawSquares(g, i+1, x - squareSize/2, y + squareSize/2);
            drawSquares(g, i+1, x + squareSize/2, y + squareSize/2);
        }

    }

    public static void readParameters(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(PARAMETER_FILE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(scanner.hasNextLine()){
            parameters.add(scanner.nextLine());
        }
    }

}
