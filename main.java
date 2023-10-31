public class main {
    public static void main(String[] args)
    {
        System.out.print("Hello, World!\n");
        int[] array = {1, 1, 1 ,1 ,1};
        System.out.println(fast_power(10,12));
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
