# leanStock

1 原始数据通过python tushare模块进行抓取 通过ts.get_k_data 方法获取90年至今的所有股票每天的交易数据
2 spring booter服务提供接口 对股票数据进行分析监控和抓取
3 通过dockerfile初始化python和tushare环境,基于 alpine 并发布镜像在dockerhub镜像仓库
