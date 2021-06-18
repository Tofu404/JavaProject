package com.hello;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class NumberTest {

    public static void main(String[] args) {
        Queue<Byte> byteQueue = new LinkedList<>();
        byte[] bytes = {0x00, 0x11, 0x22, 0x33, 0x44, 0x55};
        for (byte aByte : bytes) {
            byteQueue.add(aByte);
        }
        Logger logger = Logger.getGlobal();
        logger.info("nihao");
        logger.info("nihao");
        logger.info("nihao");
        logger.info("nihao");
    }
}
