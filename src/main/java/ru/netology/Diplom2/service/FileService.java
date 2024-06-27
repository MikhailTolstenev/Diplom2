package ru.netology.Diplom2.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.Diplom2.entity.File;
import ru.netology.Diplom2.entity.User;
import ru.netology.Diplom2.exeption.SaveFileException;
import ru.netology.Diplom2.repository.FileRepository;
import ru.netology.Diplom2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service


public class FileService {
    private static FileRepository fileRepository;
//    private static  UserRepository userRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
//    public void UserRepository (UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }


    public static void uploadFile(String authToken, String fileName, String contentType, byte[] bytes) {
        try {

            File fileEntity = new File();
            fileEntity.setName(fileName);
            fileEntity.setContentType(contentType);
            fileEntity.setData(bytes);
            fileEntity.setUserName((UserService.getCurrentUser().getLogin()));
            fileRepository.save(fileEntity);

        } catch (Exception e) {
            throw new SaveFileException("Cannot save file" + " " + e.getMessage());
        }
    }

    @Transactional
    public void deleteFileByName(String fileName) {
        fileRepository.deleteByName(fileName);
    }

    public Optional<File> findByName(String name) {
        Optional<File> getFile = fileRepository.findByName(name);
        if (getFile.isPresent()) {
            return getFile;
        }
        getFile.get().setData(getFile.get().getData());

        return getFile;
    }

    @Transactional
    public void editFile(String name, String newName) {
        Optional<File> getFile = fileRepository.findByName(name);
        if (getFile.isPresent()) {
            getFile.get().setName(newName);
            fileRepository.save(getFile.get());
        }
    }
    @Transactional
    public List<File> findAllByUserName(String userName) {
        return fileRepository.findAllByUserName(userName);
    }


}






