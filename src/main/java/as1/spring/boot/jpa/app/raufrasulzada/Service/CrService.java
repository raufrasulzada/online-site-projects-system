package as1.spring.boot.jpa.app.raufrasulzada.Service;

import as1.spring.boot.jpa.app.raufrasulzada.Entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing Course entities.
 */
public interface CrService {

    /**
     * Retrieves all courses.
     * @return List of all courses.
     */
    List<Course> retrieveAllCourses();

    /**
     * Saves a new course.
     * @param course The course to be saved.
     * @return The saved course.
     */
    Course saveCourse(Course course);

    /**
     * Retrieves a course by its ID.
     * @param id The ID of the course to retrieve.
     * @return The retrieved course.
     */
    Course retrieveCourseById(Long id);

    /**
     * Updates an existing course.
     * @param course The course to be updated.
     * @return The updated course.
     */
    Course updateCourse(Course course);

    /**
     * Deletes a course by its ID.
     * @param id The ID of the course to delete.
     */
    void deleteCourseById(Long id);

    /**
     * Finds a course by its name.
     * @param courseName The name of the course to find.
     * @return The found course.
     */
    Course findCourseByDetails(String courseName);

    /**
     * Retrieves all courses with pagination.
     * @param pageable Pagination information.
     * @return A page of courses.
     */
    Page<Course> retrieveAllCoursesPaginated(Pageable pageable);

    /**
     * Updates students when a course is deleted.
     * @param courseId The ID of the course to delete.
     */
    void updateStudentsWhenCourseDeleted(Long courseId);

    /**
     * Retrieves courses by course name with pagination.
     * @param courseName The name of the course to search for.
     * @param pageable Pagination information.
     * @return A page of courses matching the specified name.
     */
    Page<Course> retrieveCoursesByCourseName(String courseName, Pageable pageable);

    /**
     * Retrieves courses by department with pagination.
     * @param department The department of the courses to search for.
     * @param pageable Pagination information.
     * @return A page of courses matching the specified department.
     */
    Page<Course> retrieveCoursesByDepartment(String department, Pageable pageable);

    /**
     * Retrieves courses by course name and department with pagination.
     * @param courseName The name of the course to search for.
     * @param department The department of the courses to search for.
     * @param pageable Pagination information.
     * @return A page of courses matching the specified name and department.
     */
    Page<Course> retrieveCoursesByCourseNameAndDepartment(String courseName, String department, Pageable pageable);
}
