#-------------------------------------------------------------------------------
# Name:        模块1
# Purpose:
#
# Author:      Administrator
#
# Created:     15/12/2017
# Copyright:   (c) Administrator 2017
# Licence:     <your licence>
#-------------------------------------------------------------------------------

def main():
    tmp=['123','456']
    if (tmp[1] is not None):
        tmp.append(float(tmp[1])-float(tmp[0]))
        print tmp

if __name__ == '__main__':
    main()
