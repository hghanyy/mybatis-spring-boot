package tk.mybatis.springboot.commons.http.exception;

public class HttpProcessException
        extends Exception
{
    private static final long serialVersionUID = -2749168865492921426L;

    public HttpProcessException(Exception e)
    {
        super(e);
    }

    public HttpProcessException(String msg)
    {
        super(msg);
    }

    public HttpProcessException(String message, Exception e)
    {
        super(message, e);
    }
}
