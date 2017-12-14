/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotquestionmaker;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author marcos.lindekugel
 */
class RobotQuestionSaver extends JPanel {

    public RobotQuestionSaver() {
        super.add(new JLabel("Filename: "));
        JTextField name = new JTextField("question-1",50);
        super.add(name);
        JButton save = new JButton( "Save" );
        save.addActionListener(e->{
            RobotGrid.save( name.getText() );
        });
        super.add( save );
    }

}
