package com.annet.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanhu
 * @version 2018/12/27 16:54
 */
@Data
public class PageBean<T> {

    private int number;

    private int size;

    private boolean first;

    private boolean last;

    private int totalPages;

    private long totalElements;

    private int numberOfElements;

    private List<T> content;

    private PageBean(int number, int size, long totalElements, List<T> content) {
        this.number = number;
        this.size = size;
        if (totalElements <= 0) {
            this.totalPages = 0;
            this.totalElements = 0;
        } else {
            this.totalElements = totalElements;
            if (totalElements % size == 0) {
                this.totalPages = (int) (totalElements / size);
            } else {
                this.totalPages = (int) (totalElements / size + 1);
            }
        }
        if (number + 1 == this.totalPages) {
            this.last = true;
        }
        if (number == 0) {
            this.first = true;
        }
        if (null == content) {
            content = new ArrayList<>();
        }
        this.content = content;
        this.numberOfElements = content.size();
    }

    public static <T> PageBean<T> of(int number, int size, long totalElements, List<T> content) {
        return new PageBean<>(number, size, totalElements, content);
    }
}
