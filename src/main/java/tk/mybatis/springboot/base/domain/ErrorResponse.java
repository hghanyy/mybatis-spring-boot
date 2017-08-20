package tk.mybatis.springboot.base.domain;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

    private String resultCode;
    private String resultMessage;
    private List<ValidationError> validationErrors = new ArrayList();

    public ErrorResponse() {}

    public ErrorResponse(String errorCode, String resultMessage)
    {
        this.resultCode = errorCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode()
    {
        return this.resultCode;
    }

    public void setResultCode(String errorCode)
    {
        this.resultCode = errorCode;
    }

    public String getResultMessage()
    {
        return this.resultMessage;
    }

    public void setResultMessage(String resultMessage)
    {
        this.resultMessage = resultMessage;
    }

    public List<ValidationError> getValidationErrors()
    {
        return this.validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors)
    {
        this.validationErrors = validationErrors;
    }

}
