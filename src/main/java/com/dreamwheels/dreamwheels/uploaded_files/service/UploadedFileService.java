package com.dreamwheels.dreamwheels.uploaded_files.service;

import com.dreamwheels.dreamwheels.uploaded_files.entity.UploadedFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UploadedFileService {
    private static final String UPLOAD_DIRECTORY = "src/main/resources/uploads";

    // upload files
    @Async
   public static String uploadFile(MultipartFile multipartFile, String applicationUrl) throws IOException {
        Path uploadDirectory = Paths.get(UPLOAD_DIRECTORY);
        // generate random uuid for file name
        String uuid = UUID.randomUUID().toString();
        // extract file extension
        String extension = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        // concat uuid and extension
        String fileName = uuid.concat(extension);
        InputStream inputStream = multipartFile.getInputStream();
        Path uploadPath = uploadDirectory.resolve(fileName);
        Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
        String uploadedFile = applicationUrl.concat("/api/v1/uploads/").concat(fileName);
        System.out.println(uploadedFile);
        return uploadedFile;
   }


   // delete files
    @Async
    public static void deleteUploadedFiles(String fileName){
        try {
            // extract file name by removing the application url part
            String[] fileParts = fileName.split("/");
            String extractedFileName = fileParts[fileParts.length - 1];
            System.out.println(extractedFileName);
            File fileToDelete = new File(UPLOAD_DIRECTORY.concat("/").concat(extractedFileName));
            if (fileToDelete.exists() && fileToDelete.isFile()){
                fileToDelete.delete();
            }
            System.out.println("file deleted " + extractedFileName);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }
        System.out.println("Done deleting " + fileName);
    }

}
