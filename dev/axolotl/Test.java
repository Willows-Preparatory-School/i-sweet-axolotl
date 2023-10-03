package dev.axolotl;

public class Test {
    public static void main(String[] args)
    {
        writeSpaces(10);
    }

    public static void writeSpaces(int how_many_spaces_does_our_terrible_user_want)
    {
        for (int i = 0; i < how_many_spaces_does_our_terrible_user_want; i++) {
            System.out.print(" ");
        }
    }
}