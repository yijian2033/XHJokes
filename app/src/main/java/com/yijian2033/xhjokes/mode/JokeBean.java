package com.yijian2033.xhjokes.mode;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-18]
 */
public class JokeBean {
    private int error_code;
    private String reason;
    private Result result;

    public JokeBean(int error_code, String reason, Result result) {
        this.error_code = error_code;
        this.reason = reason;
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }

    public Result getResult() {
        return result;
    }
}
