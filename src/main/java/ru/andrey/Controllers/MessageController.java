package ru.andrey.Controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import ru.andrey.DAOs.DAOInterfaces.MessageDAO;
import ru.andrey.DAOs.DAOInterfaces.UserDAO;
import ru.andrey.Domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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
//    @Autowired
//    SimpMessagingTemplate template;

//    @RequestMapping("/user/{userName}")
//    public String allMessagebByUser(@PathVariable String userName, ModelMap modelMap) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        List<Message> messageList = dao.messagesByUser(auth.getName(), userName);
//        modelMap.put("messageList", messageList);
//
//        return "user";
//    }

//    @RequestMapping("/user/{userName}")
//    public void allMessagebByUser(@PathVariable String userName) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        List<Message> messageList = dao.messagesByUser(auth.getName(), userName);
//
//        template.convertAndSend("/online", messageList.get(0));
//    }

//    @MessageMapping("/messages")
//    @SendTo("/userMessages/name")
//    public List<Message> allMessagebByUser(User user, Principal principal) throws InterruptedException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        List<Message> messageList = dao.messagesByUser(principal.getName(), user.getId());
//        return messageList;
//    }

    @MessageMapping("messages/newMessage/{userToID}")
    public void newMessage(@DestinationVariable Integer userToID, @Payload String content, Principal principal) {
        Message message = dao.addMessage(principal.getName(), userToID, content);
        User user = userDAO.getUserByLogin(principal.getName());
        User otherUser = userDAO.getUserByID(userToID);
        socket.convertAndSend("/userMessages/newMessage/" +  otherUser.getUsername() + "/" + user.getId(), message);

    }


    @MessageMapping("/messages/{userID}")
    public void allMessagesByUser(@DestinationVariable Integer userID, Principal principal) {
        List<Message> messageList = dao.messagesByUser(principal.getName(), userID);
        socket.convertAndSend("/userMessages/" +  principal.getName() + "/" + userID, messageList);
    }
}
