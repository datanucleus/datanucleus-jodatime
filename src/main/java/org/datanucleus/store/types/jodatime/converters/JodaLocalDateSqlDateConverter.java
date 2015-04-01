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

import java.sql.Date;

import org.datanucleus.store.types.converters.TypeConverter;
import org.joda.time.LocalDate;

/**
 * TypeConverter from Joda LocalDate to java.sql.Date.
 */
public class JodaLocalDateSqlDateConverter implements TypeConverter<LocalDate, Date>
{
    private static final long serialVersionUID = 4411382310833638172L;

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toDatastoreType(java.lang.Object)
     */
    public Date toDatastoreType(LocalDate ld)
    {
        if (ld == null)
        {
            return null;
        }
        return new Date(ld.toDateTimeAtStartOfDay().getMillis());
//        return new Date(ld.getYear(), ld.getMonthOfYear(), ld.getDayOfMonth());
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toMemberType(java.lang.Object)
     */
    public LocalDate toMemberType(Date date)
    {
        if (date == null)
        {
            return null;
        }

        return new LocalDate(date.getTime());
    }
}
