package com.dyy.tsp.evgb.gateway.protocol.enumtype;

public enum CommandDownHelperType {

    CLOSE_DEBUG("关闭监控"),
    OPEN_DEBUG("开启监控"),
    CLEAR_CAHCE("清除内存"),
    QUERY_COMMAND("查询指令"),
    SET_COMMAND("设置指令"),
    REMOTE_CONTROL("车载终端控制命令"),
    ;
    private String desc;

    CommandDownHelperType(String desc) {
        this.desc = desc;
    }

    public static CommandDownHelperType valuesOf(String name) {
        for (CommandDownHelperType enums : CommandDownHelperType.values()) {
            if (enums.name().equals(name)) {
                return enums;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
