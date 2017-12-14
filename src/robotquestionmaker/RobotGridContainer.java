/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotquestionmaker;

import java.awt.FlowLayout;
import javax.swing.JPanel;

/**
 *
 * @author marcos.lindekugel
 */
public class RobotGridContainer extends JPanel {

    private final int padding = 10;

    public RobotGridContainer() {
        for (int i = 0; i < 4; i++) {
            super.add(RobotGrid.create());
        }
        super.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
    }
}
