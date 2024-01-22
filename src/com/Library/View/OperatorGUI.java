package com.Library.View;

import com.Library.Helper.*;
import com.Library.Model.*;


import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel wtop;
    private JButton btn_logout;
    private JPanel pnl_users;
    private JScrollPane scrl_users;
    private JTable tbl_users;
    private JPanel pnl_userAddForm;
    private JTextField fld_formFirstName;
    private JLabel lbl_formUsername;
    private JLabel lbl_formFirstName;
    private JTextField fld_formUsername;
    private JLabel lbl_formPassword;
    private JTextField fld_formPassword;
    private JButton btn_formSign;
    private JLabel lbl_formuUsertype;
    private JComboBox cmb_formUsertype;
    private JTextField fld_formLastName;
    private JLabel lbl_formLastName;
    private JTabbedPane tbd_userProcess;
    private JPanel pnl_userDeleteForm;
    private JTextField fld_deleteFormUserID;
    private JLabel lbl_deleteFormUserID;
    private JButton btn_deleteUser;
    private JPanel pnl_userSearch;
    private JTextField fld_searchFirstName;
    private JTextField fld_searchUsername;
    private JComboBox cmb_searchUserType;
    private JButton btn_searchUser;
    private JTextField fld_searchLastName;
    private JLabel lbl_searchFirstName;
    private JLabel lbl_searchLastName;
    private JLabel lbl_searchUsername;
    private JLabel lbl_searchUserType;
    private JPanel pnl_menu;
    private DefaultTableModel mdl_users;
    private Object[] row_users;

    private final Operator operator;

    public OperatorGUI(Operator operator) {
        this.operator = operator;
        add(wrapper);
        setSize(1250,750);

        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setTitle(Config.PROJECT_TITLE);

        setResizable(false);
        setVisible(true);


        mdl_users = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_users = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        mdl_users.setColumnIdentifiers(col_users);
        row_users = new Object[col_users.length];
        loadUserModel();

        tbl_users.setModel(mdl_users);
        tbl_users.getTableHeader().setReorderingAllowed(false);

        tbl_users.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selectedUserID = tbl_users.getValueAt(tbl_users.getSelectedRow(), 0).toString();
                fld_deleteFormUserID.setText(selectedUserID);
            } catch (Exception exception) {

            }
        });

        tbl_users.getModel().addTableModelListener(e -> {

            if (e.getType() == TableModelEvent.UPDATE) {
                int userID = Integer.parseInt(tbl_users.getValueAt(tbl_users.getSelectedRow(),0).toString());

                String userFirstname;
                String userLastname;
                try {
                    String[] names = tbl_users.getValueAt(tbl_users.getSelectedRow(), 1).toString().split(" ");
                    userFirstname = names[0];
                    userLastname = names[1];
                } catch (Exception exception) {
                    loadUserModel();
                    return;
                }
                String username = tbl_users.getValueAt(tbl_users.getSelectedRow(),2).toString();
                String userPassword = tbl_users.getValueAt(tbl_users.getSelectedRow(),3).toString();
                String userType = tbl_users.getValueAt(tbl_users.getSelectedRow(),4).toString();

                if (User.update(userID,userFirstname,userLastname,username,userPassword,userType)) {
                    Helper.showMsg("done");
                }
                loadUserModel();

            }

        });

        btn_formSign.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_formFirstName) || Helper.isFieldEmpty(fld_formLastName) || Helper.isFieldEmpty(fld_formPassword) || Helper.isFieldEmpty(fld_formUsername)) {
                Helper.showMsg("fill");
            }
            else {
                String firstName = fld_formFirstName.getText();
                String lastName = fld_formLastName.getText();
                String username = fld_formUsername.getText();
                String password = fld_formPassword.getText();
                String user_type = cmb_formUsertype.getSelectedItem().toString();


                if (User.add(firstName,lastName,username,password,user_type)) {
                    Helper.showMsg("done");
                    loadUserModel();
                    fld_formPassword.setText(null);
                    fld_formUsername.setText(null);
                    fld_formFirstName.setText(null);
                    fld_formLastName.setText(null);
                    cmb_formUsertype.setSelectedItem("");
                }
            }
        });
        btn_deleteUser.addActionListener(e -> {

            if (Helper.isFieldEmpty(fld_deleteFormUserID)) {
                Helper.showMsg("Lütfen Kullanıcı ID giriniz.");
            } else {
                int userID;
                try {
                    userID = Integer.parseInt(fld_deleteFormUserID.getText().trim());
                } catch (Exception exception) {
                    Helper.showMsg("Lütfen geçerli bir ID giriniz.");
                    fld_deleteFormUserID.setText(null);
                    return;
                }

                if (User.delete(userID)) {
                    Helper.showMsg("done");
                    loadUserModel();
                    fld_deleteFormUserID.setText(null);
                }
            }


        });
        btn_searchUser.addActionListener(e -> {

            String first_name = fld_searchFirstName.getText().trim();
            String last_name = fld_searchLastName.getText().trim();
            String username= fld_searchUsername.getText().trim();
            String user_type = cmb_searchUserType.getSelectedItem().toString();
            String query = User.searchQuery(first_name,last_name,username,user_type);

            ArrayList<User> searchUser = User.getSearchUserList(query);

            loadUserModel(searchUser);

        });
        btn_logout.addActionListener(e -> {
            setVisible(false);
            LoginGUI loginGUI = new LoginGUI();
            dispose();
        });
    }

    public void loadUserModel() {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_users.getModel();
        clearModel.setRowCount(0);

        for (User obj:User.getList()) {
            int i = 0;
            row_users[i++] = obj.getID();
            row_users[i++] = obj.getFirst_name() + " " + obj.getLast_name();
            row_users[i++] = obj.getUsername();
            row_users[i++] = obj.getPassword();
            row_users[i++] = obj.getUser_type();
            mdl_users.addRow(row_users);
        }

    }

    public void loadUserModel(ArrayList<User> list) {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_users.getModel();
        clearModel.setRowCount(0);

        for (User obj:list) {
            int i = 0;
            row_users[i++] = obj.getID();
            row_users[i++] = obj.getFirst_name() + " " + obj.getLast_name();
            row_users[i++] = obj.getUsername();
            row_users[i++] = obj.getPassword();
            row_users[i++] = obj.getUser_type();
            mdl_users.addRow(row_users);
        }

    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator operator1 = new Operator();
        operator1.setID(1);
        operator1.setFirst_name("Eyüp");
        operator1.setLast_name("Aydın");
        operator1.setPassword("123123");
        operator1.setUsername("eyupaydin");

        DBConnector.getInstance();

        OperatorGUI opGUI = new OperatorGUI(operator1);
    }


}
