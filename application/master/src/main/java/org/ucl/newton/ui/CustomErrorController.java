package org.ucl.newton.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

/**
 *  Instances of this class provide an MVC controller for web pages used to
 *  display that an error has occurred.
 *
 * @author John Wilkie
 */

@ControllerAdvice
@EnableWebMvc
public class CustomErrorController {

    private static Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String basicError(HttpServletRequest request, Exception e) {
        logger.error("Request: " + request.getRequestURL() + " raised " + e);
        return "errors/error";
    }

}

