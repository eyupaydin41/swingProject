package com.Library.View;

import com.Library.Helper.Config;
import com.Library.Helper.Helper;
import com.Library.Model.Operator;
import com.Library.Model.Student;
import com.Library.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_loginUsername;
    private JPasswordField fld_loginPassword;
    private JButton btn_login;
    private JButton btn_toSignup;
    private JPanel pnl_login;
    private JLabel lbl_loginUsername;
    private JLabel lbl_loginPassword;

    public LoginGUI() {
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        btn_toSignup.addActionListener(e -> {
            setVisible(false);
            SignupGUI signupGUI = new SignupGUI();
            dispose();

        });

        btn_login.addActionListener(e -> {
            if (!(Helper.isFieldEmpty(fld_loginUsername) || Helper.isFieldEmpty(fld_loginPassword))) {
                boolean isLogin = false;
                for (Operator operator: Operator.getOperatorList()) {
                    if (operator.getUsername().equals(fld_loginUsername.getText()) && Arrays.equals(operator.getPassword().toCharArray(),fld_loginPassword.getPassword())) {
                        isLogin = true;
                        setVisible(false);
                        OperatorGUI operatorGUI = new OperatorGUI(operator);
                        dispose();
                    }
                }
                for (Student student: Student.getStudentList()) {
                    if (student.getUsername().equals(fld_loginUsername.getText()) && Arrays.equals(student.getPassword().toCharArray(),fld_loginPassword.getPassword())) {
                        isLogin = true;
                        setVisible(false);
                        StudentGUI studentGUI = new StudentGUI(student);
                        dispose();
                    }
                }
                if (!isLogin) {
                    Helper.showMsg("Girdiğiniz bilgiler ile eşleşen bir hesap bulunamadı.");
                }
            }
           else {

               if (Helper.isFieldEmpty(fld_loginPassword) && Helper.isFieldEmpty(fld_loginUsername)) {
                   Helper.showMsg("Kullanıcı Adı ve Şifre boş bırakılamaz.");
               } else if (Helper.isFieldEmpty(fld_loginUsername)) {
                   Helper.showMsg("Kullanıcı Adı boş bırakılamaz.");
               } else if (Helper.isFieldEmpty(fld_loginPassword)) {
                   Helper.showMsg("Şifre boş bırakılamaz.");
               }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }

    public JTextField getFld_loginUsername() {
        return fld_loginUsername;
    }
}
