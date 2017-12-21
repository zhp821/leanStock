#-------------------------------------------------------------------------------
# Name:        zhuping
# Purpose: 获取所有stock列表和历史每天的收盘情况记录到数据库中
#
# Author:      zhuping
#
# Created:     11/09/2017
# Copyright:   (c) Administrator 2017
# Licence:     <your licence>
#-------------------------------------------------------------------------------
import tushare as ts
import os
import sys
from sqlalchemy import create_engine

def main():
   df=ts.get_stock_basics()
   df.to_csv(r'E:\work\leanStock\data\allStock.csv',mode='w',encoding='GBK')
   i=0
   engine = create_engine('mysql://leanboard:Ccjsj1200@123.206.87.92:3306/stock?charset=utf8')
   for code in df.index:
       i+=1
       print ('now is %d %s' %(i,code))
       dftmp = ts.get_k_data(code,start='1990-01-01',end='2017-09-12')
       if  dftmp is  None:
          continue
       dftmp.to_sql('stock_day',engine,if_exists='append')



if __name__ == '__main__':
    main()