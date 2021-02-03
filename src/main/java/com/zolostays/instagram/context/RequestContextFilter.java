package com.zolostays.instagram.context;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

public class RequestContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestContext.intialize();
        try {
            chain.doFilter(request, response);
        }finally {
            RequestContext.cleanup();
        }
    }

}
