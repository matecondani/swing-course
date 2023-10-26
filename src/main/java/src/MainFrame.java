package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MainFrame extends JFrame {

    private TextPanel textPanel;
    private Toolbar toolbar;
    private FormPanel formPanel;

    public MainFrame() {
        super("Hello World");

        setLayout(new BorderLayout());

        textPanel = new TextPanel();
        toolbar = new Toolbar();
        formPanel = new FormPanel();

        setJMenuBar(createMenuBar());

        toolbar.setStringListener(textPanel::appendText);

        formPanel.setFormListener(new FormListener() {
            public void formEventOccurred(FormEvent e) {
                String name = e.getName();
                String occupation = e.getOccupation();
                int ageCat = e.getAgeCategory();
                String empCat = e.getEmpCat();
                String gender = e.getGender();
                textPanel.appendText(name + ": " + occupation + ": " + ageCat + ": " + empCat + "\n");
                System.out.println(gender);
            }
        });

        add(textPanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);
        add(formPanel, BorderLayout.WEST);

        setMinimumSize(new Dimension(500,400));
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu windowMenu = new JMenu("Window");

        JMenu fileMenu = new JMenu("File");
        JMenuItem exportDataItem = new JMenuItem("Export data...");
        JMenuItem importDataItem = new JMenuItem("Import data...");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu showMenu = new JMenu("Show");
        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);
        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
//                formPanel.setVisible(menuItem.getState());
                formPanel.setVisible(menuItem.isSelected());
            }
        });
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

        exitItem.addActionListener(e -> {
            String enterUserName = JOptionPane.showInputDialog(MainFrame.this,
                    "Enter your user name.", "Enter user name",
                    JOptionPane.OK_OPTION |
//                            JOptionPane.INFORMATION_MESSAGE
                            JOptionPane.QUESTION_MESSAGE
            );

            int confirmExit = JOptionPane.showConfirmDialog(MainFrame.this,
                    "Do you really want to exit the aplication?", "Confirm Exit",
                    JOptionPane.OK_CANCEL_OPTION);
            if (confirmExit == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });

        return menuBar;
    }
}
