package com.epam.hostel.bean.entity;

import java.util.List;

/**
 * Created by ASUS on 01.02.2017.
 */
public class PagedList<T> {

    private List<T> content;
    private int currentPage;
    private int lastPage;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagedList<?> pagedList = (PagedList<?>) o;

        if (currentPage != pagedList.currentPage) return false;
        if (lastPage != pagedList.lastPage) return false;
        return content != null ? content.equals(pagedList.content) : pagedList.content == null;

    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + currentPage;
        result = 31 * result + lastPage;
        return result;
    }

    @Override
    public String toString() {
        return "PagedList{" +
                "content=" + content +
                ", currentPage=" + currentPage +
                ", lastPage=" + lastPage +
                '}';
    }
}
