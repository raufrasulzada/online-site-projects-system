package as1.spring.boot.jpa.app.raufrasulzada.Repositories;

import as1.spring.boot.jpa.app.raufrasulzada.Entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Course entities.
 */
@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    /**
     * Custom query to find a course by its name.
     * @param courseName The name of the course to search for.
     * @return The Course object with the specified name, if found.
     */
    Course findCourseByCourseName(String courseName);

    /**
     * Custom query to retrieve courses by course name using pagination.
     * @param courseName The name of the course (or part of it) to search for.
     * @param pageable Pagination information.
     * @return A page of Course objects matching the specified course name pattern.
     */
    @Query("SELECT c FROM Course c WHERE c.courseName LIKE %:courseName%")
    Page<Course> retrieveCoursesByCourseName(@Param("courseName") String courseName, Pageable pageable);

    /**
     * Custom query to retrieve courses by department using pagination.
     * @param department The department of the courses to retrieve.
     * @param pageable Pagination information.
     * @return A page of Course objects belonging to the specified department.
     */
    @Query(value = "SELECT * FROM courses WHERE (:department IS NULL OR department = :department)", nativeQuery = true)
    Page<Course> retrieveCoursesByDepartment(@Param("department") String department, Pageable pageable);

    /**
     * Custom query to retrieve courses by both course name and department using pagination.
     * @param courseName The name of the course to search for.
     * @param department The department of the course to search for.
     * @param pageable Pagination information.
     * @return A page of Course objects matching the specified name and department.
     */
    @Query(value = "SELECT * FROM courses WHERE coursename = :courseName AND department = :department", nativeQuery = true)
    Page<Course> retrieveCoursesByCourseNameAndDepartment(@Param("courseName") String courseName, @Param("department") String department, Pageable pageable);
}