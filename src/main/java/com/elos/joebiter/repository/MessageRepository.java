package com.elos.joebiter.repository;

import com.elos.joebiter.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Page<Message> findAllByOrderByIdDesc(Pageable pageable);

    Page<Message> findByTextContainingIgnoreCaseOrderByIdDesc(String text, Pageable pageable);
}
