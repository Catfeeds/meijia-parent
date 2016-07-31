package com.simi.po.model.record;

public class RecordHolidayDay {
    private Long id;

    private int year;

    private String cday;

    private Long addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCday() {
        return cday;
    }

    public void setCday(String cday) {
        this.cday = cday == null ? null : cday.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}