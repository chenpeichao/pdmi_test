package org.pcchen.vpn;

import java.sql.*;

/**
 * ����uar��vpn
 * Created by cpc on 2018/1/5.
 */
public class ConnVPNJDBC {
    public static final String url = "jdbc:mysql://10.10.32.62:3306/epaper";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "uardev";
    public static final String password = "uardev2017";

    public Connection conn = null;
    public PreparedStatement pst = null;

    public ConnVPNJDBC(String sql) {
        try {
            Class.forName(name);// ָ����������
            conn = DriverManager.getConnection(url, user, password);// ��ȡ����
            pst = conn.prepareStatement(sql);// ׼��ִ�����
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }
    public PreparedStatement getPst() {
        return pst;
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        try {
            while(true) {
                ConnVPNJDBC connVpn = new ConnVPNJDBC("select id from t_epaper_paper where id = 1");
                PreparedStatement pst2 = connVpn.getPst();
                ResultSet rs = pst2.executeQuery();
                while (rs.next()) {
                    Integer uid = rs.getInt(1);
                    System.out.println("id" + uid);
                }//��ʾ����
                rs.close();
                pst2.close();
                connVpn.close();
                Thread.currentThread().sleep(1000 * 3 * 60);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
