package ru.andrey.DAOs.DAOInterfaces;

import ru.andrey.Domain.Message;

import java.util.List;


public interface MessageDAO {
    Message addMessage(String fromUserName, Integer toUserID, String content);

    List<Message> messagesByUser(String authenticatedUserName, Integer otherUserId);
}
