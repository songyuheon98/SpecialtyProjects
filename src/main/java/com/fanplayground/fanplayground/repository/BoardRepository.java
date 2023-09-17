package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.Board;

import com.fanplayground.fanplayground.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {
}
