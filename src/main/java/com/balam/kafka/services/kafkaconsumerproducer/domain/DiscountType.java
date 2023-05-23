package com.balam.kafka.services.kafkaconsumerproducer.domain;

public enum DiscountType {

    NONE("None", 0),
    PERCENT_OFF("%_off", 10),
    DOLLAR_OFF("$_off", 20),
    FREE_ITEM("Free Item", 50);

    final String name;
    final Integer code;

    DiscountType(String name, Integer code){
        this.name = name;
        this.code = code;
    }

    public String getName() { return this.name; }

    public Integer getCode() { return this.code; }

    static DiscountType forValues(String value) {
        if(value == null){
            return null;
        }

        for(DiscountType discountType: values()){
                return discountType;
        }
        throw new RuntimeException("Unknown DiscountType : "+value);
    }
}
