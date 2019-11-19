package org.kurenai.demo.permission;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.kurenai.demo.spring.Receiver;
import org.kurenai.demo.spring.SpringGuidesApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Class Name: Runner
 * Description:
 *
 * @author: liufuhong
 * @mail: liufuhong@ly-sky.com
 * @date: 2019年03月08日
 * @version: 1.0
 */

@Component
public class Runner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        ArrayList<PermissionElement> permissionElements = new ArrayList<>();
        PermissionElement permissionElement = PermissionElement.builder()
                                                               .name("permission")
                                                               .path("/test")
                                                               .regex("^/test")
                                                               .permissionCode("permissionCode")
                                                               .build();
        permissionElements.add(permissionElement);
        PermissionQueueMsg permissionQueueMsg = new PermissionQueueMsg();
        permissionQueueMsg.setAppName("appName");
        permissionQueueMsg.setOverWrite(false);
        permissionQueueMsg.setVersion("0.0.1");
        permissionQueueMsg.setPermissionElementList(permissionElements);
        rabbitTemplate.convertAndSend(PermissionMQConfig.PERMISSION_EXCHANGE,
                                      PermissionMQConfig.PERMISSION_INIT_ROUTING_KEY,
                                      permissionQueueMsg);
    }
}
