package src.gui;

import java.awt.*;
import javax.swing.*;

public class PrefsDialog extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerModel;

    public PrefsDialog(JFrame parent) {
        super(parent, "Preferences", false);

        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");
        spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerModel);

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridy = 0;
        gc.gridx = 0;

        add(new JLabel("Port: "), gc);
        gc.gridx++;
        add(portSpinner, gc);

        ////NEW ROW
        gc.gridy++;
        gc.gridx = 0;
        add(okButton, gc);
        gc.gridx++;
        add(cancelButton, gc);

        okButton.addActionListener(e -> {
            Integer value = (Integer) portSpinner.getValue();
            System.out.println(value);
        });

        cancelButton.addActionListener(e -> {
            setVisible(false);
        });

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }
}
