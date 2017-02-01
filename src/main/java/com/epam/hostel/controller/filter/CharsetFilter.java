package com.epam.hostel.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Sets character encoding for every request.
 */
public class CharsetFilter implements Filter {

    private String defaultEncoding = "utf-8";
    private static final String ENCODING = "encoding";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encoding = filterConfig.getInitParameter(ENCODING);
        if(encoding != null){
            defaultEncoding = encoding;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(defaultEncoding);
        servletResponse.setCharacterEncoding(defaultEncoding);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
