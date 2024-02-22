package code.model;

import code.enumaration.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.NotEmpty;

@SuperBuilder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class User {

    @NotEmpty
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty(message = "Pleas enter the email")
    private String email;
    @NotEmpty(message = "Please enter the password")
    private String password;
    private String role;
}