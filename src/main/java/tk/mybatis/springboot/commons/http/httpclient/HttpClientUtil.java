package tk.mybatis.springboot.commons.http.httpclient;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.config.Lookup;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.springboot.commons.http.base.HttpConfig;
import tk.mybatis.springboot.commons.http.base.HttpMethods;
import tk.mybatis.springboot.commons.http.base.Utils;
import tk.mybatis.springboot.commons.http.exception.HttpProcessException;
import tk.mybatis.springboot.commons.http.httpclient.builder.HCB;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    private static final transient Logger LOG = LoggerFactory.getLogger(HttpAsyncClientUtil.class);

    private static HttpClient create(String url, Lookup<CookieSpecProvider> cookieSpecRegistry)
            throws HttpProcessException
    {
        if (url.toLowerCase().startsWith("https://")) {
            return cookieSpecRegistry == null ? HCB.custom().ssl().build() :
                    HCB.custom().ssl().setDefaultCookieSpecRegistry(cookieSpecRegistry).build();
        }
        return cookieSpecRegistry == null ? HCB.custom().build() :
                HCB.custom().setDefaultCookieSpecRegistry(cookieSpecRegistry).build();
    }

    public static String get(HttpClient client, String url, Header[] headers, HttpContext context, String encoding)
            throws HttpProcessException
    {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.GET)
                .headers(headers).context(context).encoding(encoding));
    }

    public static String get(HttpConfig config)
            throws HttpProcessException
    {
        return send(config.method(HttpMethods.GET));
    }

    public static String post(HttpClient client, String url, Header[] headers, Map<String, Object> parasMap, HttpContext context, String encoding)
            throws HttpProcessException
    {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.POST)
                .headers(headers).map(parasMap).context(context).encoding(encoding));
    }

    public static String post(HttpConfig config)
            throws HttpProcessException
    {
        return send(config.method(HttpMethods.POST));
    }

    public static String put(HttpClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding)
            throws HttpProcessException
    {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.PUT)
                .headers(headers).map(parasMap).context(context).encoding(encoding));
    }

    public static String put(HttpConfig config)
            throws HttpProcessException
    {
        return send(config.method(HttpMethods.PUT));
    }

    public static String delete(HttpClient client, String url, Header[] headers, HttpContext context, String encoding)
            throws HttpProcessException
    {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.DELETE)
                .headers(headers).context(context).encoding(encoding));
    }

    public static String delete(HttpConfig config)
            throws HttpProcessException
    {
        return send(config.method(HttpMethods.DELETE));
    }

    public static String patch(HttpClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding)
            throws HttpProcessException
    {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.PATCH)
                .headers(headers).map(parasMap).context(context).encoding(encoding));
    }

    public static String patch(HttpConfig config)
            throws HttpProcessException
    {
        return send(config.method(HttpMethods.PATCH));
    }

    public static String head(HttpClient client, String url, Header[] headers, HttpContext context, String encoding)
            throws HttpProcessException
    {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.HEAD)
                .headers(headers).context(context).encoding(encoding));
    }

    public static String head(HttpConfig config)
            throws HttpProcessException
    {
        return send(config.method(HttpMethods.HEAD));
    }

    public static String options(HttpClient client, String url, Header[] headers, HttpContext context, String encoding)
            throws HttpProcessException
    {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.OPTIONS)
                .headers(headers).context(context).encoding(encoding));
    }

    public static String options(HttpConfig config)
            throws HttpProcessException
    {
        return send(config.method(HttpMethods.OPTIONS));
    }

    public static String trace(HttpClient client, String url, Header[] headers, HttpContext context, String encoding)
            throws HttpProcessException
    {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.TRACE)
                .headers(headers).context(context).encoding(encoding));
    }

    public static String trace(HttpConfig config)
            throws HttpProcessException
    {
        return send(config.method(HttpMethods.TRACE));
    }

    public static OutputStream down(HttpClient client, String url, Header[] headers, HttpContext context, OutputStream out)
            throws HttpProcessException
    {
        return fmt2Stream(execute(HttpConfig.custom().client(client).url(url)
                .method(HttpMethods.GET).headers(headers).context(context).out(out)), out);
    }

    public static OutputStream down(HttpConfig config)
            throws HttpProcessException
    {
        return fmt2Stream(execute(config.method(HttpMethods.GET)), config.out());
    }

    public static String send(HttpConfig config)
            throws HttpProcessException
    {
        return fmt2String(execute(config), config.outenc());
    }

    private static HttpResponse execute(HttpConfig config)
            throws HttpProcessException
    {
        if (config.client() == null) {
            config.client(create(config.url(), config.cookieSpecRegistry()));
        }
        HttpResponse resp = null;
        try
        {
            HttpRequestBase request = getRequest(config.url(), config.method());

            request.setHeaders(config.headers());
            request.setConfig(request.getConfig() == null ? config
                    .requestConfig() : request.getConfig());
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass()))
            {
                List<NameValuePair> nvps = new ArrayList();

                config.url(Utils.checkHasParas(config.url(), nvps, config.inenc()));

                HttpEntity entity = Utils.map2List(nvps, config.map(), config.inenc());

                ((HttpEntityEnclosingRequestBase)request).setEntity(entity);

                LOG.info("请求地址：" + config.url());
                if (nvps.size() > 0) {
                    LOG.info("请求参数：" + nvps.toString());
                }
            }
            else
            {
                int idx = config.url().indexOf("?");
                LOG.info("请求地址：" + config
                        .url().substring(0, idx > 0 ? idx : config.url().length()));
                if (idx > 0) {
                    LOG.info("请求参数：" + config.url().substring(idx + 1));
                }
            }
            if (request.getConfig() != null) {
                LOG.info("requestConfig:" + JSONObject.toJSONString(request.getConfig()));
            }
            resp = config.context() == null ? config.client().execute(request) : config.client().execute(request, config.context());
            if (config.isReturnRespHeaders()) {
                config.headers(resp.getAllHeaders());
            }
            return resp;
        }
        catch (IOException e)
        {
            throw new HttpProcessException(e);
        }
    }

    private static String fmt2String(HttpResponse resp, String encoding)
            throws HttpProcessException
    {
        String body = "";
        try
        {
            if (resp.getEntity() != null)
            {
                body = EntityUtils.toString(resp.getEntity(), encoding);
                LOG.debug(body);
            }
            EntityUtils.consume(resp.getEntity());
        }
        catch (IOException e)
        {
            throw new HttpProcessException(e);
        }
        finally
        {
            close(resp);
        }
        return body;
    }

    public static OutputStream fmt2Stream(HttpResponse resp, OutputStream out)
            throws HttpProcessException
    {
        try
        {
            resp.getEntity().writeTo(out);
            EntityUtils.consume(resp.getEntity());
        }
        catch (IOException e)
        {
            throw new HttpProcessException(e);
        }
        finally
        {
            close(resp);
        }
        return out;
    }

    private static HttpRequestBase getRequest(String url, HttpMethods method)
    {
        HttpRequestBase request = null;
        switch (method.getCode())
        {
            case 0:
                request = new HttpGet(url);
                break;
            case 1:
                request = new HttpPost(url);
                break;
            case 2:
                request = new HttpHead(url);
                break;
            case 3:
                request = new HttpPut(url);
                break;
            case 4:
                request = new HttpDelete(url);
                break;
            case 5:
                request = new HttpTrace(url);
                break;
            case 6:
                request = new HttpPatch(url);
                break;
            case 7:
                request = new HttpOptions(url);
                break;
            default:
                request = new HttpPost(url);
        }
        return request;
    }

    private static void close(HttpResponse resp)
    {
        try
        {
            if (resp == null) {
                return;
            }
            if (CloseableHttpResponse.class.isAssignableFrom(resp.getClass())) {
                ((CloseableHttpResponse)resp).close();
            }
        }
        catch (IOException e)
        {
            LOG.error("close error", e);
        }
    }

}
