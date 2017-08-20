package tk.mybatis.springboot.commons.http.base;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static final String ENTITY_STRING = "$ENTITY_STRING$";
    public static final String ENTITY_FILE = "$ENTITY_FILEE$";
    public static final String ENTITY_BYTES = "$ENTITY_BYTES$";
    public static final String ENTITY_INPUTSTREAM = "$ENTITY_INPUTSTREAM$";
    public static final String ENTITY_SERIALIZABLE = "$ENTITY_SERIALIZABLE$";
    private static final List<String> SPECIAL_ENTITIY = Arrays.asList(new String[] { "$ENTITY_STRING$", "$ENTITY_BYTES$", "$ENTITY_FILEE$", "$ENTITY_INPUTSTREAM$", "$ENTITY_SERIALIZABLE$" });

    public static String checkHasParas(String url, List<NameValuePair> nvps, String encoding)
            throws UnsupportedEncodingException
    {
        if ((url.contains("?")) && (url.indexOf("?") < url.indexOf("=")))
        {
            Map<String, Object> map = buildParas(url.substring(url.indexOf("?") + 1));
            map2List(nvps, map, encoding);
            url = url.substring(0, url.indexOf("?"));
        }
        return url;
    }

    public static HttpEntity map2List(List<NameValuePair> nvps, Map<String, Object> map, String encoding)
            throws UnsupportedEncodingException
    {
        HttpEntity entity = null;
        if ((map != null) && (map.size() > 0))
        {
            boolean isSpecial = false;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (SPECIAL_ENTITIY.contains(entry.getKey()))
                {
                    isSpecial = true;
                    if ("$ENTITY_STRING$".equals(entry.getKey()))
                    {
                        entity = new StringEntity(String.valueOf(entry.getValue()), encoding);
                        break;
                    }
                    if ("$ENTITY_BYTES$".equals(entry.getKey()))
                    {
                        entity = new ByteArrayEntity((byte[])entry.getValue());
                        break;
                    }
                    if ("$ENTITY_FILEE$".equals(entry.getKey())) {
                        break;
                    }
                    if ("$ENTITY_INPUTSTREAM$".equals(entry.getKey())) {
                        break;
                    }
                    if ("$ENTITY_SERIALIZABLE$".equals(entry.getKey())) {
                        break;
                    }
                    nvps.add(new BasicNameValuePair((String)entry.getKey(),
                            String.valueOf(entry.getValue())));
                }
                else
                {
                    nvps.add(new BasicNameValuePair((String)entry.getKey(),
                            String.valueOf(entry.getValue())));
                }
            }
            if (!isSpecial) {
                entity = new UrlEncodedFormEntity(nvps, encoding);
            }
        }
        return entity;
    }

    public static Map<String, Object> buildParas(String paras)
    {
        String[] p = paras.split("&");
        String[][] ps = new String[p.length][2];
        int pos = 0;
        for (int i = 0; i < p.length; i++)
        {
            pos = p[i].indexOf("=");
            ps[i][0] = p[i].substring(0, pos);
            ps[i][1] = p[i].substring(pos + 1);
            pos = 0;
        }
        return buildParas(ps);
    }

    public static Map<String, Object> buildParas(String[][] paras)
    {
        Map<String, Object> map = new HashMap();
        for (String[] para : paras) {
            map.put(para[0], para[1]);
        }
        return map;
    }
}
