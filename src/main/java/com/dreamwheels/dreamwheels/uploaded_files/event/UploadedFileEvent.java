package com.dreamwheels.dreamwheels.uploaded_files.event;

import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.uploaded_files.entity.UploadedFile;
import com.dreamwheels.dreamwheels.uploaded_files.enums.FileTags;
import com.dreamwheels.dreamwheels.users.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UploadedFileEvent extends ApplicationEvent {
    private Garage garage;
    private List<MultipartFile> files;
    private UploadedFileEventType uploadedFileEventType;
    private User user;
    private FileTags fileTags;
    private String applicationUrl;
    private List<UploadedFile> uploadedFiles;

    public UploadedFileEvent(
            Garage garage, List<MultipartFile> files, UploadedFileEventType uploadedFileEventType,
            User user, FileTags fileTags, String applicationUrl, List<UploadedFile> uploadedFiles
    ) {
        super(user);
        this.garage = garage;
        this.files = files;
        this.uploadedFileEventType = uploadedFileEventType;
        this.user = user;
        this.fileTags = fileTags;
        this.applicationUrl = applicationUrl;
        this.uploadedFiles = uploadedFiles;
    }
}
