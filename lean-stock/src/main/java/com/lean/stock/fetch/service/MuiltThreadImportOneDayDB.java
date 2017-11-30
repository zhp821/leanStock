package com.lean.stock.fetch.service;

import java.util.*;
import java.sql.*;
import java.io.*;

public class MuiltThreadImportOneDayDB
{
    private static int m_record;
    private static BufferedReader br;
    private ArrayList<String> list;
    private static int m_thread;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.168.0.147:3306/stock";
    static final String USER = "root";
    static final String PASS = "pmo#04052017P";
    static final String file = "E:\\work\\leanStock\\data1\\2017-09-18-2017-09-18.csv";
    Connection conn;
    Statement stmt;
    
    public MuiltThreadImportOneDayDB() {
        this.conn = null;
        this.stmt = null;
    }
    
    public void start(String file) {
        if (file == null || file.equals("")) {
            file = "E:\\work\\leanStock\\data1\\2017-09-18-2017-09-18.csv";
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            (this.conn = DriverManager.getConnection("jdbc:mysql://10.168.0.147:3306/stock", "root", "pmo#04052017P")).setAutoCommit(false);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        int count = 0;
        this.list = new ArrayList<String>(MuiltThreadImportOneDayDB.m_record + 1);
        try {
            MuiltThreadImportOneDayDB.br = new BufferedReader(new FileReader(file), 8192);
        }
        catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return;
        }
        try {
            String line;
            while ((line = MuiltThreadImportOneDayDB.br.readLine()) != null) {
                if (count < MuiltThreadImportOneDayDB.m_record) {
                    this.list.add(line);
                    ++count;
                }
                else {
                    this.list.add(line);
                    count = 0;
                   // final Thread t1 = new Thread((Runnable)new MuiltThreadImportOneDayDB.MultiThread(this, (ArrayList)this.list), Integer.toString(MuiltThreadImportOneDayDB.m_thread++));
                    System.out.println("********start thread number " + MuiltThreadImportOneDayDB.m_thread);
                    //t1.start();
                    this.list = new ArrayList<String>(MuiltThreadImportOneDayDB.m_record + 1);
                }
            }
            //final Thread t1 = new Thread((Runnable)new MuiltThreadImportOneDayDB.MultiThread(this, (ArrayList)this.list), Integer.toString(MuiltThreadImportOneDayDB.m_thread++));
            //t1.start();
            this.list = new ArrayList<String>(MuiltThreadImportOneDayDB.m_record + 1);
            System.out.println("********start thread number " + MuiltThreadImportOneDayDB.m_thread);
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
    }
    
    public static void main(final String[] args) {
        if (args.length < 1) {
            return;
        }
        new MuiltThreadImportOneDayDB().start(args[0]);
    }
    
    static {
        MuiltThreadImportOneDayDB.m_record = 99999;
        MuiltThreadImportOneDayDB.br = null;
        MuiltThreadImportOneDayDB.m_thread = 0;
    }
}
