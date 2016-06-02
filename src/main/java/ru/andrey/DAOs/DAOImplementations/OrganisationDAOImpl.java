package ru.andrey.DAOs.DAOImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.andrey.DAOs.DAOInterfaces.OrganisationDAO;
import ru.andrey.Domain.Department;
import ru.andrey.Domain.Message;
import ru.andrey.Domain.Organisation;
import ru.andrey.Domain.Position;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class OrganisationDAOImpl implements OrganisationDAO {
//    new RowMapper<Message>() {
//        @Override
//        public Message mapRow(ResultSet resultSet, int i) throws SQLException {
//            Message message = new Message();
//            message.setContent(resultSet.getString("msg_content"));
//            message.setSrcUserID(resultSet.getInt("msg_src_user_id"));
//            message.setDstUserID(resultSet.getInt("msg_dst_user_id"));
//
//            return message;
//        }
//    };

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Organisation getOrganisation(String orgName) {
        return jdbcTemplate.queryForObject(
                "SELECT org_id, org_name FROM organisations WHERE org_name = ? ",
                new Object[] {orgName},
                new RowMapper<Organisation>() {

                    @Override
                    public Organisation mapRow(ResultSet resultSet, int num) throws SQLException {
                        Organisation organisation = new Organisation();

                        organisation.setId(resultSet.getInt("org_id"));
                        organisation.setName(resultSet.getString("org_name"));

                        return organisation;
                    }
                }
        );
    }

    @Override
    public List<Department> getAllDepartments() {
        return null;
    }

    @Override
    public List<Position> getAllPositions() {
        return null;
    }
}
