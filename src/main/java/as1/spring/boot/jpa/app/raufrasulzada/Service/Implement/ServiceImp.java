package as1.spring.boot.jpa.app.raufrasulzada.Service.Implement;

import as1.spring.boot.jpa.app.raufrasulzada.Entities.Course;
import as1.spring.boot.jpa.app.raufrasulzada.Entities.Student;
import as1.spring.boot.jpa.app.raufrasulzada.Repositories.CourseRepo;
import as1.spring.boot.jpa.app.raufrasulzada.Repositories.StudentRepo;
import as1.spring.boot.jpa.app.raufrasulzada.Service.CrService;
import as1.spring.boot.jpa.app.raufrasulzada.Service.StdService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Implementation of the {@link StdService} and {@link CrService} interfaces.
 * Handles CRUD operations for students and courses.
 */
@Transactional
@Service
@Data
@RequiredArgsConstructor
public class ServiceImp implements StdService, CrService {

    private final StudentRepo studentRepo;

    private final CourseRepo courseRepo;

    /**
     * Retrieves all courses.
     *
     * @return A list of all courses.
     */
    @Override
    public List<Course> retrieveAllCourses() {
        return courseRepo.findAll();
    }

    /**
     * Saves a student.
     *
     * @param student The student to save.
     * @return The saved student.
     */
    @Override
    public Student saveStudent(Student student) {
        return studentRepo.save(student);
    }

    /**
     * Saves a course.
     *
     * @param course The course to save.
     * @return The saved course.
     */
    @Override
    public Course saveCourse(Course course) {
        return courseRepo.save(course);
    }

     /**
     * Retrieves a student by ID.
     *
     * @param id The ID of the student to retrieve.
     * @return The retrieved student.
     */
    @Override
    public Student retrieveStudentById(Long id) {
        return studentRepo.findById(id).get();
    }

    /**
    * Retrieves a course by ID.
    *
    * @param id The ID of the course to retrieve.
    * @return The retrieved course.
    */
    @Override
    public Course retrieveCourseById(Long id) {
        return courseRepo.findById(id).get();
    }

    /**
    * Updates a student.
    *
    * @param student The student to update.
    * @return The updated student.
    */
    @Override
    public Student updateStudent(Student student) {
        return studentRepo.save(student);
    }

    /**
     * Updates a course.
    *
    * @param course The course to update.
    * @return The updated course.
    */
    @Override
    public Course updateCourse(Course course) {
        return courseRepo.save(course);
    }

    /**
    * Deletes a student by ID.
    *
    * @param id The ID of the student to delete.
    */
    @Override
    public void deleteStudentById(Long id) {
        studentRepo.deleteById(id);
    }

    /**
    * Deletes a course by ID.
    *
    * @param id The ID of the course to delete.
    */
    @Override
    public void deleteCourseById(Long id) {
        courseRepo.deleteById(id);
        updateStudentsWhenCourseDeleted(id);
    }

    /**
    * Finds students by course name.
     *
    * @param courseName The name of the course.
    * @return A list of students enrolled in the specified course.
    */
    @Override
    public List<Student> findStudentByCourseName(String courseName) {
        return studentRepo.findByCourses_CourseName(courseName);
    }

    /**
    * Finds a student by details.
    *
    * @param student The student details to search for.
    * @return The found student or null if not found.
    */
    @Override
    public Student findStudentByDetails(Student student) {
        List<Student> students = studentRepo.findByFirstNameAndLastName(student.getFirstName(), student.getLastName());
        for (Student s : students) {
            if (s.getCourses().containsAll(student.getCourses())) {
                return s;
            }
        }
        return null;
    }

    /**
    * Finds a course by name.
    *
    * @param courseName The name of the course to search for.
    * @return The found course or null if not found.
    */
    @Override
    public Course findCourseByDetails(String courseName) {
        return courseRepo.findCourseByCourseName(courseName);
    }

    /**
    * Retrieves all students with courses paginated.
    *
    * @param pageable The pagination information.
    * @return A page of students with courses.
    */
    @Override
    public Page<Student> retrieveAllStudentsWithCoursesPaginated(Pageable pageable) {
        return studentRepo.findAll(pageable);
    }

    /**
    * Retrieves all courses paginated.
    *
    * @param pageable The pagination information.
    * @return A page of courses.
    */
    @Override
    public Page<Course> retrieveAllCoursesPaginated(Pageable pageable) {
        return courseRepo.findAll(pageable);
    }

    /**
    * Updates students when a course is deleted.
    *
    * @param courseId The ID of the course being deleted.
    */
    @Override
    public void updateStudentsWhenCourseDeleted(Long courseId) {
        List<Student> students = studentRepo.findByCoursesId(courseId);
        for (Student student : students) {
            student.getCourses().removeIf(course -> course.getId().equals(courseId));
            studentRepo.save(student);
        }
    }

