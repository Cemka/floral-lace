package ru.myitschool.florallace.feature.ordermaking.entity;

public class OrderTime {
    private String time;
    private String timeName;

    public String getTimeName() {
        return timeName;
    }

    public String getTime() {
        return time;
    }

    public OrderTime(String time, String timeName) {
        this.time = time;
        this.timeName = timeName;
    }
}
