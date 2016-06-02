package ru.andrey.DAOs.DAOImplementations;

import ru.andrey.DAOs.DAOInterfaces.MessageDAO;
import ru.andrey.Domain.Message;
import ru.andrey.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MessageDAOImpl implements MessageDAO {
    private static RowMapper messageRowMapper = new RowMapper<Message>() {
        @Override
        public Message mapRow(ResultSet resultSet, int i) throws SQLException {
            Message message = new Message();
            message.setContent(resultSet.getString("msg_content"));
            message.setSrcUserID(resultSet.getInt("msg_src_user_id"));
            message.setDstUserID(resultSet.getInt("msg_dst_user_id"));

            return message;
        }
    };
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addMessage(Message message, User toUser, User fromUser) {

    }

    @Override
    public List<Message> messagesByUser(String userName) {
        return jdbcTemplate.query(
                "SELECT msg_content, msg_src_user_id, msg_dst_user_id from messages " +
                        "WHERE msg_src_user_id = (SELECT user_id FROM users WHERE user_username = ?)",
                new Object[]{userName},
                messageRowMapper
        );
    }
}