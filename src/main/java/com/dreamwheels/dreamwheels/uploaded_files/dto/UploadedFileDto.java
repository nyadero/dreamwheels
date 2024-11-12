package com.dreamwheels.dreamwheels.uploaded_files.dto;

import com.dreamwheels.dreamwheels.uploaded_files.enums.FileTags;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadedFileDto {
    private String url;
    private FileTags fileTags;
}
