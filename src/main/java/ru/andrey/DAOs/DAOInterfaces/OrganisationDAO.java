package ru.andrey.DAOs.DAOInterfaces;

import ru.andrey.Domain.Department;
import ru.andrey.Domain.Organisation;
import ru.andrey.Domain.Position;

import java.util.List;

public interface OrganisationDAO {
    Organisation getOrganisation(String orgName);

    List<Department> getAllDepartments();

    List<Position> getAllPositions();
}
