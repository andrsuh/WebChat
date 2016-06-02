package ru.andrey.Controllers;

import org.springframework.web.bind.annotation.PathVariable;
import ru.andrey.DAOs.DAOInterfaces.MessageDAO;
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
    private MessageDAO dao; // change to message service


    @RequestMapping(value = {"/", "/user"})
    public String listMessages(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<Message> messageList = dao.messagesByUser(auth.getName());

        modelMap.put("messageList", messageList);
        return "user";
    }

    @RequestMapping("/user/{userName}")
    public String allMessagebByUser(@PathVariable String userName, ModelMap modelMap) {
        List<Message> messageList = dao.messagesByUser(userName);

        modelMap.put("messageList", messageList);
        return "user";
    }
}
