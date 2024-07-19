package com.example.p3portaillocataireback.mapper;

import com.example.p3portaillocataireback.dto.requests.MessageDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;
import com.example.p3portaillocataireback.dto.response.Status;
import com.example.p3portaillocataireback.entity.Message;
import com.example.p3portaillocataireback.entity.Rental;
import com.example.p3portaillocataireback.entity.User;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public Message toMessage(MessageDto messageDto) {
        var message = new Message();
        message.setMessage(messageDto.getMessage());
        var rental = new Rental();
        rental.setId(messageDto.getRental_id());
        message.setRental(rental);
        var user = new User();
        user.setId(messageDto.getUser_id());
        message.setUser(user);
        return message;
    }
    public MessageResponseDto toMessageResponseDto(Message message) {
        return new MessageResponseDto(
                Status.SUCCESS.getStatus()
        );
    }
}