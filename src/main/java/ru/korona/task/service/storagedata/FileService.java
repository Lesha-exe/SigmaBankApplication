package ru.korona.task.service.storagedata;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import ru.korona.task.objectparameters.DataType;

@AllArgsConstructor
public class FileService {
    private Map<DataType, FileStorage> storageInfo;

    public void storeData(List<Object> data) throws Exception {
        DataType dataType = DataType.from(data);
        FileStorage fileServiceInterface = storageInfo.get(dataType);
        if (fileServiceInterface == null) {
            throw new Exception("Null!");
        }
        fileServiceInterface.storeData(parseObjectToString(data));
    }

    private List<String> parseObjectToString(List<Object> data) {
        return data.stream().map(String::valueOf).collect(Collectors.toList());
    }
}
