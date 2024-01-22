package com.Library.View;

import com.Library.Helper.Config;
import com.Library.Helper.Helper;
import com.Library.Model.User;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class SignupGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_signupFirstname;
    private JTextField fld_signupLastname;
    private JTextField fld_signupUsername;
    private JPasswordField fld_signupPassword;
    private JPasswordField fld_signupVerifyPassword;
    private JButton btn_signup;
    private JLabel lbl_signupFirstname;
    private JLabel lbl_signupLastname;
    private JLabel lbl_signupUsername;
    private JLabel lbl_signupPassword;
    private JLabel lbl_signupVerifyPassword;
    private JLabel btn_alreadyAccount;
    private JLabel lbl_signupFirstnameError;
    private JLabel lbl_signupLastnameError;
    private JLabel lbl_signupUsernameError;
    private JLabel lbl_signupPasswordError;
    private JLabel lbl_signupVerifyPasswordError;


    public SignupGUI() {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(false);

        lbl_signupFirstnameError.setVisible(false);
        lbl_signupLastnameError.setVisible(false);
        lbl_signupUsernameError.setVisible(false);
        lbl_signupPasswordError.setVisible(false);
        lbl_signupVerifyPasswordError.setVisible(false);

        fld_signupPassword.setName("signupPassword");
        fld_signupVerifyPassword.setName("signupVerifyPassword");
        fld_signupLastname.setName("signupLastname");
        fld_signupFirstname.setName("signupFirstname");
        fld_signupUsername.setName("signupUsername");
        lbl_signupFirstnameError.setName("signupFirstnameError");
        lbl_signupLastnameError.setName("signupLastnameError");
        lbl_signupUsernameError.setName("signupUsernameError");
        lbl_signupPasswordError.setName("signupPasswordError");
        lbl_signupVerifyPasswordError.setName("signupVerifyPasswordError");

        Border defaultBorder = fld_signupFirstname.getBorder();

        add(wrapper);
        setSize(500,500);

        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                LoginGUI loginGUI1 = new LoginGUI();
                dispose();
            }
        });

        Color red = new Color(198,23,25);


        btn_signup.addActionListener(e -> {
            JLabel[] labels = {lbl_signupFirstnameError,lbl_signupVerifyPasswordError,lbl_signupPasswordError,lbl_signupUsernameError,lbl_signupLastnameError};
            JTextField[] fields = {fld_signupPassword,fld_signupUsername,fld_signupFirstname,fld_signupLastname,fld_signupVerifyPassword};

            for (JLabel label:labels) {
                label.setVisible(false);
            }
            for (JTextField textField:fields) {
                textField.setBorder(defaultBorder);
            }

            Border emptyFieldBorder = new CompoundBorder(
                    new MatteBorder(1, 1, 1, 1, Color.RED), // Üst kısım
                    new EmptyBorder(3,3,3,3) // Kenar boşluğu
            );

            for (JTextField field:fields) {
                field.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (field.getText().isEmpty()) {
                            Border focusBorder = new CompoundBorder(
                                    new SoftBevelBorder(1, red, red),
                                    new EmptyBorder(3, 3, 3, 3)
                            );
                            field.setBorder(focusBorder);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (field.getText().isEmpty()) {
                            field.setBorder(emptyFieldBorder);
                        }
                    }
                });
            }

            if (Helper.isFieldEmpty(fld_signupFirstname) || Helper.isFieldEmpty(fld_signupLastname) || Helper.isFieldEmpty(fld_signupUsername) || Helper.isFieldEmpty(fld_signupPassword) || Helper.isFieldEmpty(fld_signupVerifyPassword)) {

                for (JTextField field:fields) {
                    if (field.getText().isEmpty()) {
                        for (JLabel label:labels) {

                            if (field.getName().substring(0,field.getName().length()-1).equals(label.getName().substring(0,label.getName().length()-6))) {
                                label.setVisible(true);
                                field.setBorder(emptyFieldBorder);
                            }
                        }

                    }
                }
            } else if (!Arrays.equals(fld_signupPassword.getPassword(),fld_signupVerifyPassword.getPassword())) {
                Helper.showMsg("Şifreler uyuşmuyor.");
            }
            else {
                String firstName = fld_signupFirstname.getText();
                String lastName = fld_signupLastname.getText();
                String username = fld_signupUsername.getText();
                String password = fld_signupPassword.getText();
                String user_type = "operator";




                if (User.add(firstName,lastName,username,password,user_type)) {
                    Helper.showMsg("done");
                    setVisible(false);
                    loginGUI.setVisible(true);
                    loginGUI.getFld_loginUsername().setText(username);

                }
            }
        });

        btn_alreadyAccount.setForeground(Color.BLUE);
        btn_alreadyAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn_alreadyAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               setVisible(false);
               loginGUI.setVisible(true);
            }
        });





    }


    public static void main(String[] args) {
        Helper.setLayout();
        SignupGUI signupGUI = new SignupGUI(); 

    }
}
