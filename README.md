# rabbitmq-demo
Demo project for RabbitMQ

## 参考  
- https://blog.battcn.com/2018/05/22/springboot/v2-queue-rabbitmq/ @唐亚峰
- https://blog.battcn.com/2018/05/23/springboot/v2-queue-rabbitmq-delay/ @唐亚峰

## rabbitmq in docker
```
docker run -d -p15672:15672 -p5672:5672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin --name mq docker.io/rabbitmq:3-management
```
