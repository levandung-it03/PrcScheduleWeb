package com.SoftwareTech.PrcScheduleWeb.config;

import jakarta.servlet.http.HttpServletRequest;

public class TestingUrlsClass {
    public static void detectAllUrlWithRequest(HttpServletRequest request) {
        String url = request.getDispatcherType().toString();
        String url1 = request.getMethod();
        String url2 = request.getServletPath();
        String url3 = request.getContextPath();
        String url4 = request.getPathInfo();
        String url5 = request.getRequestURI();
        String url6 = request.getAuthType();
        String url7 = request.getPathTranslated();
    }
}
