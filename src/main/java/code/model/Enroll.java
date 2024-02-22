package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public class Enroll {

    private int id;
    private Student student;
    private Course course;
    private LocalDateTime enroll_date;
}
