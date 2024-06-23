package ru.netology.Diplom2.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppError {
    private int status;
    private String message;

}
