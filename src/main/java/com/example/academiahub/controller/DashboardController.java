package com.example.academiahub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.academiahub.model.QueueEntry;
import com.example.academiahub.repository.QueueRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final QueueRepository queueRepository;

    @Autowired
    public DashboardController(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    @GetMapping("/queue")
    public List<QueueEntry> getQueue() {
        return queueRepository.findAll();
    }
}
