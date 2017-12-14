/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotquestionmaker;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author marcos.lindekugel
 */
public class RobotQuestionMaker extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RobotQuestionMaker x = new RobotQuestionMaker();
    }
    
    private final RobotGridContainer container;
    private RobotQuestionMaker()
    {
        super("Robot Question Maker");
        container = new RobotGridContainer();
        super.add( container );
        super.add( new RobotQuestionControls( container ), BorderLayout.WEST );
        super.add( new RobotQuestionSaver(), BorderLayout.NORTH );
        super.setSize(800, 600);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setVisible( true );
    }
}
