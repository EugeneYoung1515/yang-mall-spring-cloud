# yang-mall-spring-cloud

在另一个项目[（小型分布式商城）](https://github.com/EugeneYoung1515/yang-mall)中添加Spring Cloud F版组件（含Spring Cloud Gateway）。
将Spring Boot版本升级为2.0，使用Quartz starter替换原有的Quartz配置。优化模块的结构和继承，拆出新的JAR包，但是尽量不修改包名,保持与原项目的兼容性。尽量不修改原有代码，通过新的代码层（JAR包），引入Feign client。
