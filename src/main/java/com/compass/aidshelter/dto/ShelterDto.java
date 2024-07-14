package com.compass.aidshelter.dto;

import com.compass.aidshelter.entities.Shelter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ShelterDto (
        @NotBlank(message = "O nome não pode ficar em branco")
        @Size(min = 1, max = 100, message = "O nome precisa ter entre 1 e 100 caracteres")
        String name,
        @NotBlank(message = "O endereço não pode ficar em branco")
        @Size(min = 1, max = 255, message = "O endereço precisa ter entre 1 e 255 caracteres")
        String address,
        @NotBlank(message = "O nome do responsável não pode ficar em branco")
        @Size(min = 1, max = 100, message = "O nome do responsável precisa ter entre 1 e 100 caracteres")
        String responsible,
        @NotBlank(message = "Telefone não pode ficar em branco")
        @Pattern(regexp = "\\d{10,11}", message = "Telefone precisa ter 10 ou 11 dígitos")
        String phone,
        @Email(message = "E-mail precisa ser válido")
        @NotBlank(message = "E-mail não pode ficar em branco")
        String email,
        @NotBlank(message = "A capacidade de ocupação não pode ficar em branco")
        String occupationCapacity,
        @NotBlank(message = "A porcentagem de ocupação não pode ficar em branco")
        String occupationPercentage
){
    public Shelter toEntity(Long id) {
        return new Shelter(id, name, address, responsible, phone, email, occupationCapacity, occupationPercentage);
    }
}
