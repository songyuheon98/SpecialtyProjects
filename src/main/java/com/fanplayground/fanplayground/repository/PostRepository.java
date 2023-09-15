package com.fanplayground.fanplayground.repository;


import com.fanplayground.fanplayground.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAt();


    List<Post> findByFolderNumber(Long id);
}
