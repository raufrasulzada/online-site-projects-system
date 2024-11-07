package as1.spring.boot.jpa.app.raufrasulzada.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a course entity in the system.
 */
@Entity
@Data
@Table(name = "courses")

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coursename", nullable = false, unique = true)
    private String courseName;

    @Column(name = "department", nullable = false)
    private  String department;

    /**
     * Defines the many-to-many relationship with students.
     * <p>
     * This field indicates the students enrolled in this course.
     */
    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Student> students = new HashSet<>();


    /**
     * Default constructor for Course.
     */
    public Course() {

    }

    /**
     * Constructor for Course with parameters.
     *
     * @param courseName The name of the course.
     * @param department The department of the course.
     */
    public Course(String courseName, String department) {
        super();
        this.courseName = courseName;
        this.department = department;
    }

     /**
     * Override hashCode method to ensure consistency in collections.
     *
     * @return The hash code value for this course.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
