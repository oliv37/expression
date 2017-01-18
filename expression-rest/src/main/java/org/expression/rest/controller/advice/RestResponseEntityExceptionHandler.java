package org.expression.rest.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorInfo> handleInternalError(Exception ex, WebRequest request) {
    logger.error(ex.getMessage(), ex);
    return new ResponseEntity<ErrorInfo>(new ErrorInfo(ex), new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({NoHandlerFoundException.class})
  public ResponseEntity<ErrorInfo> handlePageNotFound(Exception ex, WebRequest request) {
    // TODO : Log page not found with path
    return new ResponseEntity<ErrorInfo>(new ErrorInfo("Page not found"), new HttpHeaders(),
        HttpStatus.NOT_FOUND);
  }

  class ErrorInfo {

    private String message;
    private String type;

    public ErrorInfo(Exception e) {
      this.message = "Internal Error";
      this.type = e.getClass().getSimpleName();
    }

    public ErrorInfo(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public String getType() {
      return type;
    }

  }

}
