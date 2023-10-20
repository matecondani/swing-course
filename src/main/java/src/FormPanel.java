package src;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

    public FormPanel() {
        Dimension dim = getPreferredSize();
        System.out.println(dim);
        dim.width = 250;
        setPreferredSize(dim);

        Border innerBorder = BorderFactory.createTitledBorder("Add Person");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
}
