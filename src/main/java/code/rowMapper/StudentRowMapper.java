package code.rowMapper;

import code.enumaration.Gender;
import code.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        String genderString = rs.getString("gender");
        Gender gender = Gender.valueOf(genderString.toUpperCase());
        return Student.builder()
                .id(rs.getInt("student_id"))
                .name(rs.getString("name"))
                .dob(rs.getDate("dob").toString())
                .gender(rs.getString("gender"))
                .phone(rs.getString("phone"))
                .education(rs.getString("education"))
                .build();

    }
}
