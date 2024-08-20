package com.hungarycoder.ems.model.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateEmployeeRequestDTO {

    @NotBlank(message = "A név megadása kötelező!")
    private String name;
    @Email(message = "Nem megfelelő emailformátum!")
    @NotBlank(message = "Az email megadása kötelező!")
    private String email;

    public CreateEmployeeRequestDTO(String name, String email) {
        this.name = name;
        this.email = email;
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
