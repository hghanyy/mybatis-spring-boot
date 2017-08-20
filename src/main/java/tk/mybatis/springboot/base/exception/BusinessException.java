package tk.mybatis.springboot.base.exception;

public class BusinessException extends AbstractException {
    private static final long serialVersionUID = 1L;

    public BusinessException() {}

    public BusinessException(String resultCode, String resultMessage)
    {
        super(resultCode, resultMessage);
    }
}
