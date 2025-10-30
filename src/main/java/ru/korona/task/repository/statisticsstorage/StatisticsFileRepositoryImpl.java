package ru.korona.task.repository.statisticsstorage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.objectparameters.StatisticsType;
import ru.korona.task.repository.StatisticsRepository;
import ru.korona.task.service.FileService;

@Component
@Profile("file")
public class StatisticsFileRepositoryImpl implements StatisticsRepository {
    private static final String DEPARTMENT_HEADER_KEY = "department";
    private static List<String> statisticsHeaders;
    private final String outputFileName;
    private final String outputDirectory;
    private final FileService fileService;

    public StatisticsFileRepositoryImpl(
            @Value("${statistics.header}") List<String> statisticsHeaders,
            @Value("${statistics.outputFileName}") String outputFileName,
            @Value("${departments.outputDir}") String outputDirectory,
            FileService fileService) {
        this.statisticsHeaders = statisticsHeaders;
        this.outputFileName = outputFileName;
        this.outputDirectory = outputDirectory;
        this.fileService = fileService;
    }

    @Override
    public void storeStatistics(List<DepartmentStatistics> departmentStatisticsList) {
        List<String> statisticsData = convertToStatisticsLine(departmentStatisticsList);
        fileService.storeData(
                statisticsData, outputDirectory.toString(), outputFileName.toString());
    }

    private List<String> convertToStatisticsLine(
            List<DepartmentStatistics> departmentStatisticsList) {
        return Stream.concat(
                        Stream.of(headerToString()),
                        departmentStatisticsList.stream()
                                .map(StatisticsFileRepositoryImpl::createDepartmentStatisticsLine))
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
