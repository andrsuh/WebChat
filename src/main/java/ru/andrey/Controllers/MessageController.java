package ru.andrey.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.andrey.DAOs.DAOInterfaces.MessageDAO;
import ru.andrey.DAOs.DAOInterfaces.UserDAO;
import ru.andrey.Domain.Message;
import ru.andrey.Domain.User;

import java.security.Principal;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private MessageDAO dao;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SimpMessagingTemplate socket;

    @MessageMapping("messages/newMessage/{userToID}")
    public void newMessage(@DestinationVariable Integer userToID, @Payload String content, Principal principal) {
        Message message = dao.addMessage(principal.getName(), userToID, content);
        User user = userDAO.getUserByLogin(principal.getName());
        User otherUser = userDAO.getUserByID(userToID);
        socket.convertAndSend("/userMessages/newMessage/" + otherUser.getUsername() + "/" + user.getId(), message);
    }


    @MessageMapping("/messages/{userID}")
    public void allMessagesByUser(@DestinationVariable Integer userID, Principal principal) {
        List<Message> messageList = dao.messagesByUser(principal.getName(), userID);
        socket.convertAndSend("/userMessages/" + principal.getName() + "/" + userID, messageList);
    }
}
