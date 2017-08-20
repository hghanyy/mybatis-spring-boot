package tk.mybatis.springboot.commons.http.base;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Lookup;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HttpContext;
import tk.mybatis.springboot.commons.http.httpclient.HttpAsyncClientUtil;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpConfig {
    private HttpClient client;
    private CloseableHttpAsyncClient asynclient;
    private String url;
    private Header[] headers;
    private boolean isReturnRespHeaders;

    public static HttpConfig custom()
    {
        return new HttpConfig();
    }

    private HttpMethods method = HttpMethods.GET;
    private String methodName;
    private HttpContext context;
    private Map<String, Object> map;
    private String encoding = Charset.defaultCharset().displayName();
    private String inenc;
    private String outenc;
    private OutputStream out;
    private HttpAsyncClientUtil.IHandler handler;
    private Lookup<CookieSpecProvider> cookieSpecRegistry;
    private RequestConfig requestConfig;

    public HttpConfig requestConfig(RequestConfig requestConfig)
    {
        this.requestConfig = requestConfig;
        return this;
    }

    public HttpConfig cookieSpecRegistry(Lookup<CookieSpecProvider> cookieSpecRegistry)
    {
        this.cookieSpecRegistry = cookieSpecRegistry;
        return this;
    }

    public HttpConfig client(HttpClient client)
    {
        this.client = client;
        return this;
    }

    public HttpConfig asynclient(CloseableHttpAsyncClient asynclient)
    {
        this.asynclient = asynclient;
        return this;
    }

    public HttpConfig url(String url)
    {
        this.url = url;
        return this;
    }

    public HttpConfig headers(Header[] headers)
    {
        this.headers = headers;
        return this;
    }

    public HttpConfig headers(Header[] headers, boolean isReturnRespHeaders)
    {
        this.headers = headers;
        this.isReturnRespHeaders = isReturnRespHeaders;
        return this;
    }

    public HttpConfig method(HttpMethods method)
    {
        this.method = method;
        return this;
    }

    public HttpConfig methodName(String methodName)
    {
        this.methodName = methodName;
        return this;
    }

    public HttpConfig context(HttpContext context)
    {
        this.context = context;
        return this;
    }

    public HttpConfig map(Map<String, Object> map)
    {
        this.map = map;
        return this;
    }

    public HttpConfig encoding(String encoding)
    {
        inenc(encoding);
        outenc(encoding);
        this.encoding = encoding;
        return this;
    }

    public HttpConfig inenc(String inenc)
    {
        this.inenc = inenc;
        return this;
    }

    public HttpConfig outenc(String outenc)
    {
        this.outenc = outenc;
        return this;
    }

    public HttpConfig out(OutputStream out)
    {
        this.out = out;
        return this;
    }

    public HttpConfig handler(HttpAsyncClientUtil.IHandler handler)
    {
        this.handler = handler;
        return this;
    }

    public HttpClient client()
    {
        return this.client;
    }

    public CloseableHttpAsyncClient asynclient()
    {
        return this.asynclient;
    }

    public Header[] headers()
    {
        return this.headers;
    }

    public boolean isReturnRespHeaders()
    {
        return this.isReturnRespHeaders;
    }

    public String url()
    {
        return this.url;
    }

    public HttpMethods method()
    {
        return this.method;
    }

    public String methodName()
    {
        return this.methodName;
    }

    public HttpContext context()
    {
        return this.context;
    }

    public Map<String, Object> map()
    {
        return this.map;
    }

    public String encoding()
    {
        return this.encoding;
    }

    public String inenc()
    {
        return this.inenc == null ? this.encoding : this.inenc;
    }

    public String outenc()
    {
        return this.outenc == null ? this.encoding : this.outenc;
    }

    public OutputStream out()
    {
        return this.out;
    }

    public HttpAsyncClientUtil.IHandler handler()
    {
        return this.handler;
    }

    public Lookup<CookieSpecProvider> cookieSpecRegistry()
    {
        return this.cookieSpecRegistry;
    }

    public RequestConfig requestConfig()
    {
        return this.requestConfig;
    }
}
