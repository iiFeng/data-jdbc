package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    static final String jdbcUrl = "jdbc:mysql://localhost:port/learnjdbc?useSSL=false&characterEncoding=utf8";
    static final String jdbcUsername = "root";
    static final String jdbcPassword = "123456";

    public List<Student> getStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM students")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(extraRow(rs));
                    }
                }
            }
        }
        return list;
    }

    public Student getStudentByName(String name) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE name=?")) {
                ps.setObject(1, name);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        if (rs.getString("name").equals(name)) {
                            return extraRow(rs);
                        }
                    }
                    throw new RuntimeException("student not found by name.");
                }
            }
        }
    }

    public Student getStudentById(long id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE id=?")) {
                ps.setObject(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        if (rs.getLong("id") == id) {
                            return extraRow(rs);
                        }
                    }
                    throw new RuntimeException("student not found by id.");
                }
            }
        }
    }

    public Student updateStudent(Student student) throws SQLException {
        getStudentById(student.getId());
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE students SET name=? WHERE id=?")) {
                ps.setObject(1, student.getName());
                ps.setObject(2, student.getId());
                int n = ps.executeUpdate();
                if (n > 0) {
                    System.out.println(n + " updated.");
                    return student;
                }
                throw new RuntimeException("update failed.");
            }
        }
    }

    public Student insertStudent(String name, Boolean gender, int grade, int score) throws SQLException {
        long id = 0;
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO students(name,gender,grade,score) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setObject(1, name);
                ps.setObject(2, gender);
                ps.setObject(3, grade);
                ps.setObject(4, score);
                int n = ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                        System.out.println(n + " inserted. id = " + id);
                        return new Student(id, name, gender, grade, score);
                    }
                    throw new RuntimeException("insert failed.");
                }
            }
        }
    }

    public void deleteStudentByName(String name) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM students where name=?")) {
                ps.setObject(1, name);
                int n = ps.executeUpdate();
                if (n > 0) {
                    System.out.println(n + " deleted.");
                } else {
                    throw new RuntimeException("deleted failed.");
                }
            }
        }
    }

    public Student extraRow(ResultSet rs) throws SQLException {
        Student std = new Student();
        std.setId(rs.getLong("id"));
        std.setName(rs.getString("name"));
        std.setGender(rs.getBoolean("gender"));
        std.setGrade(rs.getInt("grade"));
        std.setScore(rs.getInt("score"));
        return std;
    }
}
