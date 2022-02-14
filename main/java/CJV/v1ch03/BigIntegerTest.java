package CJV.v1ch03;

import java.math.*;
import java.util.Scanner;

public class BigIntegerTest {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("How many numbers do you need to draw?");
        int k = in.nextInt();

        System.out.println("What is the highest number you can draw?");
        int n = in.nextInt();

        BigInteger lotteryOdds = BigInteger.valueOf(1);
        for (int i = 1; i <= k; i++)
            lotteryOdds = lotteryOdds
                    .multiply(BigInteger.valueOf(n - i + 1))
                    .divide(BigInteger.valueOf(i));

        System.out.printf("Your odds are 1 in %s. Good luck!%n", lotteryOdds);
    }
}
