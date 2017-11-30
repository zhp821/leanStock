package com.lean.stock.fetch.service;

import java.util.*;
import java.sql.*;
import java.io.*;

public class MuiltThreadImportDB 
{
    private static int m_record;
    private static BufferedReader br;
    private ArrayList<String> list;
    private static int m_thread;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.168.0.147:3306/stock";
    static final String USER = "root";
    static final String PASS = "pmo#04052017P";
    static final String dataDir = "E:\\work\\leanStock\\data";
    Connection conn;
    Statement stmt;
    
    public MuiltThreadImportDB() {
        this.conn = null;
        this.stmt = null;
    }
    
    public void start() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            (this.conn = DriverManager.getConnection("jdbc:mysql://10.168.0.147:3306/stock", "root", "pmo#04052017P")).setAutoCommit(false);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        int count = 0;
        this.list = new ArrayList<String>(MuiltThreadImportDB.m_record + 1);
        final File dirFile = new File("E:\\work\\leanStock\\data");
        if (!dirFile.exists()) {
            System.out.println("E:\\work\\leanStock\\datado not exit");
            return;
        }
        final File[] listFiles;
        final File[] fileList = listFiles = dirFile.listFiles();
        for (final File file : listFiles) {
            if (file.getName().endsWith(".csv") && !file.getName().startsWith("allStock")) {
                try {
                    MuiltThreadImportDB.br = new BufferedReader(new FileReader(file), 8192);
                }
                catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
            }
            try {
                String line;
                while ((line = MuiltThreadImportDB.br.readLine()) != null) {
                    if (count < MuiltThreadImportDB.m_record) {
                        this.list.add(line);
                        ++count;
                    }
                    else {
                        this.list.add(line);
                        count = 0;
                        this.list = new ArrayList<String>(MuiltThreadImportDB.m_record + 1);
                    }
                }
                if (this.list.size() > 0) {
                    //final Thread t1 = new Thread((Runnable)new MuiltThreadImportDB.MultiThread(this, (ArrayList)this.list), Integer.toString(MuiltThreadImportDB.m_thread++));
                    System.out.println("********start thread number " + MuiltThreadImportDB.m_thread);
                    //t1.start();
                }
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }
    
    public static void main(final String[] args) {
        new MuiltThreadImportDB().start();
    }
    
    static {
        MuiltThreadImportDB.m_record = 99999;
        MuiltThreadImportDB.br = null;
        MuiltThreadImportDB.m_thread = 0;
    }
}
