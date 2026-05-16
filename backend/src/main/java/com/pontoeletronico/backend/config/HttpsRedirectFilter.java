package com.pontoeletronico.backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpsRedirectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!req.isSecure()) {
            String redirectUrl = "https://" + req.getServerName() + ":8443" + req.getRequestURI();
            res.sendRedirect(redirectUrl);
            return;
        }

        chain.doFilter(request, response);
    }
}