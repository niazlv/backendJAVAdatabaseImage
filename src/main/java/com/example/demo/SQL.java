package com.example.demo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.sql.*;

public class SQL {
    // JDBC URL, username and password of MySQL server
    private static final String model = "vezdecode";
    private static final String url = "jdbc:mysql://localhost:3306/"+model;
    private static final String user = "root";
    private static final String password = "12QasW34";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static PreparedStatement pst;
    private static Statement stmt;
    private static int id = 0;
    private static ResultSet rs;
    public static InputStream SQLget(int ids){
        InputStream imgStream = null;
        int i = 0;
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from "+model+".images where id ='"+ids+"';");
            while (rs.next())
                imgStream = rs.getBinaryStream(2);
        }catch (SQLException sqlEX){
            sqlEX.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        return (imgStream);
    }
    public static File SQLgetFile(int ids, String pathname){
        File file = new File(pathname);
        InputStream is= SQLget(ids);
        try(OutputStream outputStream = new FileOutputStream(file)){
            IOUtils.copy(is, outputStream);
        } catch (IOException ignored) {}

        return (file);
    }
    public static int SQLadd(String name, File image){
        try {
            FileInputStream fis = new FileInputStream(image);
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            pst = con.prepareStatement("insert into "+model+".images (name, image) values (?, ?)");

            pst.setString(1,name);
            pst.setBinaryStream(2,fis,(int) image.length());
            pst.execute();
            rs = stmt.executeQuery("SELECT * FROM "+model+".images WHERE id=LAST_INSERT_ID();");
            while (rs.next())
                id = rs.getInt(1);
        } catch (SQLException | FileNotFoundException sqlEX){
            sqlEX.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch (SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch (SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        return (id);
    }

    public static void main(String args[]) {
        SQLgetFile(1,"test.jpeg");
//        try {
//            // opening database connection to MySQL server
//            con = DriverManager.getConnection(url, user, password);
//
//            // getting Statement object to execute query
//            stmt = con.createStatement();
//
//            // executing SELECT query
//            rs = stmt.executeQuery(query);
//
//            while (rs.next()) {
//                int count = rs.getInt(1);
//                System.out.println(count + " " + rs.getString(2) + " " + rs.getString(3));
//            }
//        } catch (SQLException sqlEx) {
//            sqlEx.printStackTrace();
//        } finally {
//            //close connection ,stmt and resultset here
//            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
//            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
//            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
//        }
    }
}
