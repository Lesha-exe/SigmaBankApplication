package org.example.models;

import lombok.*;
import org.example.outputSettings.OrderType;
import org.example.outputSettings.SortType;



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
