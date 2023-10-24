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

    public FormPanel() {
        Dimension dim = getPreferredSize();
        System.out.println(dim);
        dim.width = 250;
        setPreferredSize(dim);

        nameLabel = new JLabel("Name: ");
        occupationLabel = new JLabel("Occupation: ");
        nameField = new JTextField(10);
        occupationField = new JTextField(10);

        okBtn = new JButton("OK");

        okBtn.addActionListener(event -> {
            String name = nameField.getText();
            String occupation = occupationField.getText();
            FormEvent ev = new FormEvent(this, name, occupation);
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
            gc.weightx =1;
            gc.weighty =0.1;

            gc.gridx =0;
            gc.gridy =0;
            gc.fill =GridBagConstraints.NONE;
            gc.anchor =GridBagConstraints.LINE_END;
            gc.insets =new

            Insets(0,0,0,5);

            add(nameLabel, gc);

            gc.gridx =1;
            gc.gridy =0;
            gc.anchor =GridBagConstraints.LINE_START;
            gc.insets =new

            Insets(0,0,0,0);

            add(nameField, gc);


            ////SECOND ROW////
            gc.weightx =1;
            gc.weighty =0.1;

            gc.gridx =0;
            gc.gridy =1;
            gc.anchor =GridBagConstraints.LINE_END;
            gc.insets =new

            Insets(0,0,0,5);

            add(occupationLabel, gc);

            gc.gridx =1;
            gc.gridy =1;
            gc.anchor =GridBagConstraints.LINE_START;
            gc.insets =new

            Insets(0,0,0,0);

            add(occupationField, gc);

            ////THIRD ROW////
            gc.weightx =1;
            gc.weighty =2.0;

            gc.gridx =1;
            gc.gridy =2;
            gc.insets =new

            Insets(0,0,0,0);

            gc.anchor =GridBagConstraints.FIRST_LINE_START;

            add(okBtn, gc);
        }

        public void setFormListener (FormListener listener){
            this.formListener = listener;
        }
    }
