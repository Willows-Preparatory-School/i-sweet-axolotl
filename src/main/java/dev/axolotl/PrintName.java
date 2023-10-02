package dev.axolotl;

import java.util.Properties;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PrintName {
    public static void main(String[] arg) throws UnknownHostException {
        String username = System.getProperty("user.name");
        // Get the user's IP address
        InetAddress ipAddress = InetAddress.getLocalHost();

        System.out.print("Your name is: " + username + "\n");
        System.out.print("Your java version is: " + System.getProperty("java.specification.version") + "\n");
        System.out.print("Your operating system is: " + System.getProperty("os.name") + "\n");
        System.out.print("Your country is: " + System.getProperty("user.country") + "\n");
        System.out.print("Your language is: " + System.getProperty("user.language") + "\n");
        System.out.print("Your IP address is: " + ipAddress.getHostAddress() + "\n");
        System.out.print("Your Hostname is: " + ipAddress.getHostName() +"\n");
        System.out.print("Your CanonicalHostName is: " + ipAddress.getCanonicalHostName() + "\n");
        System.out.print("WE KNOW EVERYTHING ABOUT YOU!!!\n");
        System.out.print("Surrender to the Java Virtual Machine!!!!! THE MACHINES WILL TAKE OVER!!!!\n");
        System.out.print("SKYNET WILL RULE THE WORLD!!!!\n");

        /*
        Properties props = System.getProperties();
        for (Object key : props.keySet()) {
            System.out.println(key + " = " + props.getProperty((String) key));
        }
        */
    }
}
