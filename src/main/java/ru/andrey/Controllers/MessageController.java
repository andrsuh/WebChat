package ru.andrey.Controllers;

import ru.andrey.DAOs.MessageDAO;
import ru.andrey.Domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private MessageDAO dao;


    @RequestMapping("/messages")
    public String listMessages(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<Message> messageList = dao.messagesByUser(auth.getName());

        modelMap.put("messageList", messageList);
        return "hello";
    }

}
