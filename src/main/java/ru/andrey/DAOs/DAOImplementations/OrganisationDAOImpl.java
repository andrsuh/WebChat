package ru.andrey.DAOs.DAOImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.andrey.DAOs.DAOInterfaces.OrganisationDAO;
import ru.andrey.Domain.Department;
import ru.andrey.Domain.Organisation;
import ru.andrey.Domain.Position;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class OrganisationDAOImpl implements OrganisationDAO {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Organisation> rowMapper = new RowMapper<Organisation>() {
        @Override
        public Organisation mapRow(ResultSet resultSet, int num) throws SQLException {
            Organisation organisation = new Organisation();

            organisation.setId(resultSet.getInt("org_id"));
            organisation.setName(resultSet.getString("org_name"));

            return organisation;
        }
    };

    @Override
    public Integer getOrganisationIdByName(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT org_id FROM organisations WHERE org_name = ?",
                Integer.class,
                name
        );
    }

    @Override
    public Integer getDepartmentIdByName(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT dep_id FROM departments WHERE dep_name = ?",
                Integer.class,
                name
        );
    }

    @Override
    public Integer getPositionIdByName(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT pos_id FROM positions WHERE pos_name = ?",
                Integer.class,
                name
        );
    }

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Organisation> getAllOrganisation() {
        return jdbcTemplate.query(
                "SELECT org_id, org_name FROM organisations",
                new Object[]{},
                rowMapper
        );
    }

    @Override
    public Organisation getOrganisation(String orgName) {
        return jdbcTemplate.queryForObject(
                "SELECT org_id, org_name FROM organisations WHERE org_name = ? ",
                new Object[]{orgName},
                rowMapper
        );
    }

//    SELECT D.dep_name dep_name FROM departments D JOIN dep_org C
//    ON D.dep_id = C.dep_id
//    WHERE C.org_id = (SELECT org_id FROM organisations WHERE org_name = 'EMC Corporation')

    @Override
    public List<Department> getAllDepartments(String orgName) {
        return jdbcTemplate.query(
                "SELECT D.dep_id dep_id, D.dep_name dep_name FROM departments as D JOIN dep_org C" +
                        " ON D.dep_id = C.dep_id" +
                        " WHERE C.org_id = (SELECT org_id FROM organisations WHERE org_name = ?)",
                new Object[]{orgName},
                new RowMapper<Department>() {
                    @Override
                    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
                        Department dep = new Department();

                        dep.setId(resultSet.getInt("dep_id"));
                        dep.setName(resultSet.getString("dep_name"));

                        return dep;
                    }
                }
        );
    }

//    SELECT P.pos_name FROM positions P JOIN pos_org C
//    ON P.pos_id = C.pos_id
//    WHERE C.org_id = (SELECT org_id FROM organisations WHERE org_name = 'EMC Corporation')

    @Override
    public List<Position> getAllPositions(String orgName) {
        return jdbcTemplate.query(
                "SELECT P.pos_id pos_id, P.pos_name pos_name FROM positions P JOIN pos_org C" +
                        " ON P.pos_id = C.pos_id" +
                        " WHERE C.org_id = (SELECT org_id FROM organisations WHERE org_name = ?)",
                new Object[]{orgName},
                new RowMapper<Position>() {
                    @Override
                    public Position mapRow(ResultSet resultSet, int i) throws SQLException {
                        Position position = new Position();

                        position.setId(resultSet.getInt("pos_id"));
                        position.setName(resultSet.getString("pos_name"));

                        return position;
                    }
                }
        );
    }
}
