package tk.mybatis.springboot.base.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidationError
{
    private String propertyName;
    private String propertyValue;
    private String message;

    public String getPropertyName()
    {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public String getPropertyValue()
    {
        return this.propertyValue;
    }

    public void setPropertyValue(String propertyValue)
    {
        this.propertyValue = propertyValue;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
