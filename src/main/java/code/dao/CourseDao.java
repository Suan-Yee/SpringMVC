package code.dao;

import code.model.Course;
import code.rowMapper.CourseRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseDao {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Course createCourse(Course course){
                try{
                    String sql = "INSERT INTO course (name,description,instructor) VALUES (:name,:description,:instructor)";
                    KeyHolder holder = new GeneratedKeyHolder();
                    namedParameterJdbcTemplate.update(sql,getSqlParameterSource(course),holder);
                    return course;
                }catch (EmptyResultDataAccessException e){
                    throw new RuntimeException("An error occurred");
                }
            }

    public Course mockCourse(Course course){
        try{
            String sql = "INSERT INTO course (name) VALUES (:name)";
            namedParameterJdbcTemplate.update(sql,Map.of("name",course.getName()));
            return course;
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException("An error occurred");
        }
    }
    public Course findById(int id){
        try{
            String sql = "SELECT * FROM course WHERE course_id = :id";
            return namedParameterJdbcTemplate.queryForObject(sql, Map.of("id",id),new CourseRowMapper());
        }catch(EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public Course findByName(String name){
        try{
            String sql = "SELECT * FROM course WHERE name = : name ";
            return namedParameterJdbcTemplate.queryForObject(sql,Map.of("name",name),new CourseRowMapper());
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public boolean deleteCourse(int id){
        try{
         String sql = "DELETE FROM course WHERE course_id = :id";
         namedParameterJdbcTemplate.update(sql,Map.of("id",id));
         return true;
        }catch(Exception e){
            System.out.println("Cannot delete");
            return false;
        }
    }
    public Course updateCourse(Course course){
        try{
           String sql = "UPDATE course SET name = :name,description = :description,instructor =:instructor WHERE course_id = :id";
           namedParameterJdbcTemplate.update(sql,getSqlParameterSource(course));
           Course updatedCourse = findById(course.getId());
           return updatedCourse;

        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Course> selectByStatus(){
        String sql = "SELECT * FROM course WHERE status = :status";
        try{
            List<Course> courses = namedParameterJdbcTemplate.query(sql,Map.of("status","publish"),new CourseRowMapper());
            return courses;
        }catch (EmptyResultDataAccessException e){
         throw  new RuntimeException(e.getMessage());
        }
    }
    public List<Course> findAllCourse(){
        String sql = "SELECT * FROM course";
        try{
            List<Course> courses = namedParameterJdbcTemplate.query(sql,new CourseRowMapper());
            return courses;
        }catch (EmptyResultDataAccessException e){
            throw  new RuntimeException(e.getMessage());
        }
    }
    public List<Course> findByIdOrName(int id,String name){
        String sql = "SELECT * FROM course WHERE course_id = :id OR name = :name";
        try{
            List<Course> courses  = namedParameterJdbcTemplate.query(sql,Map.of("id",id,"name",name),new CourseRowMapper());
            return courses;
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public Course changeStatus(int id,Course course){
        String sql = "UPDATE course SET status = :status WHERE course_id = :id";
        String sql2 = "UPDATE course SET status = :status WHERE course_id = :id";
        Course update_course = null;
        try{
            if(course.getStatus().equalsIgnoreCase("pending")){
                namedParameterJdbcTemplate.update(sql,Map.of("status","publish","id",id));
            }else{
                namedParameterJdbcTemplate.update(sql2,Map.of("status","pending","id",id));
            }
            update_course = findById(id);
            return update_course;
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private SqlParameterSource getSqlParameterSource(Course course){
        return new MapSqlParameterSource()
                .addValue("name",course.getName())
                .addValue("description",course.getDescription())
                .addValue("instructor",course.getInstructor());
    }
}
