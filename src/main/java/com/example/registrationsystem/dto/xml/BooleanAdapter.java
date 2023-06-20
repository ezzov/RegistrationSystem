package com.example.registrationsystem.dto.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<Integer, Boolean> {
    @Override
    public Boolean unmarshal(Integer integer) throws Exception {
        return null;
    }

    @Override
    public Integer marshal(Boolean aBoolean) throws Exception {
        return aBoolean ? 1 : 2;
    }
}
