package code.model;

import code.enumaration.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public class Student {

    private int id;
    private String name;
    private String dob;
    private String gender;
    private String phone;
    private String education;
    private User user;
    private Address address;


}
