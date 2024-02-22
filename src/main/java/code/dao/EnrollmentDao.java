package code.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EnrollmentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean saveEnrollment(int studentId,int courseId){
        String sql = "INSERT INTO enrollment (student_id,course_id,enrollment_date) VALUES (:student_id,:course_id,:date)";
        try{
            namedParameterJdbcTemplate.update(sql,getSqlParameterSource(studentId,courseId));
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public List<Integer> findCourseByStudentId(int studentId){
        String sql = "SELECT course_id FROM enrollment WHERE student_id = :id";
        try{
            return namedParameterJdbcTemplate.queryForList(sql, Map.of("id", studentId), Integer.class);
        }catch (EmptyResultDataAccessException e){
            return Collections.emptyList();
        }
    }
    public boolean deleteCourseAndStudent(int studentId) {
        String sql = "DELETE FROM enrollment WHERE student_id = :id";
        try {
            namedParameterJdbcTemplate.update(sql, Map.of("id", studentId));
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    private SqlParameterSource getSqlParameterSource(int student_id,int course_id){
        LocalDateTime date = LocalDateTime.now();
        return new MapSqlParameterSource()
                .addValue("student_id",student_id)
                .addValue("course_id",course_id)
                .addValue("date",date);
    }
}
