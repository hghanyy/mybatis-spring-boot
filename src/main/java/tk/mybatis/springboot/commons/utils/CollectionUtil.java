package tk.mybatis.springboot.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class CollectionUtil
{
    public static boolean isEmpty(Collection<?> collection)
    {
        if (null == collection) {
            return true;
        }
        return collection.isEmpty();
    }

    public static boolean isEmpty(Object[] objects)
    {
        return (objects == null) || (objects.length == 0);
    }

    public static List<?> arrayToList(Object[] arr)
    {
        List<?> list = new ArrayList();
        if (arr == null) {
            return list;
        }
        list = Arrays.asList(arr);
        return list;
    }

    public static String split(Collection<?> collections, String separator)
    {
        Object[] array = collections.toArray(new Object[0]);
        int length = array.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            stringBuilder.append(array[i]);
            if (i != length - 1) {
                stringBuilder.append(separator);
            }
        }
        return stringBuilder.toString();
    }
}

