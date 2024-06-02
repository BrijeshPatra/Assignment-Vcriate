package com.project.demo.services;

import com.project.demo.entities.File;
import com.project.demo.entities.User;
import com.project.demo.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private FileRepository fileRepository;

    public FileService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!");
        }
    }

    public File uploadFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
        Files.copy(file.getInputStream(), destinationFile);

        File uploadedFile = new File();
        uploadedFile.setName(filename);
        uploadedFile.setPath(destinationFile.toString());
        uploadedFile.setVersion(1);

        return fileRepository.save(uploadedFile);
    }

    public List<File> getFilesByOwnerId(Long ownerId) {
        return fileRepository.findByOwnerId(ownerId);
    }

    public Optional<File> getFileById(Long id) {
        return fileRepository.findById(id);
    }

    public Optional<File> downloadFile(String fileName) {
        return fileRepository.findTopByName(fileName);
    }
}
