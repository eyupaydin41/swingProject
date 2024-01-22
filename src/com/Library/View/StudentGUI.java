package com.Library.View;
import com.Library.Helper.Helper;
import com.Library.Model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField inputField;
    private JScrollPane scrollPane;
    private JPanel wrapper;
    private Student student;

    public StudentGUI(Student student) {
        this.student = student;
        add(wrapper);
        setTitle("Chat App");
        setSize(700, 700);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMessage(inputField.getText());
                inputField.setText("");
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
    }

    private void addMessage(String message) {
        JLabel label = new JLabel(message);
        label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        mainPanel.add(label);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        Student student1 = new Student();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentGUI(student1).setVisible(true);
            }
        });
    }
}
