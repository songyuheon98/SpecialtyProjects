package com.fanplayground.fanplayground.repository;


import com.fanplayground.fanplayground.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder,Long> {

    List<Folder> findByFolderNumber(Long folderNumber);
}
