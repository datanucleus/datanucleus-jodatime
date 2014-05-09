/**********************************************************************
Copyright (c) 2014 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
   ...
**********************************************************************/
package org.datanucleus.store.types.jodatime.converters;

import java.sql.Time;

import org.datanucleus.store.types.converters.TypeConverter;
import org.joda.time.LocalTime;

/**
 * TypeConverter from Joda LocalTime to java.sql.Time.
 */
public class JodaLocalTimeSqlTimeConverter implements TypeConverter<LocalTime, Time>
{
    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toDatastoreType(java.lang.Object)
     */
    @SuppressWarnings("deprecation")
    public Time toDatastoreType(LocalTime lt)
    {
        if (lt == null)
        {
            return null;
        }
        return new Time(lt.getHourOfDay(), lt.getMinuteOfHour(), lt.getSecondOfMinute());
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toMemberType(java.lang.Object)
     */
    public LocalTime toMemberType(Time time)
    {
        if (time == null)
        {
            return null;
        }
        return new LocalTime(time.getTime());
    }
}
