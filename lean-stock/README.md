- 编译

    mvn clean install -Dmaven.test.skip=true
    
- 执行

    java -jar lean-stock-1.0.0.jar --spring.profiles.active=test
    
    注意：根据具体环境配置profile，现有dev，test，online