package com.my.notes.users.binding;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    public LocalDateTime unmarshal(String value) {
        return LocalDateTime.parse(value);
    }

    public String marshal(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
