package code.dao;

import code.model.Student;

import code.rowMapper.StudentRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
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

public class StudentDao {


    @Autowired
    private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private  AddressDao addressDao;

    public StudentDao(){

    }

    public Student createStudent(Student student){
        String sql = "INSERT INTO student (name,dob,gender,phone,education) VALUES (:name,:dob,:gender,:phone,:education)";
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sql,getSqlParameterSource(student),holder);
            student.setId(holder.getKey().intValue());
            Student result_student = findById(student.getId());
            return result_student;
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public Student findById(int id){
        try{
            String sql = "SELECT * FROM student WHERE student_id = :id";
            return namedParameterJdbcTemplate.queryForObject(sql, Map.of("id",id),new StudentRowMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public List<Student> findByName(String name){
        try{
            String sql = "SELECT * FROM student WHERE name = :name";
            List<Student> students = namedParameterJdbcTemplate.query(sql,Map.of("name",name),new StudentRowMapper());
            return students;
        }catch(EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public List<Student> findAllStudent(){
        try{
            String sql = "SELECT * FROM student";
            List<Student> students = namedParameterJdbcTemplate.query(sql,new StudentRowMapper());
            return students;
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public Student updateStudent(Student student){
        try{
            String sql = "UPDATE student SET name = :name,dob = :dob, gender = :gender,phone = :phone,education =:education WHERE student_id = :id ";
            namedParameterJdbcTemplate.update(sql,getSqlParameterSource(student));
            Student updated_student = findById(student.getId());
            System.out.println(updated_student);
            return updated_student;
        }catch (Exception e){
            return null;
        }
    }
    public boolean deleteStudent(int studentId){
        String sql = "DELETE FROM student WHERE student_id  = :id";
        try{
            namedParameterJdbcTemplate.update(sql,Map.of("id",studentId));
            return true;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    /*public List<Student> searchStudent(StudentSearch studentSearch){
        String sql = "SELECT * FROM student WHERE 1 = 1";
        if(studentSearch.getId() != 0){
            sql += "AND student_id = " + studentSearch.getId();
        }
        if(studentSearch.getName() != null || studentSearch.getName().equals("")){
            sql += "AND name = '" + studentSearch.getName() + "'";
        }
        if(studentSearch.getCourse() != null || studentSearch.getCourse().equals("")){
            sql += ""
        }*/

    private SqlParameterSource getSqlParameterSource(Student student){
        return new MapSqlParameterSource()
                .addValue("id",student.getId())
                .addValue("name",student.getName())
                .addValue("dob",student.getDob())
                .addValue("gender",student.getGender())
                .addValue("phone",student.getPhone())
                .addValue("education",student.getEducation());
    }
}
