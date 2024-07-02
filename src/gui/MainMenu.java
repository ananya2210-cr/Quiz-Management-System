package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton addQuestionButton = new JButton("Add Questions");
        addQuestionButton.setBounds(50, 20, 200, 25);
        panel.add(addQuestionButton);

        JButton takeQuizButton = new JButton("Take Quiz");
        takeQuizButton.setBounds(50, 60, 200, 25);
        panel.add(takeQuizButton);

        JButton viewResultsButton = new JButton("View Results");
        viewResultsButton.setBounds(50, 100, 200, 25);
        panel.add(viewResultsButton);

        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddQuestion();
            }
        });

        takeQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuizScreen();
            }
        });

        viewResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResultScreen();
            }
        });
    }
}
