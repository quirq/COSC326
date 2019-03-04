package task5;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Task5 extends JPanel {

    private ArrayList<String> parameters;
    private ArrayList<Square> squares = new ArrayList<>();
    public static final String PARAMETER_FILE = "task5/parameters.txt";
    public static final int WIDTH = 500, HEIGHT = 500;
    private double totalScale = 0.0;
    private int drawLayer = 0;

    public Task5(){
        JFrame frame = new JFrame("YGQA(A-M) Quilt");
        add(new JLabel("Woah."));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);

        parameters = new ArrayList<>();

        readParameters();
        for(String s : parameters){
            System.out.println(s);
        }
    }

    public void paint(Graphics g){
        drawSquares(g, 0, WIDTH/2, HEIGHT/2);
    }

    private void drawSquares(Graphics g, int i, int x, int y){
        double squareDimension = (squares.get(i).getScale() / totalScale * WIDTH);
        int squareSize = (int) squareDimension;

        if(drawLayer == squares.get(i).getDrawLayer()){
            g.setColor(squares.get(i).getColor());
            g.fillRect(x - squareSize/2, y - squareSize/2, squareSize, squareSize);
        }

    }

    public void readParameters(){
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

    public static void main(String[] args){
        new Task5();
    }

}
