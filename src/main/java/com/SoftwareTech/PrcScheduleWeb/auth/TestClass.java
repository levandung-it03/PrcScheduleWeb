package com.SoftwareTech.PrcScheduleWeb.auth;

import jakarta.servlet.http.HttpServletRequest;

public class TestClass {
    public static void detectAllUrlWithRequest(HttpServletRequest request) {
        String url = request.getDispatcherType().toString();
        url = request.getMethod();
        url = request.getServletPath();
        url = request.getContextPath();
        url = request.getPathInfo();
        url = request.getRequestURI();
        url = request.getAuthType();
        url = request.getPathTranslated();
    }
}
