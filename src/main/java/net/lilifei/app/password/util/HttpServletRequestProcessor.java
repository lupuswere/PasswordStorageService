package net.lilifei.app.password.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

import static net.lilifei.app.password.util.HttpSessionConstants.USER_ID;

public class HttpServletRequestProcessor {

    public String getPostBody(final HttpServletRequest request) {
        try {
            return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserId(final HttpServletRequest request) {
        final Object userIdObj = request.getSession().getAttribute(USER_ID);
        if(userIdObj == null) return "";
        return (String) userIdObj;
    }
}
