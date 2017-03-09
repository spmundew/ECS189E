import api.IAdmin;
import api.core.impl.Admin;
import api.IInstructor;
import api.core.impl.Instructor;
import api.IStudent;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sahana on 3/8/17.
 */
public class TestInstructor
{
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }



    /*** testAddHomework ***/

    @Test
    public void testAddHomework1() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1", "Hello world.");
        // homework added, should pass
        assertTrue(this.instructor.homeworkExists("Test", 2017, "HW1"));
    }

    @Test
    public void testAddHomework2a() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        this.instructor.addHomework("Instructor", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor", "Test2", 2017, "HW2", "Hello world.");
        // HW1 added to Test1, should pass
        assertTrue(this.instructor.homeworkExists("Test1", 2017, "HW1"));
    }

    @Test
    public void testAddHomework2b() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        this.instructor.addHomework("Instructor", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor", "Test2", 2017, "HW2", "Hello world.");
        // HW1 added to Test1, not HW2, should fail
        assertFalse(this.instructor.homeworkExists("Test1", 2017, "HW2"));
    }

    @Test
    public void testAddHomework2c() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        this.instructor.addHomework("Instructor", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor", "Test2", 2017, "HW2", "Hello world.");
        // HW2 added to Test2, should pass
        assertTrue(this.instructor.homeworkExists("Test2", 2017, "HW2"));
    }

    @Test
    public void testAddHomework2d() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        this.instructor.addHomework("Instructor", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor", "Test2", 2017, "HW2", "Hello world.");
        // HW2 added to Test2, not HW1, should fail
        assertFalse(this.instructor.homeworkExists("Test2", 2017, "HW1"));
    }

    @Test
    public void testAddHomework3() {
        this.admin.createClass("Test1", 2017, "Instructor1", 10);
        this.admin.createClass("Test2", 2017, "Instructor2", 13);
        this.instructor.addHomework("Instructor2", "Test1", 2017, "HW1", "Hello world.");
        // homework added with incorrect instructor, should fail
        assertFalse(this.instructor.homeworkExists("Test1", 2017, "HW1"));
    }

    @Test
    public void testAddHomework4a() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.instructor.addHomework("Instructor", "Test", 2018, "HW1", "Hello world.");
        // homework added with incorrect year, should fail
        assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
    }

    @Test
    public void testAddHomework4b() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.instructor.addHomework("Instructor", "Test", 2018, "HW1", "Hello world.");
        // homework added with incorrect year, should fail
        assertFalse(this.instructor.homeworkExists("Test", 2018, "HW1"));
    }

    @Test
    public void testAddHomework5a() {
        this.admin.createClass("Test1", 2017, "Instructor1", 10);
        this.admin.createClass("Test2", 2017, "Instructor2", 10);
        this.instructor.addHomework("Instructor2", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor1", "Test2", 2017, "HW2", "Hello world.");
        // instructor can't add HW to class he's not assigned, should fail
        assertFalse(this.instructor.homeworkExists("Test1", 2017, "HW1"));
    }

    @Test
    public void testAddHomework5b() {
        this.admin.createClass("Test1", 2017, "Instructor1", 10);
        this.admin.createClass("Test2", 2017, "Instructor2", 10);
        this.instructor.addHomework("Instructor2", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor1", "Test2", 2017, "HW2", "Hello world.");
        // instructor can't add HW to class he's not assigned, should fail
        assertFalse(this.instructor.homeworkExists("Test2", 2017, "HW2"));
    }

    @Test
    public void testAddHomework6() {
        this.instructor.addHomework("Instructor1", "Test2", 2017, "HW2", "Hello world.");
        // instructor can't add HW to non existant class, should fail
        assertFalse(this.instructor.homeworkExists("Test2", 2017, "HW2"));
    }



    /*** testAssignGrade ***/

    @Test
    public void testAssignGrade1() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1", "Hello world.");
        this.student.submitHomework("Student", "HW1", "Solution", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
        // student received 100, should pass
        assertTrue(100 == this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade2() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1", "Hello world.");
        this.student.submitHomework("Student", "HW1", "Solution", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", -1);
        // student received negative grade, should fail
        assertNull(this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade3() {
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1", "Hello world.");
        this.student.submitHomework("Student", "HW1", "Solution", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
        // class does not exist, should fail
        assertNull(this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade4() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1", "Hello world.");
        this.student.submitHomework("Student", "HW1", "Solution", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
        // student not registered, should fail
        assertNull(this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade5() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.student.registerForClass("Student", "Test", 2017);
        this.student.submitHomework("Student", "HW1", "Solution", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
        // HW1 not created, should fail
        assertNull(this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade6() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1", "Hello world.");
        this.student.submitHomework("Student", "HW1", "Solution", "Test", 2017);
        // grade not assigned, should fail
        assertNull(this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade7() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1", "Hello world.");
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
        // HW not submitted, should pass
        assertNull(this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade8() {
        // nothing exists, should pass
        assertNull(this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade9a() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        this.student.registerForClass("Student", "Test1", 2017);
        this.student.registerForClass("Student", "Test2", 2017);
        this.instructor.addHomework("Instructor", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor", "Test2", 2017, "HW1", "Hello world.");
        this.student.submitHomework("Student", "HW1", "Solution", "Test1", 2017);
        this.student.submitHomework("Student", "HW1", "Solution", "Test2", 2017);
        this.instructor.assignGrade("Instructor", "Test1", 2017, "HW1", "Student", 100);
        // student received 100, should pass
        assertTrue(100 == this.instructor.getGrade("Test1", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade9b() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        this.student.registerForClass("Student", "Test1", 2017);
        this.student.registerForClass("Student", "Test2", 2017);
        this.instructor.addHomework("Instructor", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor", "Test2", 2017, "HW1", "Hello world.");
        this.student.submitHomework("Student", "HW1", "Solution", "Test1", 2017);
        this.student.submitHomework("Student", "HW1", "Solution", "Test2", 2017);
        this.instructor.assignGrade("Instructor", "Test1", 2017, "HW1", "Student", 100);
        // student received no grade, should pass
        assertNull(this.instructor.getGrade("Test2", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade9c() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        this.student.registerForClass("Student", "Test1", 2017);
        this.student.registerForClass("Student", "Test2", 2017);
        this.instructor.addHomework("Instructor", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor", "Test2", 2017, "HW1", "Hello world.");
        this.student.submitHomework("Student", "HW1", "Solution", "Test1", 2017);
        this.student.submitHomework("Student", "HW1", "Solution", "Test2", 2017);
        this.instructor.assignGrade("Instructor", "Test1", 2017, "HW1", "Student", 100);
        this.instructor.assignGrade("Instructor", "Test2", 2017, "HW1", "Student", 100);
        // student received 100, should pass
        assertTrue(100 == this.instructor.getGrade("Test1", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade9d() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        this.student.registerForClass("Student", "Test1", 2017);
        this.student.registerForClass("Student", "Test2", 2017);
        this.instructor.addHomework("Instructor", "Test1", 2017, "HW1", "Hello world.");
        this.instructor.addHomework("Instructor", "Test2", 2017, "HW1", "Hello world.");
        this.student.submitHomework("Student", "HW1", "Solution", "Test1", 2017);
        this.student.submitHomework("Student", "HW1", "Solution", "Test2", 2017);
        this.instructor.assignGrade("Instructor", "Test1", 2017, "HW1", "Student", 100);
        this.instructor.assignGrade("Instructor", "Test2", 2017, "HW1", "Student", 100);
        // student received 100, should pass
        assertTrue(100 == this.instructor.getGrade("Test2", 2017, "HW1", "Student"));
    }

    @Test
    public void testAssignGrade10() {
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
        // just assigning grade, should fail
        assertNull(this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }

}