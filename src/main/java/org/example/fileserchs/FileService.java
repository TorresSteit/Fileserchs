package org.example.fileserchs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void processDirectory(String directoryPath) {
        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .forEach(this::saveFileToDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFileToDatabase(java.nio.file.Path filePath) {
        try {
            String content = new String(Files.readAllBytes(filePath));
            String fileName = filePath.getFileName().toString();

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setContent(content);

            fileRepository.save(fileEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileEntity findFileByContent(String content) {
        return fileRepository.findByContent(content);
    }
}