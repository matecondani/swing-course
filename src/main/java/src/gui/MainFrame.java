package src.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import javax.swing.*;
import src.controller.Controller;

public class MainFrame extends JFrame {

    private TextPanel textPanel;
    private Toolbar toolbar;
    private FormPanel formPanel;
    private JFileChooser fileChooser;
    private Controller controller;
    private TablePanel tablePanel;
    private PrefsDialog prefsDialog;
    private Preferences prefs;

    public MainFrame() {
        super("Hello World");

        setLayout(new BorderLayout());

        textPanel = new TextPanel();
        toolbar = new Toolbar();
        formPanel = new FormPanel();
        tablePanel = new TablePanel();
        prefsDialog = new PrefsDialog(this);

        prefs = Preferences.userRoot().node("db");

        controller = new Controller();

        tablePanel.setData(controller.getPeople());
        tablePanel.addPersonTableListener(new PersonTableListener() {
            @Override
            public void rowDeleted(int row) {
                controller.removePerson(row);
            }
        });

        prefsDialog.setPrefsListener(new PrefsListener() {
            @Override
            public void preferencesSet(String user, String password, int portNumber) {
                prefs.put("user", user);
                prefs.put("password", password);
                prefs.putInt("port", portNumber);
            }
        });
        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        int port = prefs.getInt("port", 3306);
        prefsDialog.setDefaults(user, password, port);

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        fileChooser.setAcceptAllFileFilterUsed(false);

        setJMenuBar(createMenuBar());

        toolbar.setToolbarListener(new ToolbarListener() {
            @Override
            public void saveEventOcurred() {
                connect();
                try {
                    controller.save();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database.", "Database connection problem", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void refreshEventOcurred() {
                connect();
                try {
                    controller.load();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database.", "Database connection problem", JOptionPane.WARNING_MESSAGE);
                }
                tablePanel.refresh();
            }
        });

        formPanel.setFormListener(new FormListener() {
            public void formEventOccurred(FormEvent e) {
                controller.addPerson(e);
                tablePanel.refresh();
            }
        });

//        add(textPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);
        add(formPanel, BorderLayout.WEST);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.disconnect();
                dispose();
                System.gc();
            }
        });

        setMinimumSize(new Dimension(500, 400));
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.", "Database connection problem", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu windowMenu = new JMenu("Window");
        JMenuItem prefsItem = new JMenuItem("Preferences...");

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
        windowMenu.add(prefsItem);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));

        prefsItem.addActionListener(e -> {
            prefsDialog.setVisible(true);
        });

        importDataItem.addActionListener(e -> {
            if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.loadFromFile(fileChooser.getSelectedFile());
                    tablePanel.refresh();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            ;
        });

        exportDataItem.addActionListener(e -> {
            if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.saveToFile(fileChooser.getSelectedFile());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Could not save data from file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            ;
        });

        exitItem.addActionListener(e -> {
//            String enterUserName = JOptionPane.showInputDialog(MainFrame.this,
//                    "Enter your user name.", "Enter user name",
////                    JOptionPane.OK_OPTION |
////                            JOptionPane.INFORMATION_MESSAGE
//                            JOptionPane.QUESTION_MESSAGE
//            );

            int confirmExit = JOptionPane.showConfirmDialog(MainFrame.this,
                    "Do you really want to exit the aplication?", "Confirm Exit",
                    JOptionPane.OK_CANCEL_OPTION);
            if (confirmExit == JOptionPane.OK_OPTION) {
                WindowListener[] listeners = getWindowListeners();
                for (WindowListener listener : listeners) {
                    listener.windowClosing(new WindowEvent(MainFrame.this, 0));
                }
            }
        });

        return menuBar;
    }
}
