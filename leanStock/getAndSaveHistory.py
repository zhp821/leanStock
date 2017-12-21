#-------------------------------------------------------------------------------
# Name:        zhuping
# Purpose: 获取所有stock列表和历史每天的收盘情况记录到本地文件中
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
import getopt

def main():
   opts, args = getopt.getopt(sys.argv[1:], "hs:e:d:")
   df=ts.get_stock_basics()
   filename='E:\\work\\leanStock\\data2\\';
   start='2010-01-01'
   end='2017-09-15'
   for op, value in opts:
    if op == "-d":
        filename = value
    elif op == "-s":
        start = value
    elif op == "-e":
        end=value
    elif op == "-h":
        print ("Help Info -s: start time 2017-09-13 -e:end time -d:file save dir")
        sys.exit()
   df.to_csv(filename+'allStock.csv',mode='w',encoding='GBK')
   i=0
   allFile=start+'-'+end+'.csv'
   for code in df.index:
       i+=1
       print ('now is %d %s' %(i,code))
       dftmp = ts.get_k_data(code,start,end)

       if  dftmp is  None:
          continue
       if os.path.exists(filename+code+".csv"):
          dftmp.to_csv(filename+code+".csv", mode='a', header=None)
       else:
          dftmp.to_csv(filename+code+".csv")



if __name__ == '__main__':
    main()