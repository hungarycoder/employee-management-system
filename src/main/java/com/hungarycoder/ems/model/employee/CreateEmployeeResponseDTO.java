package com.hungarycoder.ems.model.employee;

public class CreateEmployeeResponseDTO {

    private String uuid;

    public CreateEmployeeResponseDTO(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
