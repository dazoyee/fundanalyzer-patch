package com.github.ioridazo.fundanalyzer.patch.run;

import java.util.Arrays;

public enum CommandOptions {
    DOCUMENT_PERIOD("1"),
    UPDATE_FINANCIAL_STATEMENT("2"),
    UPDATE_ANALYSIS_RESULT("3"),
    UPDATE_VALUATION("4"),
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

    @Override
    public String toString() {
        return "CommandOptions{" +
                "value='" + value + '\'' +
                '}';
    }
}
