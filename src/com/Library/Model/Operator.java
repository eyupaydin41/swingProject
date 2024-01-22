package com.Library.Model;

import com.Library.Helper.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Operator extends User{

    public Operator() {
    }

    public Operator(int ID, String first_name, String last_name, String username, String password, String user_type) {
        super(ID, first_name, last_name, username, password, user_type);
    }


    public static ArrayList<Operator> getOperatorList() {
        ArrayList<Operator> operatorList = new ArrayList<>();
        Operator obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.user WHERE user_type = 'operator'");
            while(rs.next()) {
                obj = new Operator(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_type")
                );
                operatorList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return operatorList;
    }
}
