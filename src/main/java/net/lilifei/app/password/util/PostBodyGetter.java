package net.lilifei.app.password.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public class PostBodyGetter {

    public String getPostBody(final HttpServletRequest request) {
        try {
            return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
