package com.epam.hostel.controller.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Listener on a session creation.
 * On session creation will be set a default language.
 */
public class SessionListener implements HttpSessionListener {
    private static final String SESSION_LANGUAGE_ID = "languageId";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute(SESSION_LANGUAGE_ID, DEFAULT_LANGUAGE_ID);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
