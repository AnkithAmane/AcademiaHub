package com.example.academiahub.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.model.QueueEntry;
import com.example.academiahub.repository.QueueRepository;

@Service
public class QueueService {
    private final QueueRepository queueRepository;

    @Autowired
    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    public QueueEntry addToQueue(CourseRequest request) {
        QueueEntry entry = new QueueEntry(LocalDateTime.now(), request);
        return queueRepository.save(entry);
    }

    public Optional<QueueEntry> getNextInQueue() {
        return queueRepository.findFirstByOrderByRequestDateAsc();
    }

    public void removeFromQueue(QueueEntry entry) {
        queueRepository.delete(entry);
    }
}
