/**********************************************************************
Copyright (c) 2012 Jasper Siepkes and others. All rights reserved.
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
package org.datanucleus.store.types.jodatime.rdbms.sql.method;

import org.datanucleus.store.rdbms.mapping.datastore.ColumnMapping;
import org.datanucleus.store.rdbms.mapping.java.DateMapping;
import org.datanucleus.store.types.jodatime.rdbms.mapping.JodaIntervalMapping;
import org.joda.time.Interval;

/**
 * This is a pseudo mapping class used to be able to query on the start or end <tt>Instant</tt> (usually a
 * <tt>DateTime</tt> type) of an <tt>Interval</tt>. This mapping class pretends that the mapping only has one field
 * (either the start or end instant) instead of multiple.
 */
final class JodaIntervalInstantMapping extends DateMapping
{
    /** The actual Jodatime Interval mapping. */
    private final JodaIntervalMapping jodaIntervalMapping;

    /** The index of the mapping we should return. */
    private final int mappingIndex;

    /**
     * @param jodaIntervalMapping The <tt>JodaIntervalMapping</tt> instance we are getting our data from.
     * @param mappingIndex The index of the mapping in the previously supplied <tt>JodaIntervalMapping</tt> instance we
     * should return (ie. the mapping we pretend is the only that exists).
     */
    public JodaIntervalInstantMapping(JodaIntervalMapping jodaIntervalMapping, int mappingIndex)
    {
        this.jodaIntervalMapping = jodaIntervalMapping;
        this.mappingIndex = mappingIndex;
    }

    public ColumnMapping[] getColumnMappings()
    {
        ColumnMapping[] startColumnMapping = {jodaIntervalMapping.getColumnMappings()[mappingIndex]};
        return startColumnMapping;
    }

    public ColumnMapping getColumnMapping(int index)
    {
        return jodaIntervalMapping.getColumnMappings()[mappingIndex];
    }

    public int getNumberOfColumnMappings()
    {
        return 1;
    }

    public Class getJavaType()
    {
        return Interval.class;
    }
}