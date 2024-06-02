package com.project.demo;

import com.project.demo.entities.File;
import com.project.demo.repositories.FileRepository;
import com.project.demo.services.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FileServiceTests {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    @Test
    void testUploadFile() throws IOException {
        // Mock data
        String fileName = "test.txt";
        String contentType = "text/plain";
        byte[] content = "Hello, World!".getBytes();
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, contentType, content);

        // Mocking repository behavior
        when(fileRepository.save(Mockito.any(File.class))).thenReturn(new File());

        // Perform upload
        File uploadedFileName = fileService.uploadFile(multipartFile);

        // Assert
        assertThat(uploadedFileName).isNotNull();
        assertThat(uploadedFileName).isEqualTo(fileName);
    }

    @Test
    void testDownloadFile() {
        // Mock data
        String fileName = "test.txt";
        File file = new File();
        file.setName(fileName);
        Optional<File> optionalFile = Optional.of(file);

        // Mocking repository behavior
        when(fileRepository.findTopByNameAndOwnerOrderByVersionDesc(fileName)).thenReturn(optionalFile);

        // Perform download
        Optional<File> downloadedFile = fileService.downloadFile(fileName);

        // Assert
        assertThat(downloadedFile).isPresent();
        assertThat(downloadedFile.get().getName()).isEqualTo(fileName);
    }
}
