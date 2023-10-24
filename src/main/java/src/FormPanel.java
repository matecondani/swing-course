package src;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

    private JLabel nameLabel;
    private JLabel occupationLabel;
    private JTextField nameField;
    private JTextField occupationField;
    private JButton okBtn;
    private FormListener formListener;
    private JList ageList;

    public FormPanel() {
        Dimension dim = getPreferredSize();
        System.out.println(dim);
        dim.width = 250;
        setPreferredSize(dim);

        nameLabel = new JLabel("Name: ");
        occupationLabel = new JLabel("Occupation: ");
        nameField = new JTextField(10);
        occupationField = new JTextField(10);
        ageList = new JList();

        DefaultListModel ageModel = new DefaultListModel();
        ageModel.addElement(new AgeCategory(0, "under 18"));
        ageModel.addElement(new AgeCategory(1, "18 to 65"));
        ageModel.addElement(new AgeCategory(2, "65 or over"));
        ageList.setModel(ageModel);
        ageList.setPreferredSize(new Dimension(110, 66));
        ageList.setBorder(BorderFactory.createEtchedBorder());
        ageList.setSelectedIndex(1);

        okBtn = new JButton("OK");

        okBtn.addActionListener(event -> {
            String name = nameField.getText();
            String occupation = occupationField.getText();
            AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();

            System.out.println(ageCat.getId());

            FormEvent ev = new FormEvent(this, name, occupation, ageCat.getId());

            if (formListener != null) {
                formListener.formEventOccurred(ev);
            }
        });

        Border innerBorder = BorderFactory.createTitledBorder("Add Person");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        ////FIRST ROW////
        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new

                Insets(0, 0, 0, 5);

        add(nameLabel, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new

                Insets(0, 0, 0, 0);

        add(nameField, gc);


        ////SECOND ROW////
        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new

                Insets(0, 0, 0, 5);

        add(occupationLabel, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new

                Insets(0, 0, 0, 0);

        add(occupationField, gc);

        ////THIRD ROW////
        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 1;
        gc.gridy = 2;
        gc.insets = new

                Insets(0, 0, 0, 0);

        gc.anchor = GridBagConstraints.FIRST_LINE_START;

        add(ageList, gc);

        ////FOURTH ROW////
        gc.weightx = 1;
        gc.weighty = 2.0;

        gc.gridx = 1;
        gc.gridy = 3;
        gc.insets = new

                Insets(0, 0, 0, 0);

        gc.anchor = GridBagConstraints.FIRST_LINE_START;

        add(okBtn, gc);
    }

    public void setFormListener(FormListener listener) {
        this.formListener = listener;
    }
}

class AgeCategory {
    private String text;
    private int id;

    public AgeCategory(int id, String text) {
        this.text = text;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return text;
    }
}