package dev.axolotl;

import java.lang.*;
import java.util.*;

public class RandomWordsTest {
    public static void main (String[] args) {
        long[] a = {-73, -157512326, -112386651, 71425, -104434815,
                -128911, -88019, -7691161, 1115727};
        for (int i = 0; i < a.length; i++) {
            Random r = new Random(a[i]);
            StringBuilder sb = new StringBuilder();
            int n;
            while ((n = r.nextInt(27)) > 0) sb.append((char)('`' + n));
            System.out.println(sb);
        }
    }
}
