package ru.andrey.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.andrey.DAOs.DAOInterfaces.OrganisationDAO;
import ru.andrey.DAOs.DAOInterfaces.UserDAO;
import ru.andrey.Domain.Department;
import ru.andrey.Domain.Position;
import ru.andrey.Domain.User;

import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private OrganisationDAO organisationDAO;

    @Autowired
    private UserDAO userDAO;

    @RequestMapping("/registration")
    public String getRegistration(ModelMap model) {
        model.addAttribute("organisations", organisationDAO.getAllOrganisation());
        model.addAttribute("newUser", new User());

        return "registration";
    }

    @RequestMapping(value = "/positions/{org}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Position> getPositionsByOrganisation(@PathVariable("org") String organisation) {
        return organisationDAO.getAllPositions(organisation);
    }

    @RequestMapping(value = "/departments/{org}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Department> getDepartmentsByOrganisation(@PathVariable("org") String organisation) {
        return organisationDAO.getAllDepartments(organisation);
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String addNewUser(@RequestBody User user) {
        System.out.println(user.getUsername());
        userDAO.addUser(user);
        return "success";
    }
}
