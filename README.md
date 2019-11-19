# rabbitmq-demo
Demo project for RabbitMQ

## Reference  
- [初探RabbitMQ消息队列](https://blog.battcn.com/2018/05/22/springboot/v2-queue-rabbitmq/) by[唐亚峰](https://github.com/battcn)
- [RabbitMQ延迟队列](https://blog.battcn.com/2018/05/23/springboot/v2-queue-rabbitmq-delay/) by[唐亚峰](https://github.com/battcn)

## rabbitmq in docker
```
docker pull rabbitmq:3-management
docker run -d -p15672:15672 -p5672:5672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin --name mq docker.io/rabbitmq:3-management
# 遇到密码错误先执行一次退出登录，因为这个是公共的资源，不需要登录
docker logout
```
