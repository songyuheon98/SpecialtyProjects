package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
