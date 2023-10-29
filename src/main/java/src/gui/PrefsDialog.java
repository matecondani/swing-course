package src.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class PrefsDialog extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerModel;
    private JTextField userField;
    private JPasswordField passField;
    private PrefsListener prefsListener;

    public PrefsDialog(JFrame parent) {
        super(parent, "Preferences", false);

        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");

        spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerModel);

        userField = new JTextField(10);
        passField = new JPasswordField(10);

//        passField.setEchoChar('*');

        layoutControls();

        okButton.addActionListener(e -> {
            Integer value = (Integer) portSpinner.getValue();

            String user = userField.getText();
            char[] password = passField.getPassword();

            if (prefsListener != null) {
                prefsListener.preferencesSet(user, String.valueOf(password), value);
            }

            setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            setVisible(false);
        });

        setSize(340, 250);
        setLocationRelativeTo(parent);
    }

    private void layoutControls() {

        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 15;
        Border titleBorder = BorderFactory.createTitledBorder("Database preferences");
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

        controlsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        Insets rightPadding = new Insets(0, 0, 0, 15);
        Insets noPadding = new Insets(0, 0, 0, 0);

        gc.gridy = 0;

        ////First row
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;

        controlsPanel.add(new JLabel("User: "), gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(userField, gc);

        ////NEXT ROW
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Password: "), gc);
        gc.insets = noPadding;
        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(passField, gc);

        ////NEXT ROW
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Port: "), gc);
        gc.insets = noPadding;
        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(portSpinner, gc);

        ////BUTTONS ROW
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton, gc);
        buttonsPanel.add(cancelButton, gc);

        Dimension btnSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(btnSize);

        //Add sub panels to dialog
        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void setDefaults(String user, String password, int port) {
        userField.setText(user);
        passField.setText(password);
        portSpinner.setValue(port);
    }

    public void setPrefsListener(PrefsListener prefsListener) {
        this.prefsListener = prefsListener;
    }
}
