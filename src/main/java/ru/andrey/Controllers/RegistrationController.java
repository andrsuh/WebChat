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

    @RequestMapping(value = "/org_choice", method = RequestMethod.GET)
    public String getOrganisation(ModelMap model) {
        Organisation organisation = new Organisation();
        model.addAttribute("organisation", organisation);
        model.addAttribute("orgList", organisationDAO.getAllOrganisation());

        return "org_choice";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(@ModelAttribute Organisation organisation, ModelMap model) {
        User newUser = new User();
        System.out.println("|" + organisation.getName() + "|");
        newUser.setOrganisation(organisation.getName());

        model.put("newUser", newUser);
        model.put("depList", organisationDAO.getAllDepartments(organisation.getName()));
        model.put("posList", organisationDAO.getAllPositions(organisation.getName()));

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addNewUser(@ModelAttribute("newUser") User user) {

        Organisation organisation = organisationDAO.getOrganisation(user.getOrganisation());
//        System.out.println(organisation.getId());
//        System.out.println(user.getSex());

        return "user";
    }
}
