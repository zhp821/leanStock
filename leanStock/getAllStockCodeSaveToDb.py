#-------------------------------------------------------------------------------
# Name:        zhuping
# Purpose: 获取所有stock列表记录到数据库中
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
   engine = create_engine('mysql://root:pmo#04052017P@10.168.0.147:3306/stock?charset=utf8')
   df.to_sql('stock',engine,if_exists='append')

if __name__ == '__main__':
    main()