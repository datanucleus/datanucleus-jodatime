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

import java.sql.Timestamp;

import org.datanucleus.store.types.converters.TypeConverter;
import org.joda.time.DateTime;

/**
 * TypeConverter from Joda DateTime to java.sql.Timestamp.
 */
public class JodaDateTimeTimestampConverter implements TypeConverter<DateTime, Timestamp>
{
    private static final long serialVersionUID = 6031764409526208264L;

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toDatastoreType(java.lang.Object)
     */
    public Timestamp toDatastoreType(DateTime dt)
    {
        if (dt == null)
        {
            return null;
        }
        return new Timestamp(dt.getMillis());
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toMemberType(java.lang.Object)
     */
    public DateTime toMemberType(Timestamp ts)
    {
        if (ts == null)
        {
            return null;
        }

        return new DateTime(ts.getTime());
    }
}