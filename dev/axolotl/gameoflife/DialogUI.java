package dev.axolotl.gameoflife;

import javax.swing.*;
import java.awt.*;

public class DialogUI {
    private JFrame frame;
    private JLabel dialogLabel;

    public DialogUI() {
        frame = new JFrame("Dialog UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 200));

        dialogLabel = new JLabel();

        frame.getContentPane().add(dialogLabel);

        frame.pack();
        frame.setVisible(true);
    }

    public void pushDialog(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        dialogLabel.setText(sb.toString());
    }
}
