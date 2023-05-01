package com.willow.beer_work.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class DateMapper {
    OffsetDateTime asOffsetDateTime(Timestamp ts){
        if(ts != null)  {
            return OffsetDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
        }
        else{
            return null;
        }
    }

    Timestamp asTimeStamp(OffsetDateTime odt){
        if(odt != null) {
            return Timestamp.from(odt.toInstant());
        }
        else{
            return null;
        }
    }
}
