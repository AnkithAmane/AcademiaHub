package com.example.academiahub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.academiahub.model.QueueEntry;

public interface QueueRepository extends JpaRepository<QueueEntry, Long> {
    Optional<QueueEntry> findFirstByOrderByRequestDateAsc();
}
