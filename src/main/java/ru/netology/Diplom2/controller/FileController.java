package ru.netology.Diplom2.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.Diplom2.repository.FileRepository;
import ru.netology.Diplom2.service.FileService;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService fileService;
    private final FileRepository fileRepository;
    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String fileName,@RequestBody MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        long size = file.getSize();
        byte[] bytes = file.getBytes();
        FileService.uploadFile(authToken, fileName, contentType, bytes);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
