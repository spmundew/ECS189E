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
public class TestStudent {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();

        this.admin.createClass("ECS20", 2017, "Rogaway", 1);
        this.admin.createClass("ECS20", 2020, "Rogaway", 1);
        this.admin.createClass("ECS40", 2017, "Sean", 2);
        this.admin.createClass("ECS60", 2017, "Sean", 2);
        this.instructor.addHomework("Rogaway", "ECS20", 2017, "PS1", "Computational complexity.");
        this.instructor.addHomework("Rogaway", "ECS20", 2017, "PS2", "Set theory.");
        this.instructor.addHomework("Rogaway", "ECS20", 2017, "PS3", "Graph theory.");
        this.instructor.addHomework("Sean", "ECS40", 2017, "P1", "P1");
        this.instructor.addHomework("Sean", "ECS40", 2017, "P2", "P2");
        this.instructor.addHomework("Sean", "ECS60", 2017, "P1", "P1");
        this.instructor.addHomework("Sean", "ECS60", 2017, "P2", "P2");
    }



    /*** testRegisterForClass ***/

    @Test
    public void testRegisterForClass1() {
        this.student.registerForClass("David", "ECS20", 2017);
        // successfully registered for ECS20, should pass
        assertTrue(this.student.isRegisteredFor("David", "ECS20", 2017));
    }

    @Test
    public void testRegisterForClass2() {
        this.student.registerForClass("David", "ECS20", 2018);
        // class doesn't exist, should fail
        assertFalse(this.student.isRegisteredFor("David", "ECS20", 2018));
    }

    @Test
    public void testRegisterForClass3() {
        this.student.registerForClass("David", "ECS20", 2017);
        // class doesn't exist, should fail
        assertFalse(this.student.isRegisteredFor("David", "ECS20", 2018));
    }

    @Test
    public void testRegisterForClass4() {
        // student hasn't registered, should fail
        assertFalse(this.student.isRegisteredFor("David", "ECS20", 2018));
    }

    @Test
    public void testRegisterForClass5a() {
        this.student.registerForClass("David", "ECS20", 2017);
        this.student.registerForClass("Sahana", "ECS20", 2017);
        // successfully registered for ECS20, should pass
        assertTrue(this.student.isRegisteredFor("David", "ECS20", 2017));
    }

    @Test
    public void testRegisterForClass5b() {
        this.student.registerForClass("David", "ECS20", 2017);
        this.student.registerForClass("Sahana", "ECS20", 2017);
        // successfully registered for ECS20, should pass
        assertFalse(this.student.isRegisteredFor("Sahana", "ECS20", 2017));
    }



    /*** testDropClass ***/

    @Test
    public void testDropClass1() {
        this.student.registerForClass("David", "ECS20", 2017);
        this.student.dropClass("David", "ECS20", 2017);
        // successfully dropped ECS20, should pass
        assertFalse(this.student.isRegisteredFor("David", "ECS20", 2017));
    }

    @Test
    public void testDropClass2() {
        this.student.registerForClass("David", "ECS20", 2017);
        this.student.registerForClass("David", "ECS40", 2017);
        this.student.registerForClass("David", "ECS60", 2017);
        this.student.dropClass("David", "ECS20", 2017);
        // successfully dropped ECS20 but still in ECS60, should pass
        assertTrue(this.student.isRegisteredFor("David", "ECS60", 2017));
    }



    /*** testSubmitHomework ***/

    @Test
    public void testSubmitHomework1() {
        this.student.registerForClass("David", "ECS20", 2017);
        this.student.submitHomework("David", "PS1", "Solution", "ECS20", 2017);
        // submitted HW sucessfully, should pass
        assertTrue(this.student.hasSubmitted("David", "PS1", "ECS20", 2017));
    }

    @Test
    public void testSubmitHomework2() {
        this.student.registerForClass("David", "ECS20", 2020);
        this.student.submitHomework("David", "PS1", "Solution", "ECS20", 2017);
        // not registered, should fail
        assertFalse(this.student.hasSubmitted("David", "PS1", "ECS20", 2017));
    }

    @Test
    public void testSubmitHomework3() {
        this.student.registerForClass("David", "ECS20", 2020);
        this.student.submitHomework("David", "PS1", "Solution", "ECS20", 2020);
        // can't submit HW for future class, should fail
        assertFalse(this.student.hasSubmitted("David", "PS1", "ECS20", 2020));
    }

    @Test
    public void testSubmitHomework4() {
        this.student.registerForClass("David", "ECS20", 2017);
        this.student.registerForClass("Sahana", "ECS20", 2017);
        this.student.submitHomework("Sahana", "PS1", "Solution", "ECS20", 2017);
        // Sahana submitted, not David, should fail
        assertFalse(this.student.hasSubmitted("David", "PS1", "ECS20", 2017));
    }

    @Test
    public void testSubmitHomework5() {
        this.student.registerForClass("David", "ECS40", 2017);
        this.student.registerForClass("David", "ECS60", 2017);
        this.student.submitHomework("David", "P1", "Solution", "ECS40", 2017);
        // submitted HW sucessfully, should pass
        assertTrue(this.student.hasSubmitted("David", "PS1", "ECS40", 2017));
    }

    @Test
    public void testSubmitHomework6() {
        this.student.registerForClass("David", "ECS40", 2017);
        this.student.registerForClass("David", "ECS60", 2017);
        this.student.submitHomework("David", "P1", "Solution", "ECS40", 2017);
        // submitted HW for 40 not 60, should fail
        assertFalse(this.student.hasSubmitted("David", "PS1", "ECS60", 2017));
    }

    @Test
    public void testSubmitHomework7() {
        this.student.registerForClass("David", "ECS20", 2017);
        this.student.submitHomework("David", "PS1", "Solution", "ECS30", 2017);
        // submitted HW to wrong class, should fail
        assertFalse(this.student.hasSubmitted("David", "PS1", "ECS20", 2017));
    }



    /*** Old Test Cases, but should be good ***/

    @Test
    public void testRestisteredforPastClass()
    {
        this.student.registerForClass("David", "ECS 160", 2016);
        assertFalse(this.student.isRegisteredFor("David", "ECS 160", 2016));
    }

    @Test
    public void testRegisterForNotRealClass()
    {
        this.admin.createClass("ECS 165", 2017, "Instructor", 15);
        this.student.registerForClass("David", "ECS 160", 2017);
        assertFalse(this.student.isRegisteredFor("David", "ECS 165", 2017));
    }

    @Test
    public void testOverEnrollment()
    {
        this.admin.createClass("ECS 160", 2017, "Instructor", 2);
        this.student.registerForClass("David", "ECS 160", 2017);
        this.student.registerForClass("Anny", "ECS 160", 2017);
        this.student.registerForClass("Brandon", "ECS 160", 2017);
        assertFalse(this.student.isRegisteredFor("Brandon", "ECS 160", 2017));
    }

    @Test
    public void testDropClass()
    {
        this.admin.createClass("ECS 160", 2017, "Instructor", 15);
        this.student.registerForClass("David", "ECS 160", 2017);
        this.student.dropClass("David", "ECS 160", 2017);
        assertFalse(this.student.isRegisteredFor("David", "ECS 160", 2017));
    }

    @Test
    public void testDropWrongClass()
    {
        this.admin.createClass("ECS 160", 2017, "Instructor", 15);
        this.student.registerForClass("David", "ECS 160", 2017);
        this.student.dropClass("David", "ECS 154B", 2017);
        assertTrue(this.student.isRegisteredFor("David", "ECS 160", 2017));
    }

    @Test
    public void testDropClassNotRegistered()
    {
        this.admin.createClass("ECS 160", 2017, "Instructor", 2);
        this.student.registerForClass("David", "ECS 160", 2017);
        this.student.registerForClass("Anny", "ECS 160", 2017);
        this.student.dropClass("Brandon", "ECS 160", 2017);
        this.student.registerForClass("Sahana", "ECS 160", 2017);
        assertFalse(this.student.isRegisteredFor("Sahana", "ECS 160", 2017));
    }

    @Test
    public void testSubmitNotRealHomework1()
    {
        this.admin.createClass("ECS 160", 2017, "Instructor", 2);
        this.student.registerForClass("Shunxu", "ECS 160", 2017);
        this.instructor.addHomework("Instructor", "ECS 160", 2017, "Warcraft", "Build");
        this.student.submitHomework("Shunxu", "Week1Goals", "Optimal", "ECS 160", 2017);
        assertFalse(this.student.hasSubmitted("Shunxu", "Warcraft", "ECS 160", 2017));
    }

    @Test
    public void testSubmitNotRealHomework2()
    {
        this.admin.createClass("ECS 160", 2017, "Instructor", 2);
        this.student.registerForClass("Shunxu", "ECS 160", 2017);
        this.instructor.addHomework("Instructor", "ECS 160", 2017, "Warcraft", "Build");
        this.student.submitHomework("Shunxu", "Week1Goals", "Optimal", "ECS 160", 2017);
        assertFalse(this.student.hasSubmitted("Shunxu", "Week1Goals", "ECS 160", 2017));
    }

    @Test
    public void testSubmitHomeworkInClassYouNotIn()
    {
        this.admin.createClass("ECS 160", 2017, "Instructor", 2);
        this.instructor.addHomework("Instructor", "ECS 160", 2017, "Warcraft", "Build");
        this.student.submitHomework("Shunxu", "Warcraft", "Build", "ECS 160", 2017);
        assertFalse(this.student.hasSubmitted("Shunxu", "Warcraft", "ECS 160", 2017));
    }

    @Test
    public void testSubmitHomeworkInFutureClass()
    {
        //this.admin.createClass("ECS 160", 2018, "Instructor", 2);
        this.student.registerForClass("Shunxu", "ECS 160", 2018);
        this.instructor.addHomework("Instructor", "ECS 160", 2018, "Warcraft", "Build");
        this.student.submitHomework("Shunxu", "Warcraft", "Build", "ECS 160", 2018);
        assertFalse(this.student.hasSubmitted("Shunxu", "Warcraft", "ECS 160", 2018));
    }
}
