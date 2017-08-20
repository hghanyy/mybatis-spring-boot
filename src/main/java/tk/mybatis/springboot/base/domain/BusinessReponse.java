package tk.mybatis.springboot.base.domain;

import java.io.Serializable;

public class BusinessReponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String resultCode;
    private String resultMessage;

    public BusinessReponse() {}

    public BusinessReponse(String resultCode, String resultMessage)
    {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode()
    {
        return this.resultCode;
    }

    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getResultMessage()
    {
        return this.resultMessage;
    }

    public void setResultMessage(String resultMessage)
    {
        this.resultMessage = resultMessage;
    }
}
