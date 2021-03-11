package com.travelBill.splitBill.web;

import com.travelBill.telegram.RollbarLogger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final RollbarLogger rollbarLogger;

    public CustomExceptionHandler(RollbarLogger rollbarLogger) {
        this.rollbarLogger = rollbarLogger;
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        logError(exception, (ServletWebRequest) request);
        ErrorResponse error = new ErrorResponse();
        error.message = "Internal Server error";
        return handleExceptionInternal(exception, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private void logError(Exception exception, ServletWebRequest request) {
        exception.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        HttpServletRequest r = request.getRequest();
        stringBuilder.append(String.join(" ", r.getMethod(), r.getRequestURI(), " user: "));
        if (r.getAttribute("userId") != null) {
            stringBuilder.append(" user id: ");
            stringBuilder.append(r.getAttribute("userId").toString());
        }

        try {
            if ("POST".equalsIgnoreCase(r.getMethod()) || "PUT".equalsIgnoreCase(r.getMethod())) {
                stringBuilder.append("\n");
                stringBuilder.append(r.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
            }
        } catch (Exception e) {
            rollbarLogger.error(e, stringBuilder.toString());
        }
        rollbarLogger.error(exception, stringBuilder.toString());
    }
}
