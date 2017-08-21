package tk.mybatis.springboot.commons.http.httpclient.builder;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import tk.mybatis.springboot.commons.http.base.SSLs;
import tk.mybatis.springboot.commons.http.exception.HttpProcessException;

public class HCB
        extends HttpClientBuilder {
    private boolean isSetPool = false;
    private boolean isNewSSL = false;
    private SSLs ssls = SSLs.getInstance();

    public static HCB custom() {
        return new HCB();
    }

    public HCB timeout(int timeout) {
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        return (HCB) setDefaultRequestConfig(config);
    }

    public HCB ssl()
            throws HttpProcessException {
        if (this.isSetPool) {
            if (this.isNewSSL) {
                throw new HttpProcessException("请先设置ssl,后设置pool");
            }
            return this;
        }
        Registry<Object> socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", this.ssls.getSSLCONNSF()).build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        return (HCB) setConnectionManager(connManager);
    }

    public HCB ssl(String keyStorePath)
            throws HttpProcessException {
        return ssl(keyStorePath, "nopassword");
    }

    public HCB ssl(String keyStorePath, String keyStorepass)
            throws HttpProcessException {
        this.ssls = SSLs.custom().customSSL(keyStorePath, keyStorepass);
        this.isNewSSL = true;
        return ssl();
    }

    public HCB pool(int maxTotal, int defaultMaxPerRoute)
            throws HttpProcessException {
        Registry<Object> socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", this.ssls.getSSLCONNSF()).build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        this.isSetPool = true;
        return (HCB) setConnectionManager(connManager);
    }

    public HCB proxy(String hostOrIP, int port) {
        HttpHost proxy = new HttpHost(hostOrIP, port, "http");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        return (HCB) setRoutePlanner(routePlanner);
    }
}
