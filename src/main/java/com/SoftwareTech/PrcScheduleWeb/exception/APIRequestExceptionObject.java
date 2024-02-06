package com.SoftwareTech.PrcScheduleWeb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public record APIRequestExceptionObject(HttpStatus httpStatus, String message, ZonedDateTime time) {}
