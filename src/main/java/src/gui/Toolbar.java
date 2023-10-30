package src.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;

public class Toolbar extends JPanel implements ActionListener {

    private JButton saveButton;
    private JButton refreshButton;
    private ToolbarListener toolbarListener;

    public Toolbar() {
        setBorder(BorderFactory.createEtchedBorder());
        saveButton = new JButton("Save");
        refreshButton = new JButton("Refresh");

        saveButton.addActionListener(this);
        refreshButton.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(saveButton);
        add(refreshButton);
    }

    public void setToolbarListener(ToolbarListener listener) {
        this.toolbarListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == saveButton && Objects.nonNull(toolbarListener)) {
            toolbarListener.saveEventOcurred();
        }
        if (clicked == refreshButton && Objects.nonNull(toolbarListener)) {
            toolbarListener.refreshEventOcurred();
        }
    }
}
