package gui;

import db.DBConnection;
import model.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizScreen extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    private JLabel questionLabel;
    private JRadioButton optionA;
    private JRadioButton optionB;
    private JRadioButton optionC;
    private JRadioButton optionD;
    private ButtonGroup optionsGroup;

    public QuizScreen() {
        setTitle("Quiz");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        loadQuestions();
        displayQuestion();

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        questionLabel = new JLabel();
        questionLabel.setBounds(10, 10, 480, 25);
        panel.add(questionLabel);

        optionA = new JRadioButton();
        optionA.setBounds(10, 50, 200, 25);
        panel.add(optionA);

        optionB = new JRadioButton();
        optionB.setBounds(10, 80, 200, 25);
        panel.add(optionB);

        optionC = new JRadioButton();
        optionC.setBounds(10, 110, 200, 25);
        panel.add(optionC);

        optionD = new JRadioButton();
        optionD.setBounds(10, 140, 200, 25);
        panel.add(optionD);

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(10, 180, 80, 25);
        panel.add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                } else {
                    JOptionPane.showMessageDialog(null, "Quiz finished! Your score: " + score);
                    saveResult();
                    dispose();
                }
            }
        });
    }

    private void loadQuestions() {
        questions = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM questions";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question question = new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option")
                );
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(question.getQuestion());
        optionA.setText(question.getOptionA());
        optionB.setText(question.getOptionB());
        optionC.setText(question.getOptionC());
        optionD.setText(question.getOptionD());
        optionsGroup.clearSelection();
    }

    private void checkAnswer() {
        Question question = questions.get(currentQuestionIndex);
        String selectedOption = null;

        if (optionA.isSelected()) {
            selectedOption = "A";
        } else if (optionB.isSelected()) {
            selectedOption = "B";
        } else if (optionC.isSelected()) {
            selectedOption = "C";
        } else if (optionD.isSelected()) {
            selectedOption = "D";
        }

        if (question.getCorrectOption().equals(selectedOption)) {
            score++;
        }
    }

    private void saveResult() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO quiz_results (user_id, score) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Assuming a user ID is available, set it here
            stmt.setInt(1, 1); // replace with actual user ID
            stmt.setInt(2, score);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
