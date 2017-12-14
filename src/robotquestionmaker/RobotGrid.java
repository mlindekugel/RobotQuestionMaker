/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotquestionmaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author marcos.lindekugel
 */
class RobotGrid extends JPanel implements MouseListener, MouseMotionListener {

    private static final RobotGrid[] INSTANCES = new RobotGrid[4];
    private static int xSquares = 5;
    private static int ySquares = 5;
    private static boolean[][] SHARED_MAZE;
    private static boolean useSharedMaze = true;
    private static int instanceCount = 0;
    private static final Node SHARED_START_POINT = new Node(Node.START);
    private static boolean useSharedStartPoint = true;
    private static final Node SHARED_END_POINT = new Node(Node.END);
    private static boolean useSharedEndPoint = false;
    private static int idCount = 1;

    static {
        RobotGrid.setDimensions(5, 5);
    }

    public static void save(String fileName) {
        int width = RobotGrid.INSTANCES[0].getWidth();
        int height = RobotGrid.INSTANCES[0].getHeight();
        int padding = 30;
        BufferedImage image = new BufferedImage( (padding + width) * 2, padding + height * 2, BufferedImage.TYPE_INT_ARGB );
        Graphics g = image.getGraphics();
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                int idx = x + y * 2;
                g.setColor( Color.BLACK );
                g.setFont( new Font("Times Roman", Font.BOLD, 32) );
                g.drawString(String.valueOf(idx + 1) + ".",
                        2 + (padding * x) + width * x,
                        padding + (height + padding) * y
                );
                g.drawImage(
                        RobotGrid.INSTANCES[idx].saveImage(fileName),
                        (padding * (x + 1)) + width * x,
                        (height + padding) * y,
                        null
                );
            }
        }
        try {
            ImageIO.write(image, "PNG", new File(String.format("%s.png", fileName)));
        } catch (IOException ex) {
            Logger.getLogger(RobotGrid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static RobotGrid create() {
        RobotGrid g = new RobotGrid();
        g.addMouseListener(g);
        g.addMouseMotionListener(g);
        RobotGrid.INSTANCES[RobotGrid.instanceCount++] = g;
        return g;
    }

    public static void useSharedMaze(boolean value) {
        RobotGrid.useSharedMaze = value;
    }

    public static boolean useSharedMaze() {
        return RobotGrid.useSharedMaze;
    }

    public static void useSharedStartPoint(boolean value) {
        RobotGrid.useSharedStartPoint = value;
    }

    public static boolean useSharedStartPoint() {
        return RobotGrid.useSharedStartPoint;
    }

    public static void useSharedEndPoint(boolean value) {
        RobotGrid.useSharedEndPoint = value;
    }

    public static boolean useSharedEndPoint() {
        return RobotGrid.useSharedEndPoint;
    }

    public static void setDimensions(int x, int y) {
        RobotGrid.xSquares = x;
        RobotGrid.ySquares = y;
        RobotGrid.SHARED_MAZE = new boolean[RobotGrid.xSquares][RobotGrid.ySquares];
        for (RobotGrid each : RobotGrid.INSTANCES) {
            if (each != null) {
                each.maze = new boolean[RobotGrid.xSquares][RobotGrid.ySquares];
            }
        }
    }

    public static void repaintAll() {
        for (RobotGrid each : RobotGrid.INSTANCES) {
            each.repaint();
        }
    }

    public static void setAllSizes(int x, int y) {
        for ( RobotGrid each : RobotGrid.INSTANCES )
        {
            each.setPreferredSize( new Dimension( x, y ) );
        }
    }

    private boolean mazeBlockType;
    private final Node startPoint;
    private final Node endPoint;
    private boolean[][] maze;
    private final int id;

    // Constructor is private because we want to track each instance
    // created so that we can repaint all grids when necessary.
    // To create a new RobotGrid, call RobotGrid.create();
    private RobotGrid() {
        super.setPreferredSize(new Dimension(300, 200));
        this.startPoint = new Node(Node.START);
        this.endPoint = new Node(Node.END);
        this.maze = new boolean[RobotGrid.xSquares][RobotGrid.ySquares];
        this.id = RobotGrid.idCount++;
    }

    public int getSquareWidth() {
        return this.getWidth() / RobotGrid.xSquares - 1;
    }

    public int getSquareHeight() {
        return this.getHeight() / RobotGrid.ySquares - 1;
    }

    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        int squareWidth = this.getSquareWidth();
        int squareHeight = this.getSquareHeight();
        boolean[][] localMaze = RobotGrid.useSharedMaze ? RobotGrid.SHARED_MAZE : this.maze;
        for (int y = 0; y < RobotGrid.ySquares; y++) {
            for (int x = 0; x < RobotGrid.xSquares; x++) {
                if (localMaze[x][y]) {
                    g.setColor(Color.GRAY);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x * squareWidth, y * squareHeight, squareWidth, squareHeight);
                Node which = RobotGrid.useSharedStartPoint ? RobotGrid.SHARED_START_POINT : this.startPoint;
                if (which.hasBeenPlaced()) {
                    g.drawImage(which.getImage(squareWidth, squareHeight),
                            which.getX() * squareWidth,
                            which.getY() * squareHeight,
                            null
                    );
                }
                which = RobotGrid.useSharedEndPoint ? RobotGrid.SHARED_END_POINT : this.endPoint;
                if (which.hasBeenPlaced()) {
                    g.drawImage(which.getImage(squareWidth, squareHeight),
                            which.getX() * squareWidth,
                            which.getY() * squareHeight,
                            null
                    );
                }
                g.setColor(Color.BLACK);
                g.drawRect(x * squareWidth, y * squareHeight, squareWidth, squareHeight);
            }
        }
    }

    private int[] mapMouseToGrid(MouseEvent e) {
        int[] map = null;
        int x = e.getX() / this.getSquareWidth();
        int y = e.getY() / this.getSquareHeight();

        if (x >= 0 && y >= 0 && x < RobotGrid.SHARED_MAZE.length && y < RobotGrid.SHARED_MAZE[0].length) {
            map = new int[2];
            map[0] = x;
            map[1] = y;
        }
        return map;
    }

    private BufferedImage saveImage(String filename) {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), TYPE_INT_ARGB);
        this.paint(image.getGraphics());
        try {
            ImageIO.write(image, "PNG", new File(String.format("%s-%d.png", filename, this.id)));
        } catch (IOException ex) {
            Logger.getLogger(RobotGrid.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int[] dim = mapMouseToGrid(e);
        boolean[][] localMaze = RobotGrid.useSharedMaze ? RobotGrid.SHARED_MAZE : this.maze;
        if (dim != null) {
            if (localMaze[dim[0]][dim[1]] != this.mazeBlockType) {
                localMaze[dim[0]][dim[1]] = this.mazeBlockType;
                RobotGrid.repaintAll();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int[] dim = mapMouseToGrid(e);
        if (e.isShiftDown()) {
            updateEndPoint(dim[0], dim[1]);
        } else {
            updateStartPoint(dim[0], dim[1]);
        }
    }

    private void updateEndPoint(int x, int y) {
        Node which = RobotGrid.useSharedEndPoint ? RobotGrid.SHARED_END_POINT : this.endPoint;
        which.update(x, y);
        RobotGrid.repaintAll();
    }

    private void updateStartPoint(int x, int y) {
        Node which = RobotGrid.useSharedStartPoint ? RobotGrid.SHARED_START_POINT : this.startPoint;
        which.update(x, y);
        RobotGrid.repaintAll();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int[] dim = mapMouseToGrid(e);
        boolean[][] localMaze = RobotGrid.useSharedMaze ? RobotGrid.SHARED_MAZE : this.maze;
        if (dim != null) {
            this.mazeBlockType = !localMaze[dim[0]][dim[1]];
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
