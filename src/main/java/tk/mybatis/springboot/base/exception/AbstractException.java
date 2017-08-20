package tk.mybatis.springboot.base.exception;

public abstract class AbstractException  extends RuntimeException
{
    private static final long serialVersionUID = -289364689303128644L;
    private String resultCode;
    private String resultMessage;
    private String detail;

    public String getResultCode()
    {
        return this.resultCode;
    }

    public String getResultMessage()
    {
        return this.resultMessage;
    }

    public String getDetail()
    {
        return this.detail;
    }

    public AbstractException() {}

    public AbstractException(String resultCode)
    {
        this.resultCode = resultCode;
    }

    public AbstractException(String resultCode, String resultMessage)
    {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public AbstractException(String resultCode, String resultMessage, String detail)
    {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.detail = detail;
    }
}
