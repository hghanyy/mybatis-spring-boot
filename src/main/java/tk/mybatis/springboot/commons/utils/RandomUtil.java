package tk.mybatis.springboot.commons.utils;

import java.util.Random;

public class RandomUtil
{
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERCHAR = "0123456789";

    public static String generateString(int length)
    {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
        }
        return sb.toString();
    }

    public static String generateNumber(int length)
    {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append("0123456789".charAt(random.nextInt("0123456789".length())));
        }
        return sb.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(generateNumber(6));
    }
}
