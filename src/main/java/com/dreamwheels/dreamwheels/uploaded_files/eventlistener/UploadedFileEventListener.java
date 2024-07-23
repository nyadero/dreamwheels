package com.dreamwheels.dreamwheels.uploaded_files.eventlistener;

import com.dreamwheels.dreamwheels.uploaded_files.entity.UploadedFile;
import com.dreamwheels.dreamwheels.uploaded_files.enums.FileTags;
import com.dreamwheels.dreamwheels.uploaded_files.event.UploadedFileEvent;
import com.dreamwheels.dreamwheels.uploaded_files.event.UploadedFileEventType;
import com.dreamwheels.dreamwheels.uploaded_files.repository.UploadedFileRepository;
import com.dreamwheels.dreamwheels.uploaded_files.service.UploadedFileService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UploadedFileEventListener implements ApplicationListener<UploadedFileEvent> {
    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    private final List<UploadedFile> uploadedFiles = new ArrayList<>();

    @Override
    public void onApplicationEvent(@NotNull UploadedFileEvent event) {
//        listen for upload event
       if (event.getUploadedFileEventType().equals(UploadedFileEventType.Upload) && !event.getFiles().isEmpty() ){
           for (MultipartFile multipartFile: event.getFiles()){
               try {
                   String fileUrl = UploadedFileService.uploadFile(multipartFile, event.getApplicationUrl());
                   var uploadedFile = UploadedFile.builder()
                           .fileName(multipartFile.getName())
                           .fileSize(multipartFile.getSize())
                           .garage(event.getGarage())
                           .user(event.getUser())
                           .url(fileUrl)
                           .fileTags(FileTags.valueOf(String.valueOf(event.getFileTags())))
                           .fileType(multipartFile.getContentType()).build();
                   uploadedFileRepository.save(uploadedFile);
                   uploadedFiles.add(uploadedFile);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
       }

       // listen for delete event
        if (event.getUploadedFileEventType().equals(UploadedFileEventType.Delete) && !event.getUploadedFiles().isEmpty() ){
            for (UploadedFile uploadedFile: event.getUploadedFiles()){
                 uploadedFileRepository.deleteById(uploadedFile.getId());
                 // delete file from storage
                log.info("File deleted successfully " + uploadedFile.getUrl());
                 UploadedFileService.deleteUploadedFiles(uploadedFile.getUrl());
            }
        }


    }

    public List<UploadedFile> getUploadedFiles() {
        return new ArrayList<>(uploadedFiles);
    }
}
