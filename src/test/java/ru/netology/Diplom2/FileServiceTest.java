package ru.netology.Diplom2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.netology.Diplom2.entity.File;
import ru.netology.Diplom2.repository.FileRepository;
import ru.netology.Diplom2.service.FileService;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;


import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.client.ExpectedCount.times;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileService.class)
@WithMockUser(username = "user")
public class FileServiceTest {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Test
    void save() {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        try {
            FileService.uploadFile("12346",file.getName(),file.getContentType(),file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Mockito.verify(fileRepository,Mockito.times(1));
    }
    @Test
    void findByName() {
        fileService.findByName("file");

        Mockito.verify(fileRepository, Mockito.times(1));
    }

    @Test
    void deleteFileByName() {
        fileService.deleteFileByName(anyString());


        Mockito.verify(fileRepository, Mockito.times(1));
    }


}
