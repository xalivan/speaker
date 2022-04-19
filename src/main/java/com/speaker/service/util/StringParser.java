package com.speaker.service.util;

import java.util.Optional;

import static java.util.Objects.nonNull;

public class StringParser {
    private static final Integer LIMIT_VALUE = 2;

    public static Optional<Pair<String, String>> splitBySpace(String names) {
        if (nonNull(names)) {
            String[] splitNames = names.trim().split("\\s+");
            if (splitNames.length == LIMIT_VALUE) {
                return Optional.of(new Pair<String, String>(splitNames[0], splitNames[1]));
            }
        }
        return Optional.empty();
    }
}
