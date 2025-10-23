package ru.korona.task.models;

import lombok.*;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.SortType;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class AppArguments {
    private SortType sortType;
    private OrderType order;
    private StatisticsConfig statisticsConfig;
}
