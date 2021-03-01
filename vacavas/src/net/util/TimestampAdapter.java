package net.util;


import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimestampAdapter extends XmlAdapter<Date, Timestamp> {
    public Timestamp marshal(Timestamp v) {
        return new Timestamp(v.getTime());
    }
    public Timestamp unmarshal(Date v) {
        return new Timestamp(v.getTime());
    }
}
