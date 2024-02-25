package org.example.fileserchs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    private final FileEntity fileService;

    @Autowired
    public FileController(FileEntity fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/process/{directoryPath}")
    public String processDirectory(@PathVariable Long directoryPath) {
        fileService.setId(directoryPath);
        return "Directory processed successfully.";
    }

    @GetMapping("/search/{content}")
    public String searchFileByContent(@PathVariable String content) {
        FileEntity fileEntity = fileService.setContent(content);
        if (fileEntity != null) {
            return "Found file: " + fileEntity.getFileName();
        } else {
            return "File not found.";
        }
    }
}
