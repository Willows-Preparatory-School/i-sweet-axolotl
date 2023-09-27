package src.main.java.org.example;

public class PrintName {
    public static void main(String[] args) {
        String username = System.getProperty("user.name");
        System.out.print("Your name is: " + username + "\n");
    }
}