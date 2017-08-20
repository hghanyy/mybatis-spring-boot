package tk.mybatis.springboot.commons.utils;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil
{
    public static boolean isBlank(String str)
    {
        if (null == str) {
            return true;
        }
        if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String str)
    {
        return (str == null) || (str.length() == 0);
    }

    public static String toString(Object obj)
    {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    public static String restrictLength(String strSrc, int iMaxLength)
    {
        if (strSrc == null) {
            return null;
        }
        if (iMaxLength <= 0) {
            return strSrc;
        }
        String strResult = strSrc;
        byte[] b = null;
        int iLength = strSrc.length();
        if (iLength > iMaxLength)
        {
            strResult = strResult.substring(0, iMaxLength);
            iLength = iMaxLength;
        }
        for (;;)
        {
            b = strResult.getBytes();
            if (b.length <= iMaxLength) {
                break;
            }
            iLength--;
            strResult = strResult.substring(0, iLength);
        }
        return strResult;
    }

    public static String getRandomString(int length)
    {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            int num = random.nextInt(str.length());
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static String lPad(String target, String fix, int length)
    {
        if ((target == null) || (fix == null) || (target.length() >= length)) {
            return target;
        }
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < length - target.length(); i++) {
            newStr.append(fix);
        }
        return target;
    }

    public static String rPad(String target, String fix, int length)
    {
        if ((target == null) || (fix == null) || (target.length() >= length)) {
            return target;
        }
        StringBuilder newStr = new StringBuilder();
        newStr.append(target);
        for (int i = 0; i < length - target.length(); i++) {
            newStr.append(fix);
        }
        return newStr.toString();
    }

    public static String join(String[] strs, String spi)
    {
        StringBuilder buf = new StringBuilder();
        int step = 0;
        for (String str : strs)
        {
            buf.append(str);
            if (step++ < strs.length - 1) {
                buf.append(spi);
            }
        }
        return buf.toString();
    }

    public static String toString2(Object obj)
    {
        if (obj == null) {
            return "无";
        }
        if ("".equals(obj)) {
            return "无";
        }
        return obj.toString();
    }

    public static String replaceServiceNumBar(String str)
    {
        String dest = "";
        if (str != null)
        {
            Pattern p = Pattern.compile("-");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static int getByteLength(String str)
    {
        try
        {
            byte[] b = str.getBytes("UTF-8");
            return b.length;
        }
        catch (Exception ex) {}
        return 0;
    }
}