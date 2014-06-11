/**********************************************************************
Copyright (c) 2012 Andy Jefferson and others. All rights reserved.
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

import org.datanucleus.store.types.converters.TypeConverter;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;

/**
 * TypeConverter from Joda Period to String.
 */
public class JodaPeriodStringConverter implements TypeConverter<Period, String>
{
    private static final long serialVersionUID = -6320395198963801769L;

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toDatastoreType(java.lang.Object)
     */
    public String toDatastoreType(Period per)
    {
        return per != null ? per.toString(ISOPeriodFormat.standard()) : null;
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.types.converters.TypeConverter#toMemberType(java.lang.Object)
     */
    public Period toMemberType(String str)
    {
        if (str == null)
        {
            return null;
        }

        return new Period(str);
    }
}