    /**
    * Retrieves students by first name and last name paginated.
    *
    * @param firstName The first name of the student.
    * @param lastName  The last name of the student.
    * @param pageable  The pagination information.
    * @return A page of students matching the first name and last name.
    */
    @Override
    public Page<Student> retrieveStudentsByFirstNameAndLastName(String firstName, String lastName, Pageable pageable) {
        return studentRepo.findByFirstNameAndLastName(firstName, lastName, pageable);
    }

    /**
    * Retrieves students by first name paginated.
    *
    * @param firstName The first name of the student.
    * @param pageable  The pagination information.
    * @return A page of students matching the first name.
     */
    @Override
    public Page<Student> retrieveStudentsByFirstName(String firstName, Pageable pageable) {
        return studentRepo.findByFirstName(firstName, pageable);
    }

    /**
    * Retrieves students by last name paginated.
    *
    * @param lastName  The last name of the student.
    * @param pageable  The pagination information.
    * @return A page of students matching the last name.
    */
    @Override
    public Page<Student> retrieveStudentsByLastName(String lastName, Pageable pageable) {
        return studentRepo.findByLastName(lastName, pageable);
    }

    /**
    * Retrieves students by course name paginated.
     *
    * @param courseName The name of the course.
    * @param pageable   The pagination information.
    * @return A page of students enrolled in the specified course.
    */
    @Override
    public Page<Student> retrieveStudentsByCourseName(String courseName, Pageable pageable) {
        if (StringUtils.isEmpty(courseName)) {
            return studentRepo.findAll(pageable);
        } else {
            return studentRepo.findByCourseName(courseName, pageable);
        }
    }

    /**
    * Retrieves students by first name, last name, and course name paginated.
    *
    * @param firstName  The first name of the student.
    * @param lastName   The last name of the student.
    * @param courseName The name of the course.
    * @param pageable   The pagination information.
    * @return A page of students matching the first name, last name, and course name.
    */
    @Override
    public Page<Student> retrieveStudentsByFirstNameLastNameAndCourseName(String firstName, String lastName, String courseName, Pageable pageable) {
        return studentRepo.retrieveStudentsByFirstNameLastNameAndCourseName(firstName, lastName, courseName, pageable);
    }

    /**
    * Retrieves students by first name and course name paginated.
    *
    * @param firstName  The first name of the student.
    * @param courseName The name of the course.
    * @param pageable   The pagination information.
    * @return A page of students matching the first name and course name.
    */
    @Override
    public Page<Student> retrieveStudentsByFirstNameAndCourseName(String firstName, String courseName, Pageable pageable) {
        return studentRepo.retrieveStudentsByFirstNameAndCourseName(firstName, courseName, pageable);
    }

    /**
    * Retrieves students by last name and course name paginated.
     *
    * @param lastName   The last name of the student.
    * @param courseName The name of the course.
    * @param pageable   The pagination information.
    * @return A page of students matching the last name and course name.
    */
    @Override
    public Page<Student> retrieveStudentsByLastNameAndCourseName(String lastName, String courseName, Pageable pageable) {
        return studentRepo.retrieveStudentsByLastNameAndCourseName(lastName, courseName, pageable);
    }

    /**
    * Retrieves courses by course name paginated.
    *
    * @param courseName The name of the course.
    * @param pageable   The pagination information.
    * @return A page of courses matching the course name.
    */
    @Override
    public Page<Course> retrieveCoursesByCourseName(String courseName, Pageable pageable) {
        if (StringUtils.isEmpty(courseName)) {
            return courseRepo.findAll(pageable);
        } else {
            return courseRepo.retrieveCoursesByCourseName(courseName, pageable);
        }
    }

    /**
     * Retrieves courses by department paginated.
    *
    * @param department The department of the course.
    * @param pageable   The pagination information.
    * @return A page of courses matching the department.
     */
    @Override
    public Page<Course> retrieveCoursesByDepartment(String department, Pageable pageable) {
        return courseRepo.retrieveCoursesByDepartment(department, pageable);
    }

    /**
    * Retrieves courses by course name and department paginated.
    *
    * @param courseName The name of the course.
    * @param department The department of the course.
    * @param pageable   The pagination information.
    * @return A page of courses matching the course name and department.
    */
    @Override
    public Page<Course> retrieveCoursesByCourseNameAndDepartment(String courseName, String department, Pageable pageable) {
        return courseRepo.retrieveCoursesByCourseNameAndDepartment(courseName, department, pageable);
    }
}