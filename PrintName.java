public class PrintName {
    public static void main(String[] arg)
    {
        String username = System.getProperty("user.name");
        System.out.print("Your name is: " + username + "\n");
    }
}
