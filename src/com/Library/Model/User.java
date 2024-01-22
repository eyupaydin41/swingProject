package com.Library.Model;

import com.Library.Helper.DBConnector;
import com.Library.Helper.Helper;

import javax.swing.text.html.HTMLDocument;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int ID;
    private String first_name;
    private String last_name;
    private String password;
    private String user_type;
    private String username;



    public User() {}

    public User(int ID, String first_name, String last_name, String username, String password, String user_type) {
        this.ID = ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.user_type = user_type;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.user");
            while(rs.next()) {
                obj = new User(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_type")
                );
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static boolean add(String first_name, String last_name, String username, String password,  String user_type) {

        String query = "INSERT INTO public.user (first_name, last_name, username, password, user_type) VALUES (?,?,?,?,?)";

        User findUser = User.getFetch(username);

        if (findUser != null) {
            Helper.showMsg("Bu kullanıcı adı zaten sistemde mevcut.");
            findUser = null;
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,first_name);
            pr.setString(2,last_name);
            pr.setString(3,username);
            pr.setString(4,password);
            pr.setObject(5, user_type, java.sql.Types.OTHER);

            if(user_type.isEmpty()) {
                Helper.showMsg("fill");
                return false;
            }

            int response = pr.executeUpdate();

            if (response == -1) {
                Helper.showMsg("error");
            }

            return response != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM public.user WHERE id = ?";

        User findUser = User.getFetch(id);

        if (findUser == null) {
            Helper.showMsg("Bu ID'e sahip kullanıcı yoktur.");
            return false;
        }

        PreparedStatement pr;
        try {
            pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean update(int id, String firstName, String lastName, String username, String password, String user_type) {
        String query = "UPDATE public.user SET first_name=?,last_name=?,username=?,password=?,user_type=? WHERE id=?";

        User findUser = User.getFetch(username);

        if (findUser != null && findUser.getID() != id) {
            Helper.showMsg("Bu kullanıcı adı zaten sistemde mevcut.");
            findUser = null;
            return false;
        }

        if (!Helper.getUserTypeList().contains(user_type)) {
            Helper.showMsg("Lütfen geçerli bir kullanıcı türü giriniz.");
            return false;
        }



        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, firstName);
            pr.setString(2, lastName);
            pr.setString(3, username);
            pr.setString(4, password);
            pr.setObject(5, user_type, java.sql.Types.OTHER);
            pr.setInt(6, id);

            int response = pr.executeUpdate();

            if (response == -1) {
                Helper.showMsg("error");
            }

            return response != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean search(String first_name,String last_name,String username,String user_type) {

        return true;
    }

    public static ArrayList<User> getSearchUserList(String query) {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                obj = new User(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_type")
                );
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static String searchQuery(String first_name, String last_name, String username, String user_type) {

        String query = "SELECT * FROM public.user WHERE first_name ILIKE '%{{first_name}}%' AND last_name ILIKE '%{{last_name}}%' AND username ILIKE '%{{username}}%'";
        query = query.replace("{{first_name}}", first_name);
        query = query.replace("{{last_name}}", last_name);
        query = query.replace("{{username}}", username);

        if (!user_type.isEmpty()) {
            query += " AND user_type='{{user_type}}'";
            query = query.replace("{{user_type}}", user_type);
        }

        return query;
    }


    public static User getFetch(String username) {
        User obj = null;
        String query = "SELECT * FROM public.user WHERE username = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,username);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                obj = new User(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_type")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    public static User getFetch(int id) {
        User obj = null;
        String query = "SELECT * FROM public.user WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                obj = new User(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_type")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }





}
