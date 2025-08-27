package ru.korona.task.models;

import lombok.*;
import ru.korona.task.outputSettings.OrderType;
import ru.korona.task.outputSettings.SortType;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Builder
public class AppArguments {
    private final SortType sortType;
    private final OrderType order;
    private final StatisticsConfig statisticsConfig;
}
