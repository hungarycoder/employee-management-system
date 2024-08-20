package com.hungarycoder.ems.model.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ModifyEmployeeRequestDTO {

    @NotBlank(message = "A uuid megadása kötelező!")
    private String uuid;
    @NotBlank(message = "A név megadása kötelező!")
    private String name;
    @Email(message = "Nem megfelelő emailformátum!")
    @NotBlank(message = "Az email megadása kötelező!")
    private String email;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
