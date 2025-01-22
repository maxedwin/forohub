package com.alura.forohub.dto;

import com.alura.forohub.model.TopicStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TopicResponseDto {
    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TopicStatus status;
    private String course;
    private UserResponseDto author;
}
