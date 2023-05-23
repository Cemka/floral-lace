package ru.myitschool.florallace.feature.ordermaking.entity;

import java.util.List;

public class DelReqBody {
    List<Long> ids;

    public DelReqBody(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }
}
