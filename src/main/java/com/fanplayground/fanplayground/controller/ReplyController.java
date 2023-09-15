package com.fanplayground.fanplayground.controller;


import com.fanplayground.fanplayground.dto.ReplyRequestDto;
import com.fanplayground.fanplayground.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/reply")
    public ResponseEntity<?> createReply(@RequestBody ReplyRequestDto requestDto) {
        return replyService.createReply(requestDto);
    }

    @PutMapping("/reply/{id}")
    public ResponseEntity<?> updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto requestDto){
        return replyService.updateReply(id, requestDto);
    }

    @DeleteMapping("/reply/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable Long id) {
        return replyService.deleteReply(id);
    }
}
