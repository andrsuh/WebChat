package ru.andrey.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.andrey.DAOs.DAOInterfaces.MessageDAO;
import ru.andrey.DAOs.DAOInterfaces.UserDAO;
import ru.andrey.Domain.User;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MessageDAO messageDAO; // change to message service

    @Autowired
    private UserDAO userDAO;

    @RequestMapping("/main")
    private String showMain(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<User> friends = userDAO.allColeagues(auth.getName());
        modelMap.put("friends", friends);

        return "main";
    }

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.allColeagues(auth.getName());
    }

    @RequestMapping(value = "/myName", method = RequestMethod.GET)
    public
    @ResponseBody
    String getMyName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
