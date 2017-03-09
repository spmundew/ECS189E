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
public class TestAdmin {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup()
    {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    /*** testMakeClass ***/

    @Test
    public void testMakeClass1a() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass1b() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        String str = "Instructor";
        assertTrue(str.equals(this.admin.getClassInstructor("Test", 2017)));
    }

    @Test
    public void testMakeClass1c() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(15 == this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        // create past course, should fail
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void testMakeClass3() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        // create course with capacity 0, should fail
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass4() {
        this.admin.createClass("Test", 2017, "Instructor", -1);
        // create course with negative capacity, should fail
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass5() {
        this.admin.createClass("Test", 2016, "Instructor", -1);
        // create course with negative capacity and past year, should fail
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void testMakeClass6a() {
        this.admin.createClass("Test", 2017, "Instructor1", 1);

        this.admin.createClass("Test", 2017, "Instructor2", 2);
        // create duplicate course (unique class/year pair), should fail
        String str = "Instructor2";
        assertTrue(str.equals(this.admin.getClassInstructor("Test", 2017)));
    }

    @Test
    public void testMakeClass6b() {
        this.admin.createClass("Test", 2017, "Instructor1", 1);

        this.admin.createClass("Test", 2017, "Instructor2", 2);
        // create duplicate course (unique class/year pair), should fail
        assertFalse(2 == this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testMakeClass7a() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 13);
        // one instructor teaches two different courses same year, should pass
        assertTrue(this.admin.classExists("Test1", 2017));
    }

    @Test
    public void testMakeClass7b() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 13);
        // one instructor teaches two different courses same year, should pass
        assertTrue(this.admin.classExists("Test2", 2017));
    }

    @Test
    public void testMakeClass7c() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 13);
        // one instructor teaches two different courses same year, should pass
        String str = "Instructor";
        assertTrue(str.equals(this.admin.getClassInstructor("Test1", 2017)));
    }

    @Test
    public void testMakeClass7d() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 13);
        // one instructor teaches two different courses same year, should pass
        String str = "Instructor";
        assertTrue(str.equals(this.admin.getClassInstructor("Test2", 2017)));
    }

    @Test
    public void testMakeClass7e() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 13);
        // one instructor teaches two different courses same year, should pass
        assertTrue(10 == this.admin.getClassCapacity("Test1", 2017));
    }

    @Test
    public void testMakeClass7f() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 13);
        // one instructor teaches two different courses same year, should pass
        assertTrue(13 == this.admin.getClassCapacity("Test2", 2017));
    }

    @Test
    public void testMakeClass8() {
        this.admin.createClass("Test1", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor", 13);
        // creating third class for same instructor, should fail
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017));
    }

    @Test
    public void testMakeClass9a() {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.admin.createClass("Test", 2017, "Instructor2", 13);
        // Two instructors for same course, should fail
        String str = "Instructor2";
        assertFalse(str.equals(this.admin.getClassInstructor("Test", 2017)));
    }

    @Test
    public void testMakeClass9b() {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.admin.createClass("Test", 2017, "Instructor2", 13);
        // Two instructors for same course, should fail
        assertFalse(13 == this.admin.getClassCapacity("Test", 2017));
    }

    /*** testChangeCapacity ***/

    @Test
    public void testChangeCapacity1() {
        this.admin.createClass("Test", 2017, "Instructor1", 1);
        this.admin.changeCapacity("Test", 2017, 2);
        // changed course capacity to 2, should pass
        assertTrue(2 == this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacity2() {
        this.admin.createClass("Test", 2017, "Instructor1", 1);
        this.admin.changeCapacity("Test", 2017, 0);
        // changed course capacity to 0, should fail
        assertFalse(0 == this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacity3() {
        this.admin.createClass("Test", 2017, "Instructor1", 1);
        this.admin.changeCapacity("Test", 2017, -1);
        // changed course capacity to -1, should fail
        assertFalse(-1 == this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacity4a() {
        this.admin.changeCapacity("Test", 2017, 1337);
        // changed course capacity of a class that doesn't exist, should fail
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testChangeCapacity4b() {
        this.admin.changeCapacity("Test", 2017, 1337);
        // changed course capacity of a class that doesn't exist, should fail
        assertFalse(1337 == this.admin.getClassCapacity("Test", 2017));
    }


    @Test
    public void testChangeCapacity5() {
        this.admin.createClass("ECS 160", 2017, "Instructor", 4);
        this.student.registerForClass("David", "ECS 160", 2017);
        this.student.registerForClass("Anny", "ECS 160", 2017);
        this.admin.changeCapacity("ECS 160", 2017, 2);
        assertTrue(2 == this.admin.getClassCapacity("ECS 160", 2017));
    }

    @Test
    public void testChangeCapacity5a() {
        this.admin.createClass("ECS 160", 2017, "Instructor", 4);
        this.student.registerForClass("David", "ECS 160", 2017);
        this.student.registerForClass("Anny", "ECS 160", 2017);
        this.admin.changeCapacity("ECS 160", 2017, 1);
        assertFalse(1 == this.admin.getClassCapacity("ECS 160", 2017));
    }


}
