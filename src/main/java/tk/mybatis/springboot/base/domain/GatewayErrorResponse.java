package tk.mybatis.springboot.base.domain;

import java.io.Serializable;

public class GatewayErrorResponse
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String error_description;
    private String error;

    public GatewayErrorResponse() {}

    public GatewayErrorResponse(String error_description, String error)
    {
        this.error_description = error_description;
        this.error = error;
    }

    public String getError_description()
    {
        return this.error_description;
    }

    public void setError_description(String error_description)
    {
        this.error_description = error_description;
    }

    public String getError()
    {
        return this.error;
    }

    public void setError(String error)
    {
        this.error = error;
    }
}
