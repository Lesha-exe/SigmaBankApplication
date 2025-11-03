package ru.korona.task.service.statistics;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.objectparameters.StatisticsType;
import ru.korona.task.repository.StatisticsRepository;
import ru.korona.task.service.FileService;

@Component
@Slf4j
public class StatisticsDataStorage {
    private static final String DEPARTMENT_HEADER_KEY = "department";
    private static List<String> statisticsHeaders;
    private final StatisticsRepository statisticsRepository;
    private final FileService fileService;

    public StatisticsDataStorage(
            @Value("${statistics.header}") List<String> statisticsHeaders,
            StatisticsRepository statisticsRepository,
            FileService fileService) {
        this.statisticsHeaders = statisticsHeaders;
        this.statisticsRepository = statisticsRepository;
        this.fileService = fileService;
    }

//    public void storeStatistics(
//            List<DepartmentStatistics> departmentStatisticsList, AppArguments appArguments) {
//        if (appArguments.getStatisticsConfig().getOutputFilePath() != null) {
//            storeStatisticsToFile(departmentStatisticsList, appArguments);
//        } else {
//            storeStatisticsToConsole(departmentStatisticsList);
//        }
//    }
//
//    private void storeStatisticsToConsole(List<DepartmentStatistics> departmentStatisticsList) {
//        List<String> statisticsData = convertToStatisticsLine(departmentStatisticsList);
//        statisticsData.forEach(System.out::println);
//    }

    public void storeStatisticsToFile(
            List<DepartmentStatistics> departmentStatisticsList, AppArguments appArguments) {
        statisticsRepository.storeStatistics(departmentStatisticsList);
//        Path path = Path.of(appArguments.getStatisticsConfig().getOutputFilePath());
//        Path outputDirectory = path.getParent();
//        Path fileName = path.getFileName();
//        List<String> statisticsData = convertToStatisticsLine(departmentStatisticsList);
//        fileService.storeData(statisticsData, outputDirectory.toString(), fileName.toString());
    }

    private List<String> convertToStatisticsLine(
            List<DepartmentStatistics> departmentStatisticsList) {
        return Stream.concat(
                        Stream.of(headerToString()),
                        departmentStatisticsList.stream()
                                .map(StatisticsDataStorage::createDepartmentStatisticsLine))
                .toList();
    }

    private static String createDepartmentStatisticsLine(
            DepartmentStatistics departmentStatistics) {
        return statisticsHeaders.stream()
                .map(header -> getStatisticsColumnValue(departmentStatistics, header))
                .collect(Collectors.joining(", "));
    }

    private static String getStatisticsColumnValue(
            DepartmentStatistics departmentStatistics, String header) {
        if (DEPARTMENT_HEADER_KEY.equals(header)) {
            return departmentStatistics.getDepartmentName();
        } else {
            final Double statValue =
                    departmentStatistics.getStatisticsData().get(StatisticsType.from(header));
            return statValue.toString();
        }
    }

    private String headerToString() {
        return String.join(", ", statisticsHeaders);
    }
}
