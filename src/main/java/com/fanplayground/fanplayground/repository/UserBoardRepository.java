package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
    List<UserBoard> findAllByInviteUserId(Long inviteUserId);
}
