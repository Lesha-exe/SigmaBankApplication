package ru.korona.task.service.statistics;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.objectparameters.StatisticsType;

@Component
@Slf4j
public class StatisticsDataStorage {
    private static final String DEPARTMENT_HEADER_KEY = "department";
    private static List<String> statisticsHeaders;

    public StatisticsDataStorage(@Value("${statistics.header}") List<String> statisticsHeaders) {
        this.statisticsHeaders = statisticsHeaders;
    }

    public void storeStatisticsToConsole(List<DepartmentStatistics> departmentStatisticsList) {
        System.out.println(headerToString());
        departmentStatisticsList.stream()
                .forEach(
                        departmentStatistics ->
                                System.out.println(
                                        createDepartmentStatisticsLine(departmentStatistics)));
    }

    public void storeStatisticsToFile(
            List<DepartmentStatistics> departmentStatisticsList, AppArguments appArguments) {
        Path path = Path.of(appArguments.getStatisticsConfig().getOutputFilePath());
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            Files.createDirectories(path.getParent());
            bufferedWriter.write(headerToString());
            bufferedWriter.newLine();
            departmentStatisticsList.stream()
                    .forEach(
                            departmentStatistics ->
                                    writeDataToFile(
                                            createDepartmentStatisticsLine(departmentStatistics),
                                            bufferedWriter));
        } catch (Exception exception) {
            log.info(
                    "Cannot write statistics to file: "
                            + path.getFileName()
                            + ". Exception: "
                            + exception.getMessage());
        }
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

    private static void writeDataToFile(String data, BufferedWriter writer) {
        try {
            writer.write(data);
            writer.newLine();
        } catch (Exception exception) {
            log.info("Error while writing file: " + exception.getMessage());
        }
    }

    private String headerToString() {
        return String.join(", ", statisticsHeaders);
    }
}
