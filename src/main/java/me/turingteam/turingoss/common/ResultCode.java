package me.turingteam.turingoss.common;

public enum ResultCode {
    CODE_SUCCESS(200),
    CODE_UNAUTH(401),
    CODE_ERROR(500);

    final int CODE;

    ResultCode(int code){
        this.CODE = code;
    }

}
