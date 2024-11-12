package com.dreamwheels.dreamwheels.uploaded_files.adapters;

import com.dreamwheels.dreamwheels.configuration.adapters.EntityAdapter;
import com.dreamwheels.dreamwheels.uploaded_files.dto.UploadedFileDto;
import com.dreamwheels.dreamwheels.uploaded_files.entity.UploadedFile;
import org.springframework.stereotype.Component;

@Component
public class UploadedFileAdapter implements EntityAdapter<UploadedFile, UploadedFileDto> {
    @Override
    public UploadedFileDto toBusiness(UploadedFile uploadedFile) {
        return UploadedFileDto.builder()
                .url(uploadedFile.getUrl())
                .fileTags(uploadedFile.getFileTags())
                .build();
    }
}
