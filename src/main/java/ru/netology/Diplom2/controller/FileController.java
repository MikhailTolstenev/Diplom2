package ru.netology.Diplom2.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.Diplom2.dto.FileDTO;
import ru.netology.Diplom2.entity.File;
import ru.netology.Diplom2.repository.FileRepository;
import ru.netology.Diplom2.service.FileService;
import ru.netology.Diplom2.entity.File;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.netology.Diplom2.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam("filename") String filename) {
        try {
            fileService.deleteFileByName(filename);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File deleted successfully: %s", filename));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not deleted the file: %s!", filename));
        }
    }
    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam("filename")  String filename) {
        Optional<File> fileOptional = fileService.findByName(filename);

        if (fileOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        File fileEntity = fileOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + fileEntity.getName() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }


    @PutMapping("/file")
    public ResponseEntity<String> updateFile(@RequestParam String filename, @RequestBody FileDTO name) {
        try {
            fileService.editFile(filename,name.getName());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File update successfully: %s", name.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not update the file: %s!", filename));
        }
    }
    @GetMapping("/list")
    public List<FileDTO> listFile(@RequestParam String limit) {
        return fileService.findAllByUserName((UserService.getCurrentUser().getLogin()))
                .stream()
                .map(this::mapToFileResponse)
                .limit(Long.parseLong(limit))
                .collect(Collectors.toList());
    }

    private FileDTO mapToFileResponse(File fileEntity) {
        FileDTO fileResponse = new FileDTO();
        fileResponse.setName(fileEntity.getName());
        return fileResponse;
    }



}
