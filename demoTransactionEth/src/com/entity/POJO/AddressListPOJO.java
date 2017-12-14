package com.entity.POJO;

import com.entity.Address;

import java.util.List;

public class AddressListPOJO {
    List<Address> data;

    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
    }

    public static AddressListPOJO getInstance() {
        return new AddressListPOJO();
    }
}
