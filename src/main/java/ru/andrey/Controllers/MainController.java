package ru.andrey.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.andrey.DAOs.DAOImplementations.UserDAOImpl;
import ru.andrey.DAOs.DAOInterfaces.MessageDAO;
import ru.andrey.DAOs.DAOInterfaces.UserDAO;
import ru.andrey.Domain.Message;
import ru.andrey.Domain.User;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MessageDAO messageDAO; // change to message service

    @Autowired
    private UserDAO userDAO;

    @RequestMapping("/main")
    private String showMain(ModelMap modelMap, @RequestParam(value="otherUser") String otherUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<User> friends = userDAO.allFriends(auth.getName());
        modelMap.put("friends", friends);

        // getting all messages between two users and put it into the model
        List<Message> messageList = messageDAO.messagesByUser(auth.getName(), otherUser);
        modelMap.put("messageList", messageList);

        // put into the model current and other user
        modelMap.put("currentUser", userDAO.getUserByLogin(auth.getName()));
        modelMap.put("otherUser", userDAO.getUserByLogin(otherUser));

        return "main";
    }

}
