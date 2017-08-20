package tk.mybatis.springboot.commons.http.httpclient.builder;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import tk.mybatis.springboot.commons.http.base.SSLs;
import tk.mybatis.springboot.commons.http.exception.HttpProcessException;

public class HACB
        extends HttpAsyncClientBuilder
{
    private boolean isSetPool = false;
    private boolean isNewSSL = false;
    private SSLs ssls = SSLs.getInstance();

    public static HACB custom()
    {
        return new HACB();
    }

    public HACB timeout(int timeout)
    {
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        return (HACB)setDefaultRequestConfig(config);
    }

    public HACB ssl()
            throws HttpProcessException
    {
        if (this.isSetPool)
        {
            if (this.isNewSSL) {
                throw new HttpProcessException("请先设置ssl,后设置pool");
            }
            return this;
        }
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder.create().register("http", NoopIOSessionStrategy.INSTANCE).register("https", this.ssls.getSSLIOSS()).build();

        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors()).build();
        try
        {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        }
        catch (IOReactorException e)
        {
            ConnectingIOReactor ioReactor;
            throw new HttpProcessException(e);
        }
        ConnectingIOReactor ioReactor;
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor, null, sessionStrategyRegistry, null);

        return (HACB)setConnectionManager(connManager);
    }

    public HACB ssl(String keyStorePath)
            throws HttpProcessException
    {
        return ssl(keyStorePath, "nopassword");
    }

    public HACB ssl(String keyStorePath, String keyStorepass)
            throws HttpProcessException
    {
        this.ssls = SSLs.custom().customSSL(keyStorePath, keyStorepass);
        this.isNewSSL = true;
        return ssl();
    }

    public HACB pool(int maxTotal, int defaultMaxPerRoute)
            throws HttpProcessException
    {
        if (this.isSetPool) {
            return this;
        }
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder.create().register("http", NoopIOSessionStrategy.INSTANCE).register("https", this.ssls.getSSLIOSS()).build();

        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(12).build();
        try
        {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        }
        catch (IOReactorException e)
        {
            ConnectingIOReactor ioReactor;
            throw new HttpProcessException(e);
        }
        ConnectingIOReactor ioReactor;
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor, null, sessionStrategyRegistry, null);

        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        this.isSetPool = true;
        return (HACB)setConnectionManager(connManager);
    }

    public HACB proxy(String hostOrIP, int port)
    {
        HttpHost proxy = new HttpHost(hostOrIP, port, "http");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        return (HACB)setRoutePlanner(routePlanner);
    }
}
