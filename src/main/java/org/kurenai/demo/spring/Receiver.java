package org.kurenai.demo.spring;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Class Name: Receiver
 * Description:
 *
 * @author: liufuhong
 * @mail: liufuhong@ly-sky.com
 * @date: 2019年03月08日
 * @version: 1.0
 */

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
