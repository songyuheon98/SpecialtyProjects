package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
