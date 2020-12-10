package com.manueh.wikigi.enums;

public enum Codes_Permisions {
    CODE_WRITE_EXTERNAL_STORAGE_PERMISSION(123);
    ;
    private int code;
    private Codes_Permisions(int code){
        this.code=code;
    }
    public int getCode(){
        return  this.code;
    }
}
