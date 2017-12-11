package com.entity.POJO;

import com.entity.Address;

import java.util.List;

public class AddressesPOJO {
    private Pagination pagination;
    private List<Address> data;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
    }
}
