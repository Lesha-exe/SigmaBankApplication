package org.example.models;

import lombok.*;
import org.example.outputSettings.OrderType;
import org.example.outputSettings.SortType;

@Data
@Builder
public class AppArguments {
    private SortType sortType;]
    private OrderType order;
    private StatisticsConfig statisticsConfig;
}
