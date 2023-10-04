package dev.axolotl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dev.axolotl.ColonThreeDialog;

public class ForLoops2 {
    public static void main(String[] args)
    {
        int sum = 0;
        for (int i = 100; i > 0; i--) {
            sum +=i;
            System.out.println(i);
        }
        System.out.println(sum);
        // JOptionPane.showMessageDialog(null, "This is a message dialog.");
        // JOptionPane.showMessageDialog(null, ":3");
        Icon icon_3;
        new ColonThreeDialog(":3", icon_3);
    }
}
