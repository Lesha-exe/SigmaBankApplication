package org.example.outputSettings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WorkerType {
    MANAGER("Manager"),
    EMPLOYEE("Employee");
    private final String name;
    public static WorkerType from(String name){
        for(WorkerType type: values()){
            if(type.getName().equals(name)){
                return type;
            }
        }
        throw new IllegalArgumentException("ERROR! Incorrect sort type parameter: " + name);
    }
}
