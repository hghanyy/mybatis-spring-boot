package tk.mybatis.springboot.commons.http.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import tk.mybatis.springboot.commons.http.exception.HttpProcessException;

public class SSLs {

    private static final SSLHandler simpleVerifier = new SSLHandler(null);
    private static SSLSocketFactory sslFactory;
    private static SSLConnectionSocketFactory sslConnFactory;
    private static SSLIOSessionStrategy sslIOSessionStrategy;
    private static SSLs sslutil = new SSLs();
    private SSLContext sc;

    public static SSLs getInstance()
    {
        return sslutil;
    }

    public static SSLs custom()
    {
        return new SSLs();
    }

    private static class SSLHandler
            implements X509TrustManager, HostnameVerifier
    {
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[0];
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException
        {}

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException
        {}

        public boolean verify(String paramString, SSLSession paramSSLSession)
        {
            return true;
        }
    }

    public static HostnameVerifier getVerifier()
    {
        return simpleVerifier;
    }

    public synchronized SSLSocketFactory getSSLSF()
            throws HttpProcessException
    {
        if (sslFactory != null) {
            return sslFactory;
        }
        try
        {
            SSLContext sc = getSSLContext();
            sc.init(null, new TrustManager[] { simpleVerifier }, null);
            sslFactory = sc.getSocketFactory();
        }
        catch (KeyManagementException e)
        {
            throw new HttpProcessException(e);
        }
        return sslFactory;
    }

    public synchronized SSLConnectionSocketFactory getSSLCONNSF()
            throws HttpProcessException
    {
        if (sslConnFactory != null) {
            return sslConnFactory;
        }
        try
        {
            SSLContext sc = getSSLContext();

            sc.init(null, new TrustManager[] { simpleVerifier }, new SecureRandom());
            sslConnFactory = new SSLConnectionSocketFactory(sc, simpleVerifier);
        }
        catch (KeyManagementException e)
        {
            throw new HttpProcessException(e);
        }
        return sslConnFactory;
    }

    public synchronized SSLIOSessionStrategy getSSLIOSS()
            throws HttpProcessException
    {
        if (sslIOSessionStrategy != null) {
            return sslIOSessionStrategy;
        }
        try
        {
            SSLContext sc = getSSLContext();

            sc.init(null, new TrustManager[] { simpleVerifier }, new SecureRandom());
            sslIOSessionStrategy = new SSLIOSessionStrategy(sc, simpleVerifier);
        }
        catch (KeyManagementException e)
        {
            throw new HttpProcessException(e);
        }
        return sslIOSessionStrategy;
    }

    public SSLs customSSL(String keyStorePath, String keyStorepass)
            throws HttpProcessException
    {
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try
        {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File(keyStorePath));
            trustStore.load(instream, keyStorepass.toCharArray());

            this.sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();

            return this;
        }
        catch (KeyManagementException e)
        {
            throw new HttpProcessException(e);
        }
        catch (KeyStoreException e)
        {
            throw new HttpProcessException(e);
        }
        catch (FileNotFoundException e)
        {
            throw new HttpProcessException(e);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new HttpProcessException(e);
        }
        catch (CertificateException e)
        {
            throw new HttpProcessException(e);
        }
        catch (IOException e)
        {
            throw new HttpProcessException(e);
        }
        finally
        {
            try
            {
                instream.close();
            }
            catch (IOException localIOException2) {}
        }
    }

    public SSLContext getSSLContext()
            throws HttpProcessException
    {
        try
        {
            if (this.sc == null) {
                this.sc = SSLContext.getInstance("SSLv3");
            }
            return this.sc;
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new HttpProcessException(e);
        }
    }

}
