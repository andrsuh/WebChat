package ru.andrey.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.andrey.DAOs.DAOImplementations.OrganisationDAOImpl;
import ru.andrey.Domain.Organisation;
import ru.andrey.Domain.User;

@Controller
public class RegistrationController {
    @Autowired
    private OrganisationDAOImpl organisationDAO;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(ModelMap model) {
        User newUser = new User();
        model.put("newUser", newUser);

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addNewUser(@ModelAttribute("newUser") User user) {

        Organisation organisation = organisationDAO.getOrganisation(user.getOrganisation());
        System.out.println(organisation.getId());

        return "user";
    }
}
