package ru.netology.Diplom2.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.netology.Diplom2.entity.File;
import ru.netology.Diplom2.entity.User;
import ru.netology.Diplom2.exeption.AppError;
import ru.netology.Diplom2.exeption.SaveFileException;
import ru.netology.Diplom2.repository.FileRepository;

import java.util.Objects;

@Service


public class FileService {
    private static FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public static void uploadFile(String authToken, String fileName, String contentType, byte[] bytes) {
        try {

            File fileEntity = new File();
            fileEntity.setName(fileName);
            fileEntity.setContentType(contentType);
            fileEntity.setData(bytes);
            fileEntity.setUser(UserService.getCurrentUser());
            fileRepository.save(fileEntity);

        } catch (Exception e) {
            throw new SaveFileException("Cannot save file" + " " + e.getMessage());
        }
    }
}

