package ru.korona.task.services;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.outputsettings.StatisticsType;

public class StatisticsDataStorage {
    public void storeStatisticsToConsole(
            List<DepartmentStatistics> departmentStatisticsList, AppArguments appArguments) {
        System.out.println("department, min, max, mid");
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
            bufferedWriter.write("department, min, max, mid");
            bufferedWriter.newLine();
            departmentStatisticsList.stream()
                    .forEach(
                            departmentStatistics -> {
                                writeDataToFile(
                                        createDepartmentStatisticsLine(departmentStatistics),
                                        bufferedWriter);
                            });
        } catch (Exception exception) {
            System.out.println(
                    "Cannot write statistics to file: "
                            + path.getFileName()
                            + ". Exception: "
                            + exception.getMessage());
        }
    }

    private static String createDepartmentStatisticsLine(
            DepartmentStatistics departmentStatistics) {
        return String.format(
                "%s, %2f, %2f, %2f",
                departmentStatistics.getDepartmentName(),
                departmentStatistics
                        .getStatisticsData()
                        .getOrDefault(StatisticsType.MIN_SALARY, 0.0),
                departmentStatistics
                        .getStatisticsData()
                        .getOrDefault(StatisticsType.MAX_SALARY, 0.0),
                departmentStatistics
                        .getStatisticsData()
                        .getOrDefault(StatisticsType.MID_SALARY, 0.0));
    }

    private static void writeDataToFile(String data, BufferedWriter writer) {
        try {
            writer.write(data);
            writer.newLine();
        } catch (Exception exception) {
            System.out.println("Error while writing file: " + exception.getMessage());
        }
    }
}
