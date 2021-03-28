package com.github.ioridazo.fundanalyzer.patch.run;

import java.util.Arrays;

public enum CommandOptions {
    DOCUMENT_PERIOD("1"),
    DEFAULT("99"),
    ;

    private final String value;

    CommandOptions(final String value) {
        this.value = value;
    }

    public static CommandOptions fromValue(final String value) {
        return Arrays.stream(values())
                .filter(v -> v.value.equals(value))
                .findFirst()
                .orElse(CommandOptions.DEFAULT);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CommandOptions{" +
                "value='" + value + '\'' +
                '}';
    }
}
