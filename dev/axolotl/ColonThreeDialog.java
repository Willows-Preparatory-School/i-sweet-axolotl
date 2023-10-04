package dev.axolotl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColonThreeDialog extends JDialog {

    private JLabel label;
    private JButton button;

    public ColonThreeDialog(String title, Icon icon) {
        super();

        label = new JLabel("This is a dialog");
        button = new JButton("OK");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        getContentPane().add(label);
        getContentPane().add(button);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
