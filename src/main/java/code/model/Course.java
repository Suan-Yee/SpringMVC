package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public class Course {

    private int id;
    private String name;
    private String description;
    private String instructor;
    private String status;
}
