package net.lilifei.app.password.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class UncheckedObjectMapper {

    private final ObjectMapper objectMapper;

    public String writeValueAsString(final Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (final IOException e) {
            log.error("Error while writeValueAsString from {}", value, e);
            throw new RuntimeException(e);
        }
    }

    public <T> T readValue(final String content, final Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (final IOException e) {
            log.error("Error while readValue from {}", content, e);
            throw new RuntimeException(e);
        }
    }
}
