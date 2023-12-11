package dev.axolotl.practice;

public class TwoRectangles
{
    public static void main(String[] args)
    {
        final int WIDTH = 7;
        final int HEIGHT = 4;
        String widthStr = "";
        // first rectangle
        for (int i = 0; i < WIDTH; i++)
        {
            widthStr = widthStr + "*";
        }
        // System.out.print(widthStr);
        for (int i = 0; i < HEIGHT; i++)
        {
            System.out.print(widthStr + "\n");
        }
        //second rectangle
        String spaceStr = "";
        for (int i = 0; i < WIDTH; i++)
        {
            spaceStr = spaceStr + " ";
        }
        for (int i = 0; i < HEIGHT; i++)
        {
            System.out.print(spaceStr + widthStr + "\n");
        }
    }
}
