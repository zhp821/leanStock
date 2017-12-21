#-------------------------------------------------------------------------------
# Name:        zhuping
# Purpose: 闂傚倷绀侀崥瀣磿閹惰棄搴婇柤鑹扮堪娴滃綊鏌涢妷顔煎缂佺姰鍎甸弻宥堫檨闁告挾鍠庨锝夊垂椤愩倗鐣界紓渚囧亞閻摡k闂傚倷绀侀幉锛勬暜濡ゅ懌鈧啯寰勯幇顑┿儵鏌涢幇闈涙灈婵＄偘绮欓幃姗€鎮欓懜娈挎闂佺鍐胯含闁哄本绋戦埢搴ょ疀閹惧爼鐛撻梻浣风串閼靛綊宕滃┑鍡╁殫闁告洦鍨伴悡娑㈡煕閹般劍娅囬柡鍡欏█濮婅櫣鎮伴垾鍏呭闂備礁鎲￠幐鐑芥倿閿旀儳顥氶柟闂寸劍閻撴瑦銇勯弽銊ь暡闁革絽缍婇弻娑㈡偐閼碱剛浼囬梺鐟板槻椤戝顕ｉ崜浣瑰磯闁靛鐏濋銏♀拺闁告稑锕ょ粭褍顭胯椤ㄥ懘鏁冮姀鈥愁嚤闁哄鍨跺畵宥夋⒑缂佹﹩娈旈柣妤€锕幃妤咁敊濞村骸缍婇幃鈺呮惞椤愶絿鈧垶姊?
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
   filename='E:\\work\\leanStock\\data1\\';
   start='2017-09-20'
   end='2017-09-20'
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

       if os.path.exists(filename+allFile):
          dftmp.to_csv(filename+allFile, mode='a', header=None)
       else:
          dftmp.to_csv(filename+allFile)



if __name__ == '__main__':
    main()