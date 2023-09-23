package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {

    Optional<BoardColumn> findByColumnName(String name);
    Optional<BoardColumn> findByColumnId(Long columnId);
}
