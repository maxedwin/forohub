package com.alura.forohub.service;

import com.alura.forohub.dto.TopicRequestDto;
import com.alura.forohub.dto.TopicResponseDto;
import com.alura.forohub.dto.UserResponseDto;
import com.alura.forohub.model.Topic;
import com.alura.forohub.model.TopicStatus;
import com.alura.forohub.model.User;
import com.alura.forohub.repository.TopicRepository;
import com.alura.forohub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<TopicResponseDto> getAllTopics(Pageable pageable) {
        return topicRepository.findAll(pageable).map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public Page<TopicResponseDto> getTopicsByCourse(String course, Pageable pageable) {
        return topicRepository.findByCourseContainingIgnoreCase(course, pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public TopicResponseDto getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        return convertToDto(topic);
    }

    @Transactional
    public TopicResponseDto createTopic(TopicRequestDto requestDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Topic topic = new Topic();
        topic.setTitle(requestDto.getTitle());
        topic.setMessage(requestDto.getMessage());
        topic.setCourse(requestDto.getCourse());
        topic.setAuthor(author);
        topic.setStatus(TopicStatus.NOT_ANSWERED);

        Topic savedTopic = topicRepository.save(topic);
        return convertToDto(savedTopic);
    }

    @Transactional
    public TopicResponseDto updateTopic(Long id, TopicRequestDto requestDto) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        topic.setTitle(requestDto.getTitle());
        topic.setMessage(requestDto.getMessage());
        topic.setCourse(requestDto.getCourse());

        Topic updatedTopic = topicRepository.save(topic);
        return convertToDto(updatedTopic);
    }

    @Transactional
    public void deleteTopic(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico no encontrado");
        }
        topicRepository.deleteById(id);
    }

    @Transactional
    public TopicResponseDto updateTopicStatus(Long id, TopicStatus status) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        topic.setStatus(status);
        Topic updatedTopic = topicRepository.save(topic);
        return convertToDto(updatedTopic);
    }

    private TopicResponseDto convertToDto(Topic topic) {
        TopicResponseDto dto = new TopicResponseDto();
        dto.setId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setMessage(topic.getMessage());
        dto.setCreatedAt(topic.getCreatedAt());
        dto.setUpdatedAt(topic.getUpdatedAt());
        dto.setStatus(topic.getStatus());
        dto.setCourse(topic.getCourse());

        UserResponseDto authorDto = new UserResponseDto();
        authorDto.setId(topic.getAuthor().getId());
        authorDto.setFullName(topic.getAuthor().getFullName());
        authorDto.setEmail(topic.getAuthor().getEmail());
        dto.setAuthor(authorDto);

        return dto;
    }
}
