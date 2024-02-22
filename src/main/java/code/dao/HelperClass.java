package code.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HelperClass {


    private final EnrollmentDao enrollmentDao;

    public List<Integer> courseId(int id){
        return enrollmentDao.findCourseByStudentId(id);
    }
}
