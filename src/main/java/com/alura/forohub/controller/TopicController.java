package com.alura.forohub.controller;

import com.alura.forohub.dto.TopicRequestDto;
import com.alura.forohub.dto.TopicResponseDto;
import com.alura.forohub.model.TopicStatus;
import com.alura.forohub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<Page<TopicResponseDto>> getAllTopics(
            @RequestParam(required = false) String course,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TopicResponseDto> topics = course != null ?
            topicService.getTopicsByCourse(course, pageable) :
            topicService.getAllTopics(pageable);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDto> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    @PostMapping
    public ResponseEntity<TopicResponseDto> createTopic(@Valid @RequestBody TopicRequestDto requestDto) {
        return ResponseEntity.ok(topicService.createTopic(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDto> updateTopic(
            @PathVariable Long id,
            @Valid @RequestBody TopicRequestDto requestDto) {
        return ResponseEntity.ok(topicService.updateTopic(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TopicResponseDto> updateTopicStatus(
            @PathVariable Long id,
            @RequestBody TopicStatus status) {
        return ResponseEntity.ok(topicService.updateTopicStatus(id, status));
    }
}
