package code.rowMapper;

import code.model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {


    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Course.builder()
                .id(rs.getInt("course_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .instructor(rs.getString("instructor"))
                .status(rs.getString("status"))
                .build();
    }
}
