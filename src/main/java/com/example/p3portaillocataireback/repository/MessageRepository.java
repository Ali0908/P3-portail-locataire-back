package com.example.p3portaillocataireback.repository;

import com.example.p3portaillocataireback.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository  extends JpaRepository<Message, Integer> {
}
