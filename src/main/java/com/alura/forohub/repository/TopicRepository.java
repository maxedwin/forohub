package com.alura.forohub.repository;

import com.alura.forohub.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByCourseContainingIgnoreCase(String course, Pageable pageable);
}
