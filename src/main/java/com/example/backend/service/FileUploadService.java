package com.example.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    public String uploadImage(String path, MultipartFile file) throws IOException {

        //File name
        String name = file.getOriginalFilename();
        //Change file name with random id
        String uuid = UUID.randomUUID().toString();
        String newFilename = uuid.concat("_" + name);
        //Fullpath
        String filepath = path + File.separator + newFilename;

        //Create folder if not  created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        //File copy
        Files.copy(file.getInputStream(), Paths.get(filepath));
        return newFilename;
    }
}
