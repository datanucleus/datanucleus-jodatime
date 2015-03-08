/**********************************************************************
Copyright (c) 2015 Andy Jefferson and others. All rights reserved.
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

import org.datanucleus.store.types.converters.ColumnLengthDefiningTypeConverter;
import org.datanucleus.store.types.converters.TypeConverter;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * TypeConverter from Joda LocalDateTime to String.
 */
public class JodaLocalDateTimeStringConverter implements TypeConverter<LocalDateTime, String>, ColumnLengthDefiningTypeConverter
{
    private static final long serialVersionUID = 6490626063186033515L;

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toDatastoreType(java.lang.Object)
     */
    public String toDatastoreType(LocalDateTime lt)
    {
        return lt != null ? lt.toString() : null;
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toMemberType(java.lang.Object)
     */
    public LocalDateTime toMemberType(String str)
    {
        if (str == null)
        {
            return null;
        }

        return ISODateTimeFormat.dateTime().parseLocalDateTime(str);
    }

    public int getDefaultColumnLength(int columnPosition)
    {
        if (columnPosition != 0)
        {
            return -1;
        }
        // Persist as "yyyy-MM-ddTHH:mm:ss.SSS" when stored as string
        // e.g "2009-05-13T07:09:26.000"
        return 23;
    }
}
