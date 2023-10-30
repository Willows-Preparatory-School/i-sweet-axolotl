package dev.axolotl;

import java.awt.*;
import java.util.Scanner;

public class pokemon_ev
{
    public static Scanner userinput = new Scanner(System.in);

    public static void main(String arg[])
    {
        Frame frame = new Frame("Pokemon EV Calculator");
        frame.setSize(300, 200);
        frame.setVisible(true);

        System.out.print("Hit points: ");
        double hp = userinput.nextInt();
        // System.out.println(hp);
        System.out.print("Level: ");
        double level = userinput.nextInt();
        System.out.print("IV: ");
        double iv = userinput.nextInt();
        System.out.print("Base HP: ");
        double base = userinput.nextInt();
        double ev = (((hp - 10) * 100)/ level - 2 * base - iv - 100) * 4;
        System.out.println(ev);

        int w = 0;
        int h = 0;
        while (true)
        {

        }
    }
}
