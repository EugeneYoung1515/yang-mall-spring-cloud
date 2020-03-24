package com.ywcjxf.mall.service.constant;

public class OrderAndPayConstants {
    public static final String ORDER = "order:";
    public static final String INVENTORY = "inventory:";
    public static final String ORDER_USER_IDENTIFIER = "order:user:identifier:";
    public static final String ORDER_USER_ORDER_ID = "order:user:orderId:";
    public static final String ORDER_LIST = "order:list:";
    public static final String ORDER_STAT = "order:stat:";
    public static final String ORDER_STAT_ORDER_ID = "order:stat:orderId:";

    public static final String PAY_ORDER = "pay:order:";
    public static final String PAY_ORDER_USER_IDENTIFIER = "pay:order:user:identifier:";
    public static final String PAY_ORDER_USER_ORDER_ID = "pay:order:user:orderId:";

    public static final String PAY_ORDER_USER_PAY_PLATFORM_NUMBER = "pay:order:user:payPlatformNumber:";

    public static final String PAY = "pay:";
    public static final String EMPTY_STRING_PREFIX = "";

    public static final String PAY_ORDER_USER_SUCCESS = "pay:order:user:success:";

    public static final String RABBITMQ_INCR_INVENTORY = "incrInventory";
    public static final String RABBITMQ_ORDER = "order";
    public static final String RABBITMQ_DEAD_QUEUE_INCR_INVENTORY = "deadQueueIncrInventory";
    public static final String RABBITMQ_INCR_INVENTORY_30_MIN = "incrInventory30min";
    public static final String RABBITMQ_DEAD_QUEUE_INCR_INVENTORY_30_MIN = "deadQueueIncrInventory30min";

    public static final String RABBITMQ_DEAD_LETTER_EXCHANGE = "deadLetterExchange";
    public static final String RABBITMQ_DEAD_QUEUE = "deadQueue";

    public static final String LOCK_USER_ORDER = "user:order:";
    public static final String LOCK_SPEC = "spec:";
    public static final String LOCK_PAY_USER_ORDER = "pay:user:order";

    public static final String ADDRESS_PROVINCE = "province";
    public static final String ADDRESS_CITY = "city";
    public static final String ADDRESS_DISTRICT = "district";
    public static final String ADDRESS_NAME = "name";
    public static final String ADDRESS_ADDRESS = "address";
    public static final String ADDRESS_AREA_INFO = "areaInfo";
    public static final String ADDRESS_PHONE_NUMBER = "phoneNumber";
    public static final String ADDRESS_NOT_EXIST = "地区不存在";

    public static final String TEMP_LOGISTICS_ID = "temp:logistics:id";
    public static final String TEMP_LOGISTICS = "temp:logistics:";

    public static final int TEN_MIN_AS_MILLI_SECONDS = 600000;
    public static final int THIRTY_MIN_AS_MILLI_SECONDS = 1800000;
    public static final int TEN_MIN = 10;
    public static final int TWENTY_MIN = 20;
    public static final int THIRTY_MIN = 30;
    public static final int SIXTY_MIN = 60;
}
