package ru.andrey.DAOs.DAOInterfaces;

import org.springframework.security.access.annotation.Secured;
import ru.andrey.Domain.Message;
import ru.andrey.Domain.User;

import java.util.List;

/**
 * Created by andrey on 30.05.16.
 */
public interface MessageDAO {
    void addMessage(String fromUserName, Integer toUserID, String content);

    List<Message> messagesByUser(String authenticatedUserName, Integer otherUserId);

    // removeMessage
}
