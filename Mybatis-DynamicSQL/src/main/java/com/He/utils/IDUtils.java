package com.He.utils;

import java.util.UUID;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/18 11:21
 */
public class IDUtils {
    public static String getId() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
