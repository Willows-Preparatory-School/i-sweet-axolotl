package dev.axolotl.homework;

public class Stewie2 {

    public static void why_do_we_have_to_do_this ()
    {
        System.out.print("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
        System.out.print("|| Victory is mine! ||\n");
        System.out.print("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
    }

    public static void main(String[] args)
    {
        // Print that one with a one-off slash line
        System.out.print("//////////////////////\n");
        System.out.print("|| Victory is mine! ||\n");
        System.out.print("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");

        // I'm going to assume we print four of the other ones, can't really see since the page scan is cut off.
        for (int i = 0; i < 4; i++) {
            why_do_we_have_to_do_this();
        }
    }
}
