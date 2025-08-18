package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.outputSettings.OrderType;
import org.example.outputSettings.SortType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppArguments {
    private SortType SortType;
    private OrderType order;
    private StatisticsConfig statisticsConfig;
}
