package ru.korona.task.service.reader;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArgumentKey {
    public static final String SORT_ARGUMENT_PREFIX = "--sort";
    public static final String SORT_ARGUMENT_SHORT_PREFIX = "-s";
    public static final String ORDER_ARGUMENT_PREFIX = "--order";
    public static final String STATISTICS_FLAG = "--stat";
    public static final String OUTPUT_ARGUMENT_PREFIX = "--output";
    public static final String OUTPUT_ARGUMENT_SHORT_PREFIX = "-o";
    public static final String PATH_ARGUMENT_PREFIX = "--path";
}
