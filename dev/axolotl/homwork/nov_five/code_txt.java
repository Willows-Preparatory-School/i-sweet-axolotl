package dev.axolotl.homwork.nov_five;

public class code_txt {
    public static void main (String[] arg)
    {
            // run 5 times.
            final int TREE_PARTS_NUM = 5;
            final int TREE_PARTS_LENGTH = 4;
            for(int i = 0; i < TREE_PARTS_NUM; i++){
            // draw tree "triangles"
                for(int j = 0; j < TREE_PARTS_LENGTH; j++){
                    System.out.println("*".repeat(j));
                }
            }
            // draw trunk
                System.out.println("|  |\n |  |\n\\_/");
            }
    }
