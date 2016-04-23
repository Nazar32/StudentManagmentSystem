package Logic;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.List;


public class ManagementSystem {

    private List<Group> groups;
    private Collection<Student> students;
    private static Connection con;


    private static ManagementSystem instance;

    public static synchronized ManagementSystem getInstance() throws Exception {

        if (instance == null)
            instance = new ManagementSystem();

        return instance;

    }

    private ManagementSystem() throws Exception {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/students";
            con = (Connection) DriverManager.getConnection(url, "root", "root");
        }

        catch (ClassNotFoundException e) {
            throw new Exception(e);
        }

        catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Group> getGroups() throws SQLException {

        List<Group> groups = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = (Statement) con.createStatement();
            rs = (ResultSet) stmt.executeQuery("SELECT group_id, groupName, curator, speciality FROM groups");
            while (rs.next()) {
                Group gr = new Group();
                gr.setGroupId(rs.getInt(1));
                gr.setNameGroup(rs.getString(2));
                gr.setCurator(rs.getString(3));
                gr.setSpeciality(rs.getString(4));

                groups.add(gr);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return groups;
    }

    public Collection<Student> getAllStudents() throws SQLException {
        Collection<Student> students = new ArrayList<Student>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = (Statement) con.createStatement();
            rs = (ResultSet) stmt.executeQuery(
                    "SELECT student_id, firstName, patronymic, surName, " +
                            "sex, dateOfBirth, group_id, educationYear FROM students " +
                            "ORDER BY surName, firstName, patronymic");
            while (rs.next()) {
                Student st = new Student(rs);
                students.add(st);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return students;
    }

    public Collection<Student> getStudentsFromGroup(Group group, int year) throws SQLException {
        Collection<Student> students = new ArrayList<Student>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = (PreparedStatement) con.prepareStatement(
                    "SELECT student_id, firstName, patronymic, surName, " +
                            "sex, dateOfBirth, group_id, educationYear FROM students " +
                            "WHERE group_id=? AND educationYear=? " +
                            "ORDER BY surName, firstName, patronymic");
            stmt.setInt(1, group.getGroupId());
            stmt.setInt(2, year);
            rs = (ResultSet) stmt.executeQuery();
            while (rs.next()) {
                Student st = new Student(rs);

                students.add(st);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return students;
    }

    public void moveStudentsToGroup(Group oldGroup, int oldYear, Group newGroup, int newYear) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = (PreparedStatement) con.prepareStatement(
                    "UPDATE students SET group_id=?, educationYear=? " +
                            "WHERE group_id=? AND educationYear=?");
            stmt.setInt(1, newGroup.getGroupId());
            stmt.setInt(2, newYear);
            stmt.setInt(3, oldGroup.getGroupId());
            stmt.setInt(4, oldYear);
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void removeStudentsFromGroup(Group group, int year) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = (PreparedStatement) con.prepareStatement(
                    "DELETE FROM students WHERE group_id=? AND educationYear=?");
            stmt.setInt(1, group.getGroupId());
            stmt.setInt(2, year);
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void insertStudent(Student student) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = (PreparedStatement) con.prepareStatement(
                    "INSERT INTO students " +
                            "(firstName, patronymic, surName, sex, dateOfBirth, group_id, educationYear) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getPatronymic());
            stmt.setString(3, student.getSurName());
            stmt.setString(4, new String(new char[]{student.getSex()}));
            stmt.setDate(5, new Date(student.getDateOfBirth().getTime()));
            stmt.setInt(6, student.getGroupId());
            stmt.setInt(7, student.getEducationYear());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void updateStudent(Student student) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = (PreparedStatement) con.prepareStatement(
                    "UPDATE students SET " +
                            "firstName=?, patronymic=?, surName=?, " +
                            "sex=?, dateOfBirth=?, group_id=?, educationYear=?" +
                            "WHERE student_id=?");
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getPatronymic());
            stmt.setString(3, student.getSurName());
            stmt.setString(4, new String(new char[]{student.getSex()}));
            stmt.setDate(5, new Date(student.getDateOfBirth().getTime()));
            stmt.setInt(6, student.getGroupId());
            stmt.setInt(7, student.getEducationYear());
            stmt.setInt(8, student.getStudentId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void deleteStudent(Student student) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = (PreparedStatement) con.prepareStatement(
                    "DELETE FROM students WHERE student_id=?");
            stmt.setInt(1, student.getStudentId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static void printString(Object s) {

        //System.out.println(s.toString());
        try {
            System.out.println(new String(s.toString().getBytes("windows-1251"), "windows-1252"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void printString() {

        System.out.println();
    }





}
