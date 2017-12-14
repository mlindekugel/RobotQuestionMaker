/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotquestionmaker;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author marcos.lindekugel
 */
public class RobotQuestionControls extends JPanel {

    public RobotQuestionControls(Component parent) {
        super.setPreferredSize(new Dimension(140, 500));
        ButtonGroup shareMaze = new ButtonGroup();
        JRadioButton shareMazeYes = new JRadioButton("Yes");
        shareMazeYes.setSelected(RobotGrid.useSharedMaze());
        shareMazeYes.addItemListener(e -> {
            RobotGrid.useSharedMaze(e.getStateChange() == ItemEvent.SELECTED);
            RobotGrid.repaintAll();
        });
        JRadioButton shareMazeNo = new JRadioButton("No");
        shareMazeNo.setSelected(!RobotGrid.useSharedStartPoint());
        shareMaze.add(shareMazeYes);
        shareMaze.add(shareMazeNo);

        super.add(createJText("Drag to define walls or paths in the maze. Use same maze for all grids?"));
        super.add(shareMazeYes);
        super.add(shareMazeNo);

        ButtonGroup shareStartPoint = new ButtonGroup();
        JRadioButton shareStartYes = new JRadioButton("Yes");
        shareStartYes.setSelected(RobotGrid.useSharedStartPoint());
        shareStartYes.addItemListener(e -> {
            RobotGrid.useSharedStartPoint(e.getStateChange() == ItemEvent.SELECTED);
            RobotGrid.repaintAll();
        });
        JRadioButton shareStartNo = new JRadioButton("No");
        shareStartNo.setSelected(!RobotGrid.useSharedStartPoint());
        shareStartPoint.add(shareStartYes);
        shareStartPoint.add(shareStartNo);

        super.add(createJText("Click a square to place the start point. Use same start point for all grids?"));
        super.add(shareStartYes);
        super.add(shareStartNo);

        ButtonGroup shareEndPoint = new ButtonGroup();
        JRadioButton shareEndYes = new JRadioButton("Yes");
        shareEndYes.setSelected(RobotGrid.useSharedEndPoint());
        shareEndYes.addItemListener(e -> {
            RobotGrid.useSharedEndPoint(e.getStateChange() == ItemEvent.SELECTED);
            RobotGrid.repaintAll();
        });
        JRadioButton shareEndNo = new JRadioButton("No");
        shareEndNo.setSelected(!RobotGrid.useSharedEndPoint());
        shareEndPoint.add(shareEndYes);
        shareEndPoint.add(shareEndNo);

        super.add(createJText("Shift-Click a square to place the end point. Use same end point for all grids?"));
        super.add(shareEndYes);
        super.add(shareEndNo);

        super.add(new JLabel("Squares Wide"));
        JTextField x = new JTextField("5", 2);
        super.add(x);
        super.add(new JLabel("Squares High"));
        JTextField y = new JTextField("5", 2);
        super.add(y);

        super.add(new JLabel("Pixels Wide"));
        JTextField px = new JTextField("300", 4);
        super.add(px);
        super.add(new JLabel("Pixels High"));
        JTextField py = new JTextField("200", 4);
        super.add(py);
        JButton updateDimensions = new JButton("Reconfigure");
        updateDimensions.addActionListener((ActionEvent e) -> {
            try {
                RobotGrid.setDimensions(
                        Integer.parseInt(x.getText()),
                        Integer.parseInt(y.getText())
                );
                RobotGrid.setAllSizes(
                        Integer.parseInt(px.getText()),
                        Integer.parseInt(py.getText())
                );
                parent.revalidate();
                RobotGrid.repaintAll();
            } catch (Exception ex) {
            }
        });

        super.add(updateDimensions);
    }

    private JTextArea createJText(String s) {
        JTextArea area = new JTextArea();
        area.setText(s);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setEditable(false);
        area.setBackground(super.getBackground());
        return area;
    }

}
