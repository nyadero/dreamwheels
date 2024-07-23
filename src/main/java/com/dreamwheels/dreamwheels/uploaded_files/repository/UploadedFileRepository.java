package com.dreamwheels.dreamwheels.uploaded_files.repository;

import com.dreamwheels.dreamwheels.uploaded_files.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, String> {
}
