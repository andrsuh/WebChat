package ru.andrey.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.andrey.DAOs.DAOInterfaces.OrganisationDAO;
import ru.andrey.DAOs.DAOInterfaces.UserDAO;
import ru.andrey.Domain.Organisation;
import ru.andrey.Domain.User;

@Controller
public class RegistrationController {
    @Autowired
    private OrganisationDAO organisationDAO;

    @Autowired
    private UserDAO userDAO;

//    @RequestMapping(value = "/org_choice", method = RequestMethod.GET)
//    public String getOrganisation(ModelMap model) {
//        Organisation organisation = new Organisation();
//        model.addAttribute("organisation", organisation);
//        model.addAttribute("orgList", organisationDAO.getAllOrganisation());
//
//        return "org_choice";
//    }

    @RequestMapping(value = "/registration")
    public String getRegistration(ModelMap model) {
        model.addAttribute("organisations", organisationDAO.getAllOrganisation());
        model.addAttribute("newUser", new User());

        return "registration";
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String getRegistration(@ModelAttribute Organisation organisation, ModelMap model) {
//        User newUser = new User();
//        newUser.setOrganisation(organisation.getName());
//
//        model.put("newUser", newUser);
//        model.put("organisation", organisation);
//        model.put("depList", organisationDAO.getAllDepartments(organisation.getName()));
//        model.put("posList", organisationDAO.getAllPositions(organisation.getName()));
//
//        return "registration";
//    }

//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public String addNewUser(@ModelAttribute("newUser") User user) {
//        System.out.println(user.getOrganisation());
//
//        userDAO.addUser(user);
//        return "user";
//    }
}
