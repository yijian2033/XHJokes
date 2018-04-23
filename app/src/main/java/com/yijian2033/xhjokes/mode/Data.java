package com.yijian2033.xhjokes.mode;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-18]
 */
public class Data {
    private String content;
    private String hashId;
    private long unixtime;
    private String updatetime;

    public Data(String content, String hashId, long unixtime, String updatetime) {
        this.content = content;
        this.hashId = hashId;
        this.unixtime = unixtime;
        this.updatetime = updatetime;
    }

    public String getContent() {
        return content;
    }

    public String getHashId() {
        return hashId;
    }

    public long getUnixtime() {
        return unixtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }
}
