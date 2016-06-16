package ru.andrey.DAOs.DAOImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.andrey.DAOs.DAOInterfaces.MessageDAO;
import ru.andrey.Domain.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
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
    public Message addMessage(String fromUserName, Integer toUserID, String content) {
        Integer fromUserID = jdbcTemplate.queryForObject(
                "SELECT user_id FROM users WHERE user_username = ?",
                Integer.class,
                fromUserName
        );

        jdbcTemplate.update(
                "INSERT INTO messages(msg_time, msg_dst_user_id, msg_src_user_id, msg_content)\n" +
                        " VALUES\n" +
                        " (?, ?, ?, ?)",
                new Timestamp(Calendar.getInstance().getTimeInMillis()),
                toUserID,
                fromUserID,
                content
        );
        Message message = new Message();
        message.setSrcUserID(fromUserID);
        message.setDstUserID(toUserID);
        message.setContent(content);

        return message;
    }

    @Override
    public List<Message> messagesByUser(String authenticatedUserName, Integer otherUserId) {
        Integer authenticatedUserId = jdbcTemplate.queryForObject(
                "select * from getUserIdByName(?)",
                Integer.class,
                authenticatedUserName
        );


        return jdbcTemplate.query(
                "SELECT msg_content, msg_src_user_id, msg_dst_user_id from messages " +
                        "WHERE (msg_src_user_id = ? AND msg_dst_user_id = ?) OR " +
                        "(msg_src_user_id = ? AND msg_dst_user_id = ?)" +
                        "ORDER BY msg_time",
                new Object[]{authenticatedUserId, otherUserId, otherUserId, authenticatedUserId},
                messageRowMapper
        );
    }
}
