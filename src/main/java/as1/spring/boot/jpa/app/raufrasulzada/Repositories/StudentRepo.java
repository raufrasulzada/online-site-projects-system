package as1.spring.boot.jpa.app.raufrasulzada.Repositories;

import as1.spring.boot.jpa.app.raufrasulzada.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Repository interface for managing Student entities.
 */
@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

     /**
     * Custom query to find students by course name.
     * @param courseName The name of the course to search for.
     * @return A list of Student objects enrolled in the specified course.
     */
    List<Student> findByCourses_CourseName(String courseName);

    /**
     * Custom query to find students by first name and last name.
     * @param firstName The first name of the student to search for.
     * @param lastName The last name of the student to search for.
     * @return A list of Student objects matching the specified first and last names.
     */
    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.lastName = ?2")
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);
 
    /**
     * Custom query to find students by course ID.
     * @param courseId The ID of the course.
     * @return A list of Student objects enrolled in the specified course.
     */
    List<Student> findByCoursesId(Long courseId);

    /**
     * Custom query to find students by first name and last name with pagination.
     * @param firstName The first name of the student to search for.
     * @param lastName The last name of the student to search for.
     * @param pageable Pagination information.
     * @return A page of Student objects matching the specified first and last names.
     */
    Page<Student> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

    /**
     * Custom native query to find students by first name with pagination.
     * @param firstName The first name of the student to search for.
     * @param pageable Pagination information.
     * @return A page of Student objects matching the specified first name.
     */
    @Query(value = "SELECT * FROM students s WHERE s.firstName = ?1", nativeQuery = true)
    Page<Student> findByFirstName(String firstName, Pageable pageable);

    /**
     * Custom native query to find students by last name with pagination.
     * @param lastName The last name of the student to search for.
     * @param pageable Pagination information.
     * @return A page of Student objects matching the specified last name.
     */
    @Query(value = "SELECT * FROM students s WHERE s.lastName = ?1", nativeQuery = true)
    Page<Student> findByLastName(String lastName, Pageable pageable);

    /**
     * Custom native query to find students by course name with pagination.
     * @param courseName The name of the course to search for.
     * @param pageable Pagination information.
     * @return A page of Student objects enrolled in the specified course.
     */
    @Query(value = "SELECT DISTINCT s.* FROM students s LEFT JOIN studentcourse sc ON s.id = sc.studentid LEFT JOIN courses c ON sc.courseid = c.id WHERE (:courseName IS NULL OR c.coursename LIKE CONCAT('%', :courseName, '%'))", nativeQuery = true)
    Page<Student> findByCourseName(@Param("courseName") String courseName, Pageable pageable);


    /**
     * Custom native query to retrieve students by first name, last name, and course name with pagination.
     * @param firstName The first name of the student to search for.
     * @param lastName The last name of the student to search for.
     * @param courseName The name of the course to search for.
     * @param pageable Pagination information.
     * @return A page of Student objects matching the specified first name, last name, and course name.
     */
    @Query(value = "SELECT s.* FROM students s " +
            "JOIN studentcourse sc ON s.id = sc.studentid " +
            "JOIN courses c ON sc.courseid = c.id " +
            "WHERE s.firstname = :firstName " +
            "AND s.lastname = :lastName " +
            "AND c.coursename = :courseName",
            countQuery = "SELECT COUNT(s.id) FROM students s " +
                    "JOIN studentcourse sc ON s.id = sc.studentid " +
                    "JOIN courses c ON sc.courseid = c.id " +
                    "WHERE s.firstname = :firstName " +
                    "AND s.lastname = :lastName " +
                    "AND c.coursename = :courseName",
            nativeQuery = true)
    Page<Student> retrieveStudentsByFirstNameLastNameAndCourseName(String firstName, String lastName, String courseName, Pageable pageable);

    /**
     * Custom native query to retrieve students by first name and course name with pagination.
     * @param firstName The first name of the student to search for.
     * @param courseName The name of the course to search for.
     * @param pageable Pagination information.
     * @return A page of Student objects matching the specified first name and course name.
     */
    @Query(value = "SELECT s.* FROM students s " +
            "JOIN studentcourse sc ON s.id = sc.studentid " +
            "JOIN courses c ON sc.courseid = c.id " +
            "WHERE s.firstname = :firstName " +
            "AND c.coursename = :courseName",
            countQuery = "SELECT COUNT(s.id) FROM students s " +
                    "JOIN studentcourse sc ON s.id = sc.studentid " +
                    "JOIN courses c ON sc.courseid = c.id " +
                    "WHERE s.firstname = :firstName " +
                    "AND c.coursename = :courseName",
            nativeQuery = true)
    Page<Student> retrieveStudentsByFirstNameAndCourseName(String firstName, String courseName, Pageable pageable);

    /**
     * Custom native query to retrieve students by last name and course name with pagination.
     * @param lastName The last name of the student to search for.
     * @param courseName The name of the course to search for.
     * @param pageable Pagination information.
     * @return A page of Student objects matching the specified last name and course name.
     */
    @Query(value = "SELECT s.* FROM students s " +
            "JOIN studentcourse sc ON s.id = sc.studentid " +
            "JOIN courses c ON sc.courseid = c.id " +
            "WHERE s.lastname = :lastName " +
            "AND c.coursename = :courseName",
            countQuery = "SELECT COUNT(s.id) FROM students s " +
                    "JOIN studentcourse sc ON s.id = sc.studentid " +
                    "JOIN courses c ON sc.courseid = c.id " +
                    "WHERE s.lastname = :lastName " +
                    "AND c.coursename = :courseName",
            nativeQuery = true)
    Page<Student> retrieveStudentsByLastNameAndCourseName(String lastName, String courseName, Pageable pageable);
}