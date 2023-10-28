package src.gui;

import java.awt.*;
import javax.swing.*;

public class PrefsDialog extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerModel;
    private JTextField userField;
    private JPasswordField passField;

    public PrefsDialog(JFrame parent) {
        super(parent, "Preferences", false);

        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");

        spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerModel);

        userField = new JTextField(10);
        passField = new JPasswordField(10);

//        passField.setEchoChar('*');

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;

        ////First row
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;

        add(new JLabel("User: "), gc);
        gc.gridx++;
        add(userField, gc);

        ////NEXT ROW
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;

        add(new JLabel("Password: "), gc);
        gc.gridx++;
        add(passField, gc);

        ////NEXT ROW
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
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

            String user = userField.getText();
            char[] password = passField.getPassword();
            System.out.println(user + " : " + String.copyValueOf(password));
        });

        cancelButton.addActionListener(e -> {
            setVisible(false);
        });

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }
}
