package ru.netology.Diplom2.exeption;

public class SaveFileException extends RuntimeException{
    public SaveFileException(String cannotSaveFile) {
        super(cannotSaveFile);
    }
}
