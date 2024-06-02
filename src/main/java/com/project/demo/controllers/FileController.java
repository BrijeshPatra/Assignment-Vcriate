package com.project.demo.controllers;

import com.project.demo.entities.File;
import com.project.demo.entities.User;
import com.project.demo.services.FileService;
import com.project.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private UserService userServices;
    @Autowired
    private FileService fileServices;
    @PostMapping("/upload")
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        User owner= userServices.findByUsername(principal.getName()).orElseThrow();
        return ResponseEntity.ok(fileServices.uploadFile(file));
    }
    @GetMapping
    public ResponseEntity<List<File>> getFilesByOwnerId(Principal principal){
        User owner = userServices.findByUsername(principal.getName()).orElseThrow();
        return ResponseEntity.ok(fileServices.getFilesByOwnerId(owner.getId()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<File> getFileById(@PathVariable Long id) {
        return fileServices.getFileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/versions")
    public ResponseEntity<List<File>> getFileVersions(@PathVariable Long id, Principal principal) {
        User owner = userServices.findByUsername(principal.getName()).orElseThrow();
        Optional<File> file = fileServices.getFileById(id);
        if (file.isPresent() && file.get().getOwner().equals(owner)) {
            List<File> versions = fileServices.getFilesByOwnerId(owner.getId());
            return ResponseEntity.ok(versions);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
