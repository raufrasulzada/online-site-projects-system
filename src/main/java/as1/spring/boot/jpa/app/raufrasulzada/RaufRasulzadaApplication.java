package as1.spring.boot.jpa.app.raufrasulzada;

import as1.spring.boot.jpa.app.raufrasulzada.Entities.Course;
import as1.spring.boot.jpa.app.raufrasulzada.Entities.Student;
import as1.spring.boot.jpa.app.raufrasulzada.Repositories.StudentRepo;
import as1.spring.boot.jpa.app.raufrasulzada.Repositories.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication
public class RaufRasulzadaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RaufRasulzadaApplication.class, args);
	}

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private CourseRepo courseRepo;
	
	/**
     * This method is executed after the Spring Boot application context is loaded.
     * It is responsible for inserting sample data into the database.
     *
     * @param args Command-line arguments.
     * @throws Exception An exception that might occur during the execution.
     */
	@Override
	public void run(String... args) throws Exception {
		Student std1 = new Student("First", "StudentFirst");
		studentRepo.save(std1);
		Course course1 = new Course("Calculus I", "Math");
		courseRepo.save(course1);

		Student std2 = new Student("Second", "StudentSecond");
		studentRepo.save(std2);
		Course course2 = new Course("Programming Principles I", "IT");
		courseRepo.save(course2);

		Student std3 = new Student("Third", "StudentThird");
		studentRepo.save(std3);
		Course course3 = new Course("Philosophy", "Humanities");
		courseRepo.save(course3);
	}
}

