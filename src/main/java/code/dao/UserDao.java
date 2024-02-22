package code.dao;

import code.model.User;
import code.rowMapper.UserRowMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;



    /*public User findById(int user_id){
        String sql = "SELECT * FROM user WHERE user_id = ?";
        return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setName(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    String roleString = rs.getString("role");
                    if (roleString != null) {
                        Role role = Role.valueOf(roleString.toUpperCase());
                        user.setRole(role);
                    }
                    return user;
                }
                return null;
            }
        });
    }*/
    public User createUser(User user){
        String sql = "INSERT INTO user (username,password,email) VALUES (:name,:password,:email)";
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(sql,getSqlParameterSource(user),keyHolder);
            return user;
        }catch (Exception e){
            throw new RuntimeException("An error occurred" + e.getMessage());
        }
    }
    public User findById(int id){
        String sql = "SELECT * FROM user WHERE user_id = :id";
        try{
            return jdbcTemplate.queryForObject(sql, Map.of("id",id),new UserRowMapper());
        }catch(EmptyResultDataAccessException e){
            throw new RuntimeException("No user found by id " + id);
        }catch(Exception e){
            throw new RuntimeException("An error occurred 2");
        }
    }
    public User loginUser(User user){
        String sql = "SELECT * FROM user WHERE email = :email AND password = :password";
        try{
            List<User> result =  jdbcTemplate.query(sql,Map.of("email",user.getEmail(),"password",user.getPassword()),new UserRowMapper());
            if (result.isEmpty()) {
                return null;
            } else if (result.size() == 1) {
                return result.get(0);
            } else {
                throw new RuntimeException("Multiple users found for the given credentials");
            }
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public User changeEmail(User user, HttpServletRequest request) {
        String sql = "UPDATE user SET email = :email,password = :password WHERE user_id = :id";
        int id = 0;
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("valid_user") != null) {
                User loggedInUser = (User) session.getAttribute("valid_user");
                id = loggedInUser.getId();
            }
        }catch (Exception e){
            System.out.println("error");
        }
        try{
            jdbcTemplate.update(sql,Map.of("email",user.getEmail(),"password",user.getPassword(),"id",id));
            User update_user = findById(id);
            return update_user;
        }catch (Exception e){
            return null;
        }
    }

    public List<User> findByName(String name){

        String sql = "SELECT * FROM user WHERE username = :name";
        try{
            return jdbcTemplate.query(sql,Map.of("name",name),new UserRowMapper());
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    /*public User updateUser(User user){
        try{
            String sql = "UPDATE user SET name = :name,email = :email";
            return jdbcTemplate.update(sql,getSqlParameterSource(user));
        }catch(EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }*/

    public boolean deleteUser(int userId){
        String sql = "DELETE from user WHERE user_id = :id";
        try{
            jdbcTemplate.update(sql,Map.of("id",userId));
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<User> findAll(){
        String sql = "SELECT * FROM user";
        try{
            List<User> users = jdbcTemplate.query(sql,new UserRowMapper());
        return users;
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public List<User> findByIdOrUserName(int id,String name){
        String sql = "SELECT * FROM user WHERE user_id = :id OR username = :name";
        try {
            List<User> users = jdbcTemplate.query(sql,Map.of("id",id,"name",name),new UserRowMapper());
            return users;
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Integer validEmail(String email){
        String sql = "SELECT COUNT(*) FROM user WHERE email = :email";
        try{
            return jdbcTemplate.queryForObject(sql,Map.of("email",email),Integer.class);
        }catch(EmptyResultDataAccessException e){
            throw new RuntimeException("An error occurred");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private SqlParameterSource getSqlParameterSource(User user){
        return new MapSqlParameterSource()
                .addValue("name",user.getName())
                .addValue("password",user.getPassword())
                .addValue("email",user.getEmail());
    }

}
