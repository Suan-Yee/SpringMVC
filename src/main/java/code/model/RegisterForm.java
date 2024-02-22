package code.model;

import code.model.Course;
import code.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RegisterForm {

    private List<Integer> courses;
    private Student student;
}
