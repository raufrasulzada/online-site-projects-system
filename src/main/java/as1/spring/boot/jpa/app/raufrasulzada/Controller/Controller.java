package as1.spring.boot.jpa.app.raufrasulzada.Controller;

import as1.spring.boot.jpa.app.raufrasulzada.Entities.Course;
import as1.spring.boot.jpa.app.raufrasulzada.Entities.Student;
import as1.spring.boot.jpa.app.raufrasulzada.Service.CrService;
import as1.spring.boot.jpa.app.raufrasulzada.Service.StdService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller class responsible for handling HTTP requests related to students and courses.
 */
@Data
@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class Controller {

    private final StdService stdService;

    private final CrService crService;

     /**
     * Mapping for the homepage.
     *
     * @return The name of the homepage view.
     */
    @GetMapping("/homepage")
    public String homepage() {
        return "homepage";
    }

    /**
     * Mapping for listing students.
     *
     * @param model      The model to which attributes are added.
     * @param page       The page number for pagination.
     * @param sortField  The field to sort by.
     * @param sortOrder  The sorting order (asc/desc).
     * @param firstName  The first name of the student to filter by.
     * @param lastName   The last name of the student to filter by.
     * @param courseName The name of the course to filter by.
     * @return The name of the view to display the list of students.
     */
    @GetMapping("/students")
    public String listStudents(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "firstName") String sortField,
                               @RequestParam(defaultValue = "asc") String sortOrder,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String courseName) {
        Page<Student> studentsPage;
        // Check if all three search criteria are provided: firstName, lastName, and courseName
        if (StringUtils.hasText(firstName) && StringUtils.hasText(lastName) && StringUtils.hasText(courseName)) {
            studentsPage = stdService.retrieveStudentsByFirstNameLastNameAndCourseName(firstName, lastName, courseName, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        }
        // Check if firstName and courseName are provided
        else if (StringUtils.hasText(firstName) && StringUtils.hasText(courseName)) {
            studentsPage = stdService.retrieveStudentsByFirstNameAndCourseName(firstName, courseName, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        }
        // Check if lastName and courseName are provided
        else if (StringUtils.hasText(lastName) && StringUtils.hasText(courseName)) {
            studentsPage = stdService.retrieveStudentsByLastNameAndCourseName(lastName, courseName, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        }
        // Check if only courseName is provided
        else if (StringUtils.hasText(courseName)) {
            studentsPage = stdService.retrieveStudentsByCourseName(courseName, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        }
        // Check if firstName and lastName are provided
        else if (StringUtils.hasText(firstName) && StringUtils.hasText(lastName)) {
            studentsPage = stdService.retrieveStudentsByFirstNameAndLastName(firstName, lastName, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        }
        // Check if only firstName is provided
        else if (StringUtils.hasText(firstName)) {
            studentsPage = stdService.retrieveStudentsByFirstName(firstName, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        }
        // Check if only lastName is provided
        else if (StringUtils.hasText(lastName)) {
            studentsPage = stdService.retrieveStudentsByLastName(lastName, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        }
        // If nothing is provided, retrieve all students paginated
        else {
            studentsPage = stdService.retrieveAllStudentsWithCoursesPaginated(PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        }
        List<Course> courses = crService.retrieveAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("students", studentsPage.getContent());
        model.addAttribute("currentPage", studentsPage.getNumber());
        model.addAttribute("totalPages", studentsPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("courseName", courseName);
        return "students";
    }

    /**
     * Mapping for listing courses.
     *
     * @param model      The model to which attributes are added.
     * @param page       The page number for pagination.
     * @param sortField  The field to sort by.
     * @param sortOrder  The sorting order (asc/desc).
     * @param courseName The name of the course to filter by.
     * @param department The department of the course to filter by.
     * @return The name of the view to display the list of courses.
     */
    @GetMapping("/courses")
    public String listCourses(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "courseName") String sortField,
                              @RequestParam(defaultValue = "asc") String sortOrder,
                              @RequestParam(required = false) String courseName,
                              @RequestParam(required = false, name = "courseDepartment") String department) {
        Page<Course> coursesPage;

        if (StringUtils.hasText(courseName) && StringUtils.hasText(department)) {
            // Both courseName and department are provided, filter by both
            coursesPage = crService.retrieveCoursesByCourseNameAndDepartment(courseName, department, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        } else if (StringUtils.hasText(courseName)) {
            // Only courseName is provided, filter by courseName
            coursesPage = crService.retrieveCoursesByCourseName(courseName, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        } else if (StringUtils.hasText(department)) {
            // Only department is provided, filter by department
            coursesPage = crService.retrieveCoursesByDepartment(department, PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        } else {
            // Neither courseName nor department is provided, fetch all courses
            coursesPage = crService.retrieveAllCoursesPaginated(PageRequest.of(page, 5, Sort.by(Sort.Direction.fromString(sortOrder), sortField)));
        }

        List<Course> allCourses = crService.retrieveAllCourses();
        List<String> departments = allCourses.stream().map(Course::getDepartment).distinct().collect(Collectors.toList());

        model.addAttribute("courses", coursesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursesPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("courseName", courseName);
        model.addAttribute("department", department);
        model.addAttribute("departments", departments);
        return "courses";
    }


     /**
     * Mapping for creating a new student.
     *
     * @param model The model to which attributes are added.
     * @return The name of the view to create a new student.
     */
    @GetMapping("/students/new")
    public String createStudent(Model model) {
        Student student = new Student();
        List<Course> courses = crService.retrieveAllCourses();
        model.addAttribute("student", student);
        model.addAttribute("courses", courses);
        return "new_student";
    }

    /**
     * Mapping for creating a new course.
     *
     * @param model The model to which attributes are added.
     * @return The name of the view to create a new course.
     */
    @GetMapping("/courses/new")
    public String createCourse(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "new_course";
    }

     /**
     * Mapping for saving a student.
     *
     * @param student   The student object to be saved.
     * @param courseIds The IDs of the courses associated with the student.
     * @return The redirect URL after saving the student.
     */
    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student,
                              @RequestParam("courseIds") List<Long> courseIds) {
        Set<Course> courses = courseIds.stream()
                .map(id -> crService.retrieveCourseById(id))
                .collect(Collectors.toSet());

        Student existingStudent = stdService.findStudentByDetails(student);
        if (existingStudent != null) {
            existingStudent.getCourses().addAll(courses);
            stdService.updateStudent(existingStudent);
        } else {
            student.setCourses(courses);
            stdService.saveStudent(student);
        }

        return "redirect:/students";
    }

    /**
     * Mapping for saving a course.
     * Creating an existing course with different department will force the existing course's department to be replaced.
     *
     * @param course The course object to be saved.
     * @return The redirect URL after saving the course.
     */
    @PostMapping("/courses")
    public String saveCourse(@ModelAttribute("course") Course course) {
        Course existingCourse = crService.findCourseByDetails(course.getCourseName());
        if (existingCourse != null) {
            existingCourse.setDepartment(course.getDepartment());
            crService.updateCourse(existingCourse);
        } else {
            crService.saveCourse(course);
        }
        return "redirect:/courses";
    }

    /**
     * Mapping for displaying the form to update student information.
     *
     * @param id    The ID of the student to be updated.
     * @param model The model object to add attributes.
     * @return The view name for updating student information.
     */
    @GetMapping("/students/update/{id}")
    public String updateStudentForm(@PathVariable Long id, Model model) {
        Student student = stdService.retrieveStudentById(id);
        List<Course> courses = crService.retrieveAllCourses();
        model.addAttribute("student", student);
        model.addAttribute("courses", courses);
        return "update_student";
    }

    /**
    * Mapping for displaying the form to update course information.
    *
    * @param id The ID of the course to be updated.
    * @param model The model to be used for passing data to the view.
    * @return The view name for updating the course.
     */
    @GetMapping("/courses/update/{id}")
    public String updateCourseForm(@PathVariable Long id, Model model) {
        model.addAttribute("course", crService.retrieveCourseById(id));
        return "update_course";
    }

    
    /**
     * Mapping for updating a student.
     *
     * @param id         The ID of the student to be updated.
     * @param student    The updated student object.
     * @param courseIds  The IDs of the courses associated with the student.
     * @return The redirect URL after updating the student.
     */
    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("student") Student student,
                                @RequestParam("courseIds") List<Long> courseIds) {
        Student existingStudent = stdService.retrieveStudentById(id);
        if (existingStudent != null) {
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());

            Set<Course> courses = courseIds.stream()
                    .map(crService::retrieveCourseById)
                    .collect(Collectors.toSet());
            existingStudent.setCourses(courses);

            stdService.updateStudent(existingStudent);
        }
        return "redirect:/students";
    }

     /**
     * Mapping for submitting updated course information.
     *
     * @param id      The ID of the course to be updated.
     * @param course  The updated course object.
     * @return The redirect URL after updating the course.
     */
    @PostMapping("/courses/update/{id}")
    public String updateCourse(@PathVariable Long id,
                               @ModelAttribute("course") Course course) {
        Course existingCourse = crService.retrieveCourseById(id);
        if (existingCourse != null) {
            existingCourse.setCourseName(course.getCourseName());
            crService.updateCourse(existingCourse);
        }
        return "redirect:/courses";
    }

     /**
     * Mapping for deleting a student.
     *
     * @param id The ID of the student to be deleted.
     * @return The redirect URL after deleting the student.
     */
    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id){
        stdService.deleteStudentById(id);
        return "redirect:/students";
    }

    /**
     * Mapping for deleting a course.
     *
     * @param id The ID of the course to be deleted.
     * @return The redirect URL after deleting the course.
     */
    @GetMapping("/courses/{id}")
    public String deleteCourse(@PathVariable Long id) {
        crService.updateStudentsWhenCourseDeleted(id);
        crService.deleteCourseById(id);
        return "redirect:/courses";
    }

    /**
     * Mapping for retrieving students by course name.
     *
     * @param courseName The name of the course to retrieve students for.
     * @param model      The model object to add attributes.
     * @return The view name for displaying the students.
     */
    @GetMapping("/students/byCourse/{courseName}")
    public String retrieveStudentsByCourseName(@PathVariable String courseName, Model model) {
        List<Student> students = stdService.findStudentByCourseName(courseName);
        model.addAttribute("students", students);
        return "students";
    }
}