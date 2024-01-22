package com.Library.Model;

import com.Library.Helper.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Student extends User{

    public Student() {
    }

    public Student(int ID, String first_name, String last_name, String username, String password, String user_type) {
        super(ID, first_name, last_name, username, password, user_type);
    }

    public static ArrayList<Student> getStudentList() {
        ArrayList<Student> studentList = new ArrayList<>();
        Student obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.user WHERE user_type = 'student'");
            while(rs.next()) {
                obj = new Student(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_type")
                );
                studentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentList;
    }

}
