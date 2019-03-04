package task5;

import java.awt.*;

public class Square {

    private double scale;
    private Color col;
    private int drawLayer;

    public Square(double scale, int r, int g, int b, int drawLayer){
        this.scale = scale;
        col = new Color(r, g, b);
        this.drawLayer = drawLayer;
    }

    public double getScale(){
        return scale;
    }

    public Color getColor(){
        return col;
    }

    public int getDrawLayer(){
        return drawLayer;
    }

}
