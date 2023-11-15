import java.util.Arrays;
import java.util.Scanner;

public class main {
    public static void main(String[] args)
    {
        System.out.print("Hello, World!\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Total Amount of nums: ");
        int totalNumSum = scanner.nextInt();
        int[] numList = {1,5,6,7};
        for(int i = 0; i < totalNumSum;i++)
        {
            int next = scanner.nextInt();
            numList[i] = next;
        }
        System.out.println(numList);
    }

    public static long fast_power(long base, long power)
    {
        long MOD = 1000000007;
        long result = 1;
        while(power > 0) {

            if(power % 2 == 1)
            {
                result = (result*base) % MOD;
            }
            base = (base * base) % MOD;
            power = power / 2;
        }
        return result;
    }
}
