package com.SoftwareTech.PrcScheduleWeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class APIExceptionHandler {
    public ResponseEntity<Object> handleAPIException(HttpStatus httpStatus, RuntimeException e) {
        return ResponseEntity.status(httpStatus).body(new APIRequestExceptionObject(
                httpStatus,
                e.getMessage(),
                ZonedDateTime.now()
        ));
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> APIHandlerBadRequestException(BadRequestException e) {
        return handleAPIException(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> APIHandlerUnauthorizedException(UnauthorizedException e) {
        return handleAPIException(HttpStatus.UNAUTHORIZED, e);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> APIHandlerForbiddenException(ForbiddenException e) {
        return handleAPIException(HttpStatus.FORBIDDEN, e);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> APIHandlerNotFoundException(NotFoundException e) {
        return handleAPIException(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(value = {InternalServerErrorException.class})
    public ResponseEntity<Object> APIHandlerInternalServerErrorException(InternalServerErrorException e) {
        return handleAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
}
