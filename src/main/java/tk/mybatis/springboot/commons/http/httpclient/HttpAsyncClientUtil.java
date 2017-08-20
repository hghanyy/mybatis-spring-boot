package tk.mybatis.springboot.commons.http.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.springboot.commons.http.base.HttpConfig;
import tk.mybatis.springboot.commons.http.base.HttpMethods;
import tk.mybatis.springboot.commons.http.base.Utils;
import tk.mybatis.springboot.commons.http.exception.HttpProcessException;
import tk.mybatis.springboot.commons.http.httpclient.builder.HACB;


public class HttpAsyncClientUtil {
    private static final transient Logger LOG = LoggerFactory.getLogger(HttpAsyncClientUtil.class);

    public static CloseableHttpAsyncClient create(String url)
            throws HttpProcessException
    {
        if (url.toLowerCase().startsWith("https://")) {
            return HACB.custom().ssl().build();
        }
        return HACB.custom().build();
    }

    public static void send(HttpConfig config)
            throws HttpProcessException
    {
        execute(config);
    }

    public static void get(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler)
            throws HttpProcessException
    {
        send(
                HttpConfig.custom().method(HttpMethods.GET).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    public static void get(HttpConfig config)
            throws HttpProcessException
    {
        send(config.method(HttpMethods.GET));
    }

    public static void post(CloseableHttpAsyncClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler)
            throws HttpProcessException
    {
        send(
                HttpConfig.custom().method(HttpMethods.POST).asynclient(client).url(url).map(parasMap).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    public static void post(HttpConfig config)
            throws HttpProcessException
    {
        send(config.method(HttpMethods.POST));
    }

    public static void put(CloseableHttpAsyncClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler)
            throws HttpProcessException
    {
        send(
                HttpConfig.custom().method(HttpMethods.PUT).asynclient(client).url(url).map(parasMap).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    public static void put(HttpConfig config)
            throws HttpProcessException
    {
        send(config.method(HttpMethods.PUT));
    }

    public static void delete(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler)
            throws HttpProcessException
    {
        send(
                HttpConfig.custom().method(HttpMethods.DELETE).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    public static void delete(HttpConfig config)
            throws HttpProcessException
    {
        send(config.method(HttpMethods.DELETE));
    }

    public static void patch(CloseableHttpAsyncClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler)
            throws HttpProcessException
    {
        send(
                HttpConfig.custom().method(HttpMethods.PATCH).asynclient(client).url(url).map(parasMap).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    public static void patch(HttpConfig config)
            throws HttpProcessException
    {
        send(config.method(HttpMethods.PATCH));
    }

    public static void head(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler)
            throws HttpProcessException
    {
        send(
                HttpConfig.custom().method(HttpMethods.HEAD).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    public static void head(HttpConfig config)
            throws HttpProcessException
    {
        send(config.method(HttpMethods.HEAD));
    }

    public static void options(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler)
            throws HttpProcessException
    {
        send(
                HttpConfig.custom().method(HttpMethods.OPTIONS).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    public static void options(HttpConfig config)
            throws HttpProcessException
    {
        send(config.method(HttpMethods.OPTIONS));
    }

    public static void trace(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler)
            throws HttpProcessException
    {
        send(
                HttpConfig.custom().method(HttpMethods.TRACE).asynclient(client).url(url).headers(headers).context(context).encoding(encoding).handler(handler));
    }

    public static void trace(HttpConfig config)
            throws HttpProcessException
    {
        send(config.method(HttpMethods.TRACE));
    }

    public static void down(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, OutputStream out)
            throws HttpProcessException
    {
        execute(
                HttpConfig.custom().method(HttpMethods.GET).asynclient(client).url(url).headers(headers).context(context).out(out));
    }

    public static void down(HttpConfig config)
            throws HttpProcessException
    {
        execute(config.method(HttpMethods.GET));
    }

    private static void execute(HttpConfig config)
            throws HttpProcessException
    {
        if (config.asynclient() == null) {
            config.asynclient(create(config.url()));
        }
        try
        {
            HttpRequestBase request = getRequest(config.url(), config.method());

            request.setHeaders(config.headers());
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass()))
            {
                List<NameValuePair> nvps = new ArrayList();

                config.url(Utils.checkHasParas(config.url(), nvps, config.inenc()));

                HttpEntity entity = Utils.map2List(nvps, config.map(), config.inenc());

                ((HttpEntityEnclosingRequestBase)request).setEntity(entity);

                LOG.info("请求地址：" + config.url());
                LOG.info("请求参数：" + nvps.toString());
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
            final CloseableHttpAsyncClient client = config.asynclient();
            final String encoding = config.outenc();
            IHandler handler = config.handler();
            final OutputStream out = config.out();

            client.start();

            client.execute(request, new FutureCallback()
            {
                public void failed(Exception e)
                {
                    this.val$handler.failed(e);
                    HttpAsyncClientUtil.close(client);
                }

                public void completed(HttpResponse resp)
                {
                    try
                    {
                        if (out == null) {
                            this.val$handler.completed(HttpAsyncClientUtil.fmt2String(resp, encoding));
                        } else {
                            this.val$handler.down(HttpAsyncClientUtil.fmt2Stream(resp, out));
                        }
                    }
                    catch (HttpProcessException e)
                    {
                        e.printStackTrace();
                    }
                    HttpAsyncClientUtil.close(client);
                }

                public void cancelled()
                {
                    this.val$handler.cancelled();
                    HttpAsyncClientUtil.close(client);
                }
            });
        }
        catch (UnsupportedEncodingException e)
        {
            throw new HttpProcessException(e);
        }
    }

    private static void close(CloseableHttpAsyncClient client)
    {
        try
        {
            client.close();
        }
        catch (IOException e)
        {
            LOG.error("close error", e);
        }
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

    private static String fmt2String(HttpResponse resp, String encoding)
            throws HttpProcessException
    {
        String body = "";
        try
        {
            HttpEntity entity = resp.getEntity();
            if (entity != null)
            {
                InputStream instream = entity.getContent();
                try
                {
                    StringBuilder sb = new StringBuilder();
                    char[] tmp = new char['?'];
                    Reader reader = new InputStreamReader(instream, encoding);
                    int l;
                    while ((l = reader.read(tmp)) != -1) {
                        sb.append(tmp, 0, l);
                    }
                    body = sb.toString();
                }
                finally
                {
                    instream.close();
                    EntityUtils.consume(entity);
                }
            }
        }
        catch (UnsupportedOperationException e)
        {
            LOG.error("UnsupportedOperationException error", e);
        }
        catch (IOException e)
        {
            LOG.error("IOException error", e);
        }
        return body;
    }

    private static OutputStream fmt2Stream(HttpResponse resp, OutputStream out)
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
    public static abstract interface IHandler
    {
        public abstract Object failed(Exception paramException);

        public abstract Object completed(String paramString);

        public abstract Object down(OutputStream paramOutputStream);

        public abstract Object cancelled();
    }
}
