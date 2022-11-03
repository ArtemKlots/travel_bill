package com.travelBill.splitBill.web;

import com.travelBill.RollbarLogger;
import com.travelBill.TravelBillException;
import com.travelBill.splitBill.core.ClosedBillException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Scanner;

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

    @ExceptionHandler(TravelBillException.class)
    public final ResponseEntity<Object> handleTravelBillException(Exception exception, WebRequest request) {
        logError(exception, (ServletWebRequest) request);
        ErrorResponse error = new ErrorResponse();
        error.message = exception.getMessage();
        return handleExceptionInternal(exception, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ClosedBillException.class)
    public final ResponseEntity<Object> handleClosedBillException(Exception exception, WebRequest request) {
        logError(exception, (ServletWebRequest) request);
        ErrorResponse error = new ErrorResponse();
        error.message = "Could not perform operation for closed bill";
        return handleExceptionInternal(exception, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private void logError(Exception exception, ServletWebRequest request) {
        exception.printStackTrace();

        // TODO: This should be removed after caching fix is tested
        if (exception.getMessage().contains("500 Internal Server Error: [no body]")) {
            System.out.println("Ignoring error.");
            return;
        }

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
                // Next lines are identical, but getReader() can be called only once, and it is called somewhere under the hood,
                // so the following exceptions is thrown > getinputstream() has already been called for this request.
//                stringBuilder.append(r.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
                stringBuilder.append(new Scanner(r.getInputStream(), "UTF-8").useDelimiter("\\A"));
            }
        } catch (Exception e) {
            rollbarLogger.error(e, stringBuilder.toString());
        }
        rollbarLogger.error(exception, stringBuilder.toString());
    }
}
