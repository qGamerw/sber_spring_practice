package ru.sber.entities.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Класс для входа пользователей
 */
@Data
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
