package cn.iwars.webproject.utils;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 生成UUID
     * @return UUId
     */
    public static  String getUUId(){
       return UUID.randomUUID().toString();
    }
}
