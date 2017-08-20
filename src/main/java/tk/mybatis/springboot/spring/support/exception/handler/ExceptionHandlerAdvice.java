package tk.mybatis.springboot.spring.support.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tk.mybatis.springboot.base.domain.ErrorResponse;
import tk.mybatis.springboot.base.exception.BusinessException;

/**
 * @author hanguang
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(ExceptionHandlerAdvice.class);
    @ExceptionHandler({ BusinessException.class })
    public ResponseEntity<ErrorResponse> exception(BusinessException e) {
        ErrorResponse resp = new ErrorResponse(e.getResultCode(),
                e.getResultMessage());
        LOGGER.error(e.getResultMessage(), e);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @ExceptionHandler({ SystemException.class })
    public ResponseEntity<ErrorResponse> exception(SystemException e) {
        LOGGER.error(e.getResultMessage(), e);
        ErrorResponse resp = new ErrorResponse(e.getResultCode(),
                e.getResultMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        LOGGER.error(e.getMessage(), e);
        ErrorResponse resp = new ErrorResponse(
                ResultCode.INTERNAL_SERVER_ERROR.getValue(),
                e.getLocalizedMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<ErrorResponse> httpRequestMethodNotSupportedException(
            Exception e) {
        LOGGER.error(e.getMessage(), e);
        ErrorResponse resp = new ErrorResponse(
                ResultCode.METHOD_NOT_ALLOWED.getValue(),
                e.getLocalizedMessage());
        return new ResponseEntity<>(resp, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpMediaTypeNotSupportedException(
            Exception e) {
        LOGGER.error(e.getMessage(), e);
        ErrorResponse resp = new ErrorResponse(
                ResultCode.UNSUPPORTED_MEDIA_TYPE.getValue(),
                e.getLocalizedMessage());
        return new ResponseEntity<>(resp, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
