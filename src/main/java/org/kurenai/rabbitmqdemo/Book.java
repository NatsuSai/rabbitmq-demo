package org.kurenai.rabbitmqdemo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘富鸿
 * @since  2019-02-26 22:38
 */

@Data
public class Book implements Serializable {
    private static final long serializableUID = 1L;

    private String id;
    private String name;
}
