package com.Library.Helper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static int screenCenterPoint(String axis, Dimension size) {

        int point;

        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMsg(String str) {
        optionPageTR();
        String msg;
        String title;

        switch (str) {
            case "fill":
                msg = "Lütfen tüm alanları doldurunuz!";
                title = "Hata";
                break;
            case "done":
                msg = "İşlem Başarılı!";
                title = "Başarılı";
                break;
            case "error":
                msg = "Bir hata oluştu!";
                title = "Hata";
                break;
            default:
                msg = str;
                title = "Mesaj";
        }

        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static void optionPageTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
    }

    public static List<String> getUserTypeList() {
        List<String> userTypeList = new ArrayList<>();
        userTypeList.add("student");
        userTypeList.add("operator");
        userTypeList.add("educator");

        return userTypeList;
    }

}
