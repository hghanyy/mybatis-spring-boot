package tk.mybatis.springboot.commons.utils;

import java.util.UUID;

public class UUIDUtil {
    public static String genId32()
    {
        return UUID.randomUUID().toString().replaceAll("\\-", "").toUpperCase();
    }

    public static int genShortId()
    {
        return genId32().hashCode();
    }
}
