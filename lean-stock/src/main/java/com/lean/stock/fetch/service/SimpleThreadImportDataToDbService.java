package com.lean.stock.fetch.service;

import com.baomidou.mybatisplus.service.impl.*;
import com.lean.stock.common.dao.*;
import com.lean.stock.common.entity.*;
import com.baomidou.mybatisplus.service.*;
import org.springframework.stereotype.*;
import java.util.*;
import java.io.*;
import java.sql.*;

@Service
public class SimpleThreadImportDataToDbService extends ServiceImpl<StockDayMapper, StockDay> implements IService<StockDay>
{
    private static int m_record;
    private static BufferedReader br;
    private ArrayList<String> list;
    
    public int start(final String dataDir) throws SQLException {
        int num = 0;
        int count = 0;
        this.list = new ArrayList<String>(SimpleThreadImportDataToDbService.m_record + 1);
        final File[] dataFiles;
        final File[] fileList = dataFiles = this.getDataFiles(dataDir);
        for (final File file : dataFiles) {
            if (file.getName().endsWith(".csv") && !file.getName().startsWith("allStock")) {
                try {
                    SimpleThreadImportDataToDbService.br = new BufferedReader(new FileReader(file), 8192);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            try {
                String line;
                while ((line = SimpleThreadImportDataToDbService.br.readLine()) != null) {
                    ++num;
                    if (count < SimpleThreadImportDataToDbService.m_record) {
                        this.list.add(line);
                        ++count;
                    }
                    else {
                        this.list.add(line);
                        count = 0;
                        this.insertUserBehaviour(this.list);
                        this.list = new ArrayList<String>(SimpleThreadImportDataToDbService.m_record + 1);
                    }
                }
                if (this.list.size() > 0) {
                    this.insertUserBehaviour(this.list);
                    this.list = new ArrayList<String>(SimpleThreadImportDataToDbService.m_record + 1);
                }
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return num;
    }
    
    private File[] getDataFiles(final String dataDir) {
        final File dfile = new File(dataDir);
        File[] fileList = null;
        if (dfile.isFile()) {
            fileList = new File[] { dfile };
        }
        else if (dfile.isDirectory()) {
            if (!dfile.exists()) {
                System.out.println(dataDir + "do not exit");
                return null;
            }
            fileList = dfile.listFiles();
        }
        return fileList;
    }
    
    public int insertUserBehaviour(final ArrayList<String> list) throws SQLException {
        for (int i = 0; i < list.size(); ++i) {
            final String[] datas = list.get(i).split(",");
            if (datas.length <= 6 || datas[1] == null || !datas[1].equals("date")) {
                if (datas.length >= 7) {
                    final StockDay stock = new StockDay(datas[7], datas[2], datas[3], datas[1], datas[6], datas[4], datas[5]);
                    this.insert((StockDay)stock);
                }
            }
        }
        System.out.println("~~~~~~~~~~~~~~already deal size " + list.size());
        return 1;
    }
    
    static {
        SimpleThreadImportDataToDbService.m_record = 9999;
        SimpleThreadImportDataToDbService.br = null;
    }
}
