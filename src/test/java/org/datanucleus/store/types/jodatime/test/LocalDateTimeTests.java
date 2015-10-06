package org.datanucleus.store.types.jodatime.test;

import org.datanucleus.store.types.converters.TypeConverter;
import org.datanucleus.store.types.jodatime.converters.JodaLocalDateSqlDateConverter;
import org.datanucleus.store.types.jodatime.converters.JodaLocalDateStringConverter;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Rodrigo Quesada on 06/10/15.
 */
public class LocalDateTimeTests {

    private static class TimeZones {
        static final String JAPAN = "Asia/Tokyo";
        static final String COSTA_RICA = "America/Costa_Rica";
    }

    private <T> void assert_localDateConversionIsUnaffectedByTimeZoneChanges(TypeConverter<LocalDate, T> converter) {
        LocalDate expectedDate = new LocalDate(2015, 10, 5);

        DateTimeZone.setDefault(DateTimeZone.forID(TimeZones.JAPAN));
        T dataStoreType = converter.toDatastoreType(expectedDate);
        DateTimeZone.setDefault(DateTimeZone.forID(TimeZones.COSTA_RICA));
        LocalDate actualDate = converter.toMemberType(dataStoreType);

        assertThat(actualDate).isEqualTo(expectedDate);
    }

    @Test
    public void localDateConversionIsUnaffectedByTimeZoneChanges() {
        assert_localDateConversionIsUnaffectedByTimeZoneChanges(new JodaLocalDateStringConverter());
        assert_localDateConversionIsUnaffectedByTimeZoneChanges(new JodaLocalDateSqlDateConverter());
    }
}
