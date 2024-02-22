package code.dao;

import code.model.Address;
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

@Service
@RequiredArgsConstructor
public class AddressDao {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Address createAddress(Address address){
        String sql = "INSERT INTO address (street,city,state,zip_code,country) VALUES (:street,:city,:state,:zip_code,:country)";
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sql,getSqlParameterSource(address),holder);
            address.setId(holder.getKey().intValue());
            return address;
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    private SqlParameterSource getSqlParameterSource(Address address){
        return new MapSqlParameterSource()
                .addValue("street",address.getStreet())
                .addValue("city",address.getCity())
                .addValue("state",address.getState())
                .addValue("zip_code",address.getZipcode())
                .addValue("country",address.getCountry());
    }
}
