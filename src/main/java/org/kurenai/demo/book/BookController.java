package org.kurenai.demo.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Kurenai
 * @since 2019-02-26 22:48
 */

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BookController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_BOOK_QUEUE, book); 对应 {@link BookHandler#listenerAutoAck}
     * this.rabbitTemplate.convertAndSend(RabbitConfig.MANUAL_BOOK_QUEUE, book); 对应 {@link BookHandler#listenerManualAck}
     */
    @GetMapping("demo")
    public void demoMessage() {
        Book book = new Book();
        book.setId("1");
        book.setName(" Demo project of RabbitMQ");
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.DEFAULT_BOOK_QUEUE, book);
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.MANUAL_BOOK_QUEUE, book);
    }

    /**
     * this.rabbitTemplate.convertAndSend(RabbitConfig.REGISTER_DELAY_EXCHANGE, RabbitConfig.DELAY_ROUTING_KEY, book); 对应 {@link BookHandler#listenerDelayQueue}
     */
    @GetMapping
    public void defaultMessage() {
        Book book = new Book();
        book.setId("1");
        book.setName("一起来学Spring Boot");
        // 添加延时队列
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.REGISTER_DELAY_EXCHANGE, RabbitMQConfig.DELAY_ROUTING_KEY, book, message -> {
            // TODO 第一句是可要可不要,根据自己需要自行处理
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Book.class.getName());
            // TODO 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(5 * 1000 + "");
            return message;
        });
        log.info("[发送时间] - [{}]", LocalDateTime.now());
    }
}
