package com.example.neuralpruning;

import java.util.List;

public class course {
    private String status;
    private String msg;
    private List<data> dataList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<data> getDataList() {
        return dataList;
    }

    public void setDataList(List<data> dataList) {
        this.dataList = dataList;
    }
}
