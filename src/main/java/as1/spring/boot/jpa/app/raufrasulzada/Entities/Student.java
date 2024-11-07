package as1.spring.boot.jpa.app.raufrasulzada.Entities;

import lombok.Data;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a student in the system.
 */
@Entity
@Data
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

   /**
     * Many-to-many relationship mapping between students and courses.
     * CascadeType specifies the cascade operations to be applied on the relationship.
     * JoinTable defines the mapping table for the many-to-many relationship.
     * joinColumns defines the column in the mapping table that references the owning entity (Student).
     * inverseJoinColumns defines the column in the mapping table that references the inverse entity (Course).
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "studentcourse",
            joinColumns = @JoinColumn(name = "studentid"),
            inverseJoinColumns = @JoinColumn(name = "courseid")
    )
    private Set<Course> courses = new HashSet<>();

    /**
     * Default constructor for the Student class.
     */
    public Student() {

    }

     /**
     * Parameterized constructor for the Student class.
     * @param firstName The first name of the student.
     * @param lastName The last name of the student.
     */
    public Student(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Override hashCode method to ensure consistency in collections.
     * @return The hash code value for this Student object.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
