package code.rowMapper;


import code.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getInt("user_id"))
                    .name(rs.getString("username"))
                    .password(rs.getString("password"))
                    .role(rs.getString("role"))
                    .email(rs.getString("email")).build();
        }
}
