package com.speaker.service.util;

import java.util.Optional;

import static java.util.Objects.nonNull;

public class StringParser {
    private static final Integer LIMIT_VALUE = 2;

    public static Optional<Pair<String, String>> parseOptionalStringBySpace(String names) {
        if (nonNull(names)) {
            return parser(names);
        }
        return Optional.empty();
    }

    public static Optional<Pair<String, String>> parseOptionalStringBySpace(Optional<String> names) {
        if (nonNull(names) && names.isPresent()) {
            return parser(names.get());
        }
        return Optional.empty();
    }

    private static Optional<Pair<String, String>> parser(String names) {
        String[] splitNames = names.trim().split("\\s+");
        if (splitNames.length == LIMIT_VALUE) {
            return Optional.of(new Pair<String, String>(splitNames[0], splitNames[1]));
        }
        return Optional.empty();
    }
}
