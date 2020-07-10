package com.lhcz.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

/**
 * @author 41008
 */
public class UUIDFactory {

    /**带时间顺序的UUID*/
    public static String getUuid(){
        TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        return timeBasedGenerator.generate().toString().replaceAll("-","").toUpperCase();
    }

    /**随机的UUID*/
    public static String getUuidStr(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        str = str.replaceAll("[-]", "");
        return str.toUpperCase();
    }

    private static final int NUM = 3;
    public static void main(String[] args){
        for(int i=0;i<NUM;i++){
            System.out.println(UUIDFactory.getUuid());
        }
    }
}
