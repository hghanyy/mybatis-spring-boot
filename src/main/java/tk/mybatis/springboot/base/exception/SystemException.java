package tk.mybatis.springboot.base.exception;

import tk.mybatis.springboot.base.enumeration.ResultCode;

public class SystemException extends AbstractException {

    private static final long serialVersionUID = 8510738985062314322L;

    public SystemException() {}

    public SystemException(String resultMessage)
    {
        super(ResultCode.INTERNAL_SERVER_ERROR.getValue(), resultMessage);
    }

    public SystemException(String resultMessage, String detail)
    {
        super(ResultCode.INTERNAL_SERVER_ERROR.getValue(), resultMessage, detail);
    }
}
