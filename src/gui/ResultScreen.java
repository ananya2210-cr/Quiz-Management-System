package gui;

import db.DBConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultScreen extends JFrame {
    private JTextArea resultsArea;

    public ResultScreen() {
        setTitle("Quiz Results");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        add(new JScrollPane(resultsArea));

        loadResults();

        setVisible(true);
    }

    private void loadResults() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM quiz_results";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder results = new StringBuilder();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int score = rs.getInt("score");
                results.append("User ID: ").append(userId).append(", Score: ").append(score).append("\n");
            }
            resultsArea.setText(results.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
