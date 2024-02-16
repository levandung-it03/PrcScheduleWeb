package com.SoftwareTech.PrcScheduleWeb.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record APIRequestExceptionObject(HttpStatus httpStatus, String message, ZonedDateTime time) {}
