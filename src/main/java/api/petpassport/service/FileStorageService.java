package api.petpassport.service;

import api.petpassport.exception.ConflictException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    private static final String ROOT = "user-files";

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(ROOT));
        } catch (IOException e) {
            throw new ConflictException("Could not initialize folder for upload!");
        }
    }

    public Path createFolder(String folder) {
        try {
            return Files.createDirectories(Paths.get(ROOT, folder));
        } catch (IOException e) {
            throw new ConflictException("Could not initialize folder for upload!");
        }
    }

    public Path save(UUID userId, MultipartFile file) {
        if (file == null) throw new ConflictException("File is null!");

        Path folder = createFolder(userId.toString());
        try {
            Path filePath = folder.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);
            return filePath;
        } catch (FileAlreadyExistsException e) {
            throw new ConflictException("A file of that name already exists.");
        } catch (Exception e) {
            throw new ConflictException(e.getMessage());
        }
    }

}
