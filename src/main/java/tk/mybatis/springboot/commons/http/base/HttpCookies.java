package tk.mybatis.springboot.commons.http.base;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

public class HttpCookies {

    private HttpClientContext context;
    private CookieStore cookieStore;

    public static HttpCookies custom()
    {
        return new HttpCookies();
    }

    private HttpCookies()
    {
        this.context = new HttpClientContext();
        this.cookieStore = new BasicCookieStore();
        this.context.setCookieStore(this.cookieStore);
    }

    public HttpClientContext getContext()
    {
        return this.context;
    }

    public HttpCookies setContext(HttpClientContext context)
    {
        this.context = context;
        return this;
    }

    public CookieStore getCookieStore()
    {
        return this.cookieStore;
    }

    public HttpCookies setCookieStore(CookieStore cookieStore)
    {
        this.cookieStore = cookieStore;
        return this;
    }
}
