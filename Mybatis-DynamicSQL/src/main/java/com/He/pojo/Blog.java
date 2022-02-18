package com.He.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/18 11:06
 */
@Data
@AllArgsConstructor
public class Blog {
    private String id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
