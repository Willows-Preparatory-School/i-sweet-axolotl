package dev.axolotl.chapter3;

public class chapter3 {
    static double roadTrip(double circle_diameter)
    {
        return circle_diameter*Math.PI;
    }

    static double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow((x2 - x1), 2)+Math.pow((y2 - y1),2));
    }

    static double totalTrip(double x1, double y1, double x2, double y2, double x3, double y3)
    {
        return distance(x1, y1, x2, y2) + distance(x2, y2, x3, y3) + distance(x3, y3, x1, y1);
    }

    public static void main(String[] args)
    {
        System.out.println("Gwangji to Busan: " + distance(16, 21, 4, 21));
        System.out.println("Busan to Seoul: " + distance(4 ,21, 15, 6));
        System.out.println("Total distance: " + totalTrip(16, 21, 4, 21, 15, 6));
    }
}
