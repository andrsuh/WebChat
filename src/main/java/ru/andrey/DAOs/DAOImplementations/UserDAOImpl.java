package ru.andrey.DAOs.DAOImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.andrey.DAOs.DAOInterfaces.UserDAO;
import ru.andrey.Domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("user_name"));
            return user;
        }
    };

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(String username, String password) {

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
    public List<User> allFriends(String login) {
        Integer userId = jdbcTemplate.queryForObject(
                "select * from getUserIdByName(?)",
                Integer.class,
                login
        );

        return jdbcTemplate.query(
                "SELECT user_id, user_name FROM users JOIN " +
                        "(SELECT F.frd_other_friend_id id from users JOIN friendShip F " +
                        "ON users.user_id = F.frd_friend_id WHERE users.user_id = ?) TEMP ON users.user_id = TEMP.id",
                new Object[]{userId},
                userRowMapper
        );
    }
}
