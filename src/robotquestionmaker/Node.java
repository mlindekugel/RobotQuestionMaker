/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotquestionmaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 *
 * @author marcos.lindekugel
 */
public class Node {
    public static final boolean START = true;
    public static final boolean END = false;
    
    private final boolean type;
    private int x;
    private int y;
    private int facing = 0;

    public Node( boolean type )
    {
        this.type = type;
        this.x = -1;
        this.y = -1;
    }
    
    public void update( int x, int y )
    {
        if ( this.x == x && this.y == y )
        {
            this.rotate();
        }
        else
        {
            this.moveTo( x, y );
        }
    }
    
    public boolean hasBeenPlaced()
    {
        return this.x != -1 && this.y != -1;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public BufferedImage getImage( int width, int height )
    {
        BufferedImage image = new BufferedImage( width, height, TYPE_INT_ARGB );
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setStroke( new BasicStroke(3));
        String text = "";
        g.setFont(new Font("Serif", Font.BOLD, height*2/3));
        if ( this.type == START )
        {
            g.setColor( Color.GREEN );
            text = "S";
        }
        else if ( this.type == END )
        {
            g.setColor( Color.RED );
            text = "E";
        }
        g.fillRect(1, 1, width-1, height-1);
        int[][] imageData = {
            {width/2, 0, 0, height, width/2, 0, width, height, width / 2 - g.getFontMetrics().stringWidth(text) / 2, height - 2},
            {0, 0, width, height/2, 0, height, width, height/2, 2, height * 2/3},
            {0, 0, width/2, height, width, 0, width/2, height, width / 2 - g.getFontMetrics().stringWidth(text) / 2, height * 2/3},
            {0, height/2, width, 0, 0, height/2, width, height, width - g.getFontMetrics().stringWidth(text) - 2, height*2/3}
        };
        int[] useMe = imageData[this.facing];
        g.setColor( Color.BLACK );
        g.drawLine(useMe[0],useMe[1],useMe[2],useMe[3]);
        g.drawLine(useMe[4],useMe[5],useMe[6],useMe[7]);
        g.drawString(text,useMe[8],useMe[9]);

        return image;
    }
    
    private void rotate()
    {
        this.facing++;
        this.facing %= 4;
    }
    
    private void moveTo( int x, int y )
    {
        this.x = x; 
        this.y = y;
    }
}
