package com.example.bootsecurity.repository;

import com.example.bootsecurity.model.Messages;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Messages, Integer> {
    List<Messages> findByTag(String tag);
}
