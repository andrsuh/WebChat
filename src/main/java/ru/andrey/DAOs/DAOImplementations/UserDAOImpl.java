package ru.andrey.DAOs.DAOImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.andrey.DAOs.DAOInterfaces.OrganisationDAO;
import ru.andrey.DAOs.DAOInterfaces.UserDAO;
import ru.andrey.Domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private OrganisationDAO organisationDAO;

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setUsername(resultSet.getString("user_username"));
            user.setName(resultSet.getString("user_name"));
            return user;
        }
    };

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserByID(Integer userID) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE user_id = ?",
                new Object[]{userID},
                userRowMapper
        );
    }

    @Override
    public User getUserByLogin(String login) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE user_username = ?",
                new Object[]{login},
                userRowMapper
        );
    }

    @Override
    public void addUser(User user) {
        Integer posId = organisationDAO.getPositionIdByName(user.getPosition());
        Integer depId = organisationDAO.getDepartmentIdByName(user.getDepartment());
        Integer orgId = organisationDAO.getOrganisationIdByName(user.getOrganisation());

        user.setEnabled(true);

        jdbcTemplate.update("INSERT INTO users(\n" +
                " user_username," +
                " user_password," +
                " user_enabled," +
                " user_name," +
                " user_sex," +
                " user_details," +
                " user_position_id," +
                " user_department_id," +
                " user_organisation_id" +
                " ) VALUES" +
                " (?, ?, ?, ?, ?::sex, ?, ?, ?, ?)",
                user.getUsername(), user.getPassword(),
                user.isEnabled(), user.getName(), user.getSex(),
                user.getDetails(), posId, depId, orgId);
    }

    @Override
    public List<User> allFriends(String login) {
        Integer userId = jdbcTemplate.queryForObject(
                "SELECT * FROM getUserIdByName(?)",
                Integer.class,
                login
        );

        return jdbcTemplate.query(
                "SELECT * FROM users JOIN " +
                        "(SELECT F.frd_other_friend_id id from users JOIN friendShip F " +
                        "ON users.user_id = F.frd_friend_id WHERE users.user_id = ?) TEMP ON users.user_id = TEMP.id",
                new Object[]{userId},
                userRowMapper
        );
    }
}
