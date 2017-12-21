#-------------------------------------------------------------------------------
# Name:        zhuping
# Purpose: 闂傚倸鍊风粈渚€宕ョ€ｎ喖纾块柟鎯版鎼村﹪鏌ら懝鎵牚濞存粌缍婇弻娑㈠Ψ椤旂厧顫╃紓浣哄О閸庣敻寮诲鍫闂佸憡鎸鹃崰搴敋閿濆鍨傛い鎰╁€楅悾鐣岀磽娓氬洤浜為柣顭戞憽k闂傚倸鍊风粈渚€骞夐敍鍕殰婵°倕鎳岄埀顒€鍟鍕箛椤戔斂鍎甸弻娑㈠箛闂堟稒鐏堝┑锛勫仒缁瑩骞冨鈧幃娆撴嚋濞堟寧顥夐梻浣侯潒閸愯儻鍚梺鍝勬湰缁嬫垿鍩㈡惔銈囩杸闁规儳鐖奸悰鎾绘⒒娴ｉ涓查柤闈涚秺瀹曟粌鈹戦崱鈺佹闂佸憡娲﹂崹浼存偂濞戙垺鐓曢柟鑸妽濞呭洭鏌￠崱娆忊枅婵﹨娅ｉ幃浼村灳閸忓懎顥氶梻鍌欑閹诧繝骞愰悜鑺ュ€块柨鏃€鍎抽ˉ姘舵煙闂傚鍔嶉柣鎾寸懄閵囧嫰寮介妸褜鏆￠梺闈╃到缂嶅﹪寮诲☉銏″亹闁肩⒈鍓涙导鍥⒑閻熸澘妲绘い鎴濐槸椤曪綁宕滄担鐟扮／闂侀潧顭悘婵嬵敇閵忊檧鎷洪梺鍛婄☉閿曘倗绮椤儻顦虫い銊ユ嚇閺佸啴濮€閳ユ剚鍤ら梺鍝勵槹閸ㄨ泛鐣靛澶嬧拺缂備焦锕╁▓鏃堟煟濡も偓閿曨亪骞冨Δ鍜佹晩婵炴潙楠哥紞濠囧箖閳哄懏鎯炴い鎰剁悼閳ь剦鍨跺?
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

   i=0
   allFile='demo.csv'

   for code in df.index:
       i+=1
       print ('now is %d %s' %(i,code))
       dftmp = ts.get_k_data(code,'2017-12-09','2017-12-14','30')

       if  dftmp is  None:
          continue

       if os.path.exists(filename+allFile):
          dftmp.to_csv(filename+allFile, mode='a', header=None)
       else:
          dftmp.to_csv(filename+allFile)



if __name__ == '__main__':
    main()