package dev.axolotl;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import com.fazecast.jSerialComm.SerialPort;

public class joyStick {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            SerialPort serialPort = SerialPort.getCommPort("COM10"); // Replace "COM3" with the appropriate serial port
            serialPort.setBaudRate(9600);
            if (serialPort.openPort()) {
                System.out.println("Serial port opened.");
                byte[] buffer = new byte[1024];
                int bytesRead;
                while (true) {
                    bytesRead = serialPort.readBytes(buffer, buffer.length);
                    if (bytesRead > 0) {
                        String input = new String(buffer, 0, bytesRead);
                        String[] values = input.trim().split(",");
                        if (values.length == 2 && !values[0].isEmpty() && !values[1].isEmpty()) {
                            int joyX = Integer.parseInt(values[0]);
                            int joyY = Integer.parseInt(values[1]);
                            // map joystick values to screen coordinates
                            int screenWidth = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                            int screenHeight = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                            int mouseX = map(joyX, -32768, 32767, 0, screenWidth);
                            int mouseY = map(joyY, -32768, 32767, 0, screenHeight);
                            // move mouse to new coordinates
                            Point currentMouseLocation = MouseInfo.getPointerInfo().getLocation();
                            int deltaX = mouseX - currentMouseLocation.x;
                            int deltaY = mouseY - currentMouseLocation.y;
                            robot.mouseMove(currentMouseLocation.x + deltaX, currentMouseLocation.y + deltaY);
                        }
                    }
                }
            } else {
                System.out.println("Failed to open serial port.");
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private static int map(int value, int inMin, int inMax, int outMin, int outMax) {
        return (value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }
}
