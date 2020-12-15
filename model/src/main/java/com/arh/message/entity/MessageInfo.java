package com.arh.message.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MessageInfo implements Serializable {
    private Integer id;

    private String messageId;

    private String name;

    private String info;
}
