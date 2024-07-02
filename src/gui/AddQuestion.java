package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddQuestion extends JFrame {
    private JTextField questionField;
    private JTextField optionAField;
    private JTextField optionBField;
    private JTextField optionCField;
    private JTextField optionDField;
    private JComboBox<String> correctOptionBox;

    public AddQuestion() {
        setTitle("Add Question");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel questionLabel = new JLabel("Question:");
        questionLabel.setBounds(10, 10, 80, 25);
        panel.add(questionLabel);

        questionField = new JTextField(20);
        questionField.setBounds(100, 10, 280, 25);
        panel.add(questionField);

        JLabel optionALabel = new JLabel("Option A:");
        optionALabel.setBounds(10, 50, 80, 25);
        panel.add(optionALabel);

        optionAField = new JTextField(20);
        optionAField.setBounds(100, 50, 280, 25);
        panel.add(optionAField);

        JLabel optionBLabel = new JLabel("Option B:");
        optionBLabel.setBounds(10, 90, 80, 25);
        panel.add(optionBLabel);

        optionBField = new JTextField(20);
        optionBField.setBounds(100, 90, 280, 25);
        panel.add(optionBField);

        JLabel optionCLabel = new JLabel("Option C:");
        optionCLabel.setBounds(10, 130, 80, 25);
        panel.add(optionCLabel);

        optionCField = new JTextField(20);
        optionCField.setBounds(100, 130, 280, 25);
        panel.add(optionCField);

        JLabel optionDLabel = new JLabel("Option D:");
        optionDLabel.setBounds(10, 170, 80, 25);
        panel.add(optionDLabel);

        optionDField = new JTextField(20);
        optionDField.setBounds(100, 170, 280, 25);
        panel.add(optionDField);

        JLabel correctOptionLabel = new JLabel("Correct Option:");
        correctOptionLabel.setBounds(10, 210, 100, 25);
        panel.add(correctOptionLabel);

        correctOptionBox = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        correctOptionBox.setBounds(120, 210, 50, 25);
        panel.add(correctOptionBox);

        JButton addButton = new JButton("Add Question");
        addButton.setBounds(180, 210, 150, 25);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addQuestion();
            }
        });
    }

    private void addQuestion() {
        String question = questionField.getText();
        String optionA = optionAField.getText();
        String optionB = optionBField.getText();
        String optionC = optionCField.getText();
        String optionD = optionDField.getText();
        String correctOption = (String) correctOptionBox.getSelectedItem();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, question);
            stmt.setString(2, optionA);
            stmt.setString(3, optionB);
            stmt.setString(4, optionC);
            stmt.setString(5, optionD);
            stmt.setString(6, correctOption);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Question added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
