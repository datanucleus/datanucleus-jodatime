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

import org.datanucleus.store.types.converters.MultiColumnConverter;
import org.datanucleus.store.types.converters.TypeConverter;
import org.joda.time.Interval;

/**
 * TypeConverter from Joda Interval to Timestamp[].
 */
public class JodaIntervalTimestampsConverter implements TypeConverter<Interval, Timestamp[]>, MultiColumnConverter
{
    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toDatastoreType(java.lang.Object)
     */
    public Timestamp[] toDatastoreType(Interval itv)
    {
        if (itv == null)
        {
            return null;
        }
        Timestamp[] timestamps = new Timestamp[2];
        timestamps[0] = new Timestamp(itv.getStartMillis());
        timestamps[1] = new Timestamp(itv.getEndMillis());
        return timestamps;
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toMemberType(java.lang.Object)
     */
    public Interval toMemberType(Timestamp[] timestamps)
    {
        if (timestamps == null)
        {
            return null;
        }
        return new Interval(timestamps[0].getTime(), timestamps[1].getTime());
    }

    public Class[] getDatastoreColumnTypes()
    {
        return new Class[]{Timestamp.class, Timestamp.class};
    }
}