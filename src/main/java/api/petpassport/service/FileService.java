package api.petpassport.service;

import api.petpassport.domain.FileEntity;
import api.petpassport.domain.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public FileEntity save(UUID userId, MultipartFile multipartFile) {
        Path filePath = fileStorageService.save(userId, multipartFile);

        FileEntity file = new FileEntity();
        file.setUserId(userId);
        file.setUri(filePath.toString());
        file.setCreatedAt(Instant.now());
        file.setUpdateAt(Instant.now());
        fileRepository.save(file);
        return file;
    }
}
