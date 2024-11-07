package as1.spring.boot.jpa.app.raufrasulzada.Service;

import as1.spring.boot.jpa.app.raufrasulzada.Entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing Student entities.
 */
public interface StdService {

    /**
     * Saves a new student.
     * @param student The student to be saved.
     * @return The saved student.
     */
    Student saveStudent(Student student);

    /**
     * Retrieves a student by its ID.
     * @param id The ID of the student to retrieve.
     * @return The retrieved student.
     */
    Student retrieveStudentById(Long id);

    /**
     * Updates an existing student.
     * @param student The student to be updated.
     * @return The updated student.
     */
    Student updateStudent(Student student);

    /**
     * Deletes a student by its ID.
     * @param id The ID of the student to delete.
     */
    void deleteStudentById(Long id);

    /**
     * Finds students by course name.
     * @param courseName The name of the course to find students for.
     * @return A list of students enrolled in the specified course.
     */
    List<Student> findStudentByCourseName(String courseName);

    /**
     * Finds a student by details.
     * @param student The details of the student to find.
     * @return The found student.
     */
    Student findStudentByDetails(Student student);

    /**
     * Retrieves all students with their courses with pagination.
     * @param pageable Pagination information.
     * @return A page of students with their courses.
     */
    Page<Student> retrieveAllStudentsWithCoursesPaginated(Pageable pageable);

    /**
     * Retrieves students by first name and last name with pagination.
     * @param firstName The first name of the student to retrieve.
     * @param lastName The last name of the student to retrieve.
     * @param pageable Pagination information.
     * @return A page of students with the specified first and last names.
     */
    Page<Student> retrieveStudentsByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

    /**
     * Retrieves students by course name with pagination.
     * @param courseName The name of the course to find students for.
     * @param pageable Pagination information.
     * @return A page of students enrolled in the specified course.
     */
    Page<Student> retrieveStudentsByCourseName(String courseName, Pageable pageable);

    /**
     * Retrieves students by first name with pagination.
     * @param firstName The first name of the student to retrieve.
     * @param pageable Pagination information.
     * @return A page of students with the specified first name.
     */
    Page<Student> retrieveStudentsByFirstName(String firstName, Pageable pageable);

    /**
     * Retrieves students by last name with pagination.
     * @param lastName The last name of the student to retrieve.
     * @param pageable Pagination information.
     * @return A page of students with the specified last name.
     */
    Page<Student> retrieveStudentsByLastName(String lastName, Pageable pageable);

    /**
     * Retrieves students by first name, last name, and course name with pagination.
     * @param firstName The first name of the student to retrieve.
     * @param lastName The last name of the student to retrieve.
     * @param courseName The name of the course to find students for.
     * @param pageable Pagination information.
     * @return A page of students with the specified first name, last name, and course name.
     */
    Page<Student> retrieveStudentsByFirstNameLastNameAndCourseName(String firstName, String lastName, String courseName, Pageable pageable);

    /**
     * Retrieves students by first name and course name with pagination.
     * @param firstName The first name of the student to retrieve.
     * @param courseName The name of the course to find students for.
     * @param pageable Pagination information.
     * @return A page of students with the specified first name and course name.
     */
    Page<Student> retrieveStudentsByFirstNameAndCourseName(String firstName, String courseName, Pageable pageable);

    /**
     * Retrieves students by last name and course name with pagination.
     * @param lastName The last name of the student to retrieve.
     * @param courseName The name of the course to find students for.
     * @param pageable Pagination information.
     * @return A page of students with the specified last name and course name.
     */
    Page<Student> retrieveStudentsByLastNameAndCourseName(String lastName, String courseName, Pageable pageable);
}
