/**********************************************************************
Copyright (c) 2009 Andy Jefferson and others. All rights reserved.
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
package org.datanucleus.store.types.jodatime.rdbms.mapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.datanucleus.ClassLoaderResolver;
import org.datanucleus.ClassNameConstants;
import org.datanucleus.ExecutionContext;
import org.datanucleus.NucleusContext;
import org.datanucleus.exceptions.NucleusUserException;
import org.datanucleus.metadata.AbstractMemberMetaData;
import org.datanucleus.metadata.ColumnMetaData;
import org.datanucleus.store.rdbms.mapping.MappingManager;
import org.datanucleus.store.rdbms.mapping.java.SingleFieldMultiMapping;
import org.datanucleus.store.rdbms.table.Column;
import org.datanucleus.store.rdbms.table.Table;
import org.datanucleus.store.rdbms.RDBMSStoreManager;
import org.datanucleus.store.types.converters.TypeConverter;
import org.joda.time.Interval;

/**
 * SCO Mapping for org.joda.time.Interval type.
 * Can be persisted using either
 * <ul>
 * <li>Single column using a String mapping.</li>
 * <li>Two columns using Timestamp mappings for the start/end components</li>
 * </ul>
 * See http://joda-time.sourceforge.net/
 * TODO Remove this and use TypeConverters
 */
public class JodaIntervalMapping extends SingleFieldMultiMapping
{
    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.mapping.JavaTypeMapping#initialize(AbstractMemberMetaData, DatastoreContainerObject, ClassLoaderResolver)
     */
    public void initialize(AbstractMemberMetaData fmd, Table table, ClassLoaderResolver clr)
    {
        super.initialize(fmd, table, clr);
        addColumns();
    }

    /*
     * (non-Javadoc)
     * @see org.datanucleus.store.rdbms.mapping.JavaTypeMapping#initialize(RDBMSStoreManager,
     * java.lang.String)
     */
    public void initialize(RDBMSStoreManager storeMgr, String type)
    {
        super.initialize(storeMgr, type);
        addColumns();
    }

    protected void addColumns()
    {
        ColumnMetaData[] colmds = getColumnMetaDataForMember(mmd, roleForMember);
        if (colmds != null && colmds.length == 1)
        {
            // Stored as single column, so use String based
            MappingManager mmgr = table.getStoreManager().getMappingManager();
            Column col = mmgr.createColumn(this, ClassNameConstants.JAVA_LANG_STRING, 0);
            mmgr.createColumnMapping(this, mmd, 0, col);
        }
        else
        {
            // Store as 2 TIMESTAMP columns
            addColumns(ClassNameConstants.JAVA_SQL_TIMESTAMP); // start
            addColumns(ClassNameConstants.JAVA_SQL_TIMESTAMP); // end
        }
    }

    /**
     * Accessor for the name of the java-type actually used when mapping the particular datastore field. This
     * java-type must have an entry in the datastore mappings.
     * @param index requested datastore field index.
     * @return the name of java-type for the requested datastore field.
     */
    public String getJavaTypeForColumnMapping(int index)
    {
        if (columnMappings != null && columnMappings.length == 1 && columnMappings[0].isStringBased())
        {
            // STRING
            return ClassNameConstants.JAVA_LANG_STRING;
        }
        // TIMESTAMP
        return ClassNameConstants.JAVA_SQL_TIMESTAMP;
    }

    public Class getJavaType()
    {
        return Interval.class;
    }

    /**
     * Method to return the value to be stored in the specified datastore index given the overall value for this java type.
     * @param nucleusCtx NucleusContext
     * @param index The datastore index
     * @param value The overall value for this java type
     * @return The value for this datastore index
     */
    public Object getValueForColumnMapping(NucleusContext nucleusCtx, int index, Object value)
    {
        Interval intvl = (Interval) value;
        if (getNumberOfColumnMappings() == 1)
        {
            return super.getValueForColumnMapping(nucleusCtx, index, value);
        }
        else if (index == 0)
        {
            return intvl.getStartMillis();
        }
        else if (index == 1)
        {
            return intvl.getEndMillis();
        }
        throw new IndexOutOfBoundsException();
    }

    public Object getObject(ExecutionContext ec, ResultSet rs, int[] exprIndex)
    {
        if (getColumnMapping(0).getObject(rs, exprIndex[0]) == null)
        {
            return null;
        }

        if (columnMappings != null && columnMappings.length == 1 && columnMappings[0].isStringBased())
        {
            // String column
            Object datastoreValue = getColumnMapping(0).getObject(rs, exprIndex[0]);
            TypeConverter conv = ec.getNucleusContext().getTypeManager().getTypeConverterForType(Interval.class, String.class);
            if (conv != null)
            {
                return conv.toMemberType(datastoreValue);
            }
            throw new NucleusUserException("This type doesn't support persistence as a String");
        }

        // Timestamp columns
        Timestamp start = (Timestamp) getColumnMapping(0).getObject(rs, exprIndex[0]);
        Timestamp end = (Timestamp) getColumnMapping(1).getObject(rs, exprIndex[1]);
        return new Interval(start.getTime(), end.getTime());
    }

    public void setObject(ExecutionContext ec, PreparedStatement ps, int[] exprIndex, Object value)
    {
        Interval interval = (Interval) value;

        if (columnMappings != null && columnMappings.length == 1 && columnMappings[0].isStringBased())
        {
            if (value == null)
            {
                getColumnMapping(0).setObject(ps, exprIndex[0], null);
            }
            else
            {
                // String column
                TypeConverter conv = ec.getNucleusContext().getTypeManager().getTypeConverterForType(Interval.class, String.class);
                if (conv != null)
                {
                    getColumnMapping(0).setObject(ps, exprIndex[0], conv.toDatastoreType(value));
                }
                else
                {
                    throw new NucleusUserException("This type doesn't support persistence as a String");
                }
            }
        }
        else
        {
            // Timestamp columns
            if (interval == null)
            {
                getColumnMapping(0).setObject(ps, exprIndex[0], null);
                getColumnMapping(1).setObject(ps, exprIndex[1], null);
            }
            else
            {
                getColumnMapping(0).setObject(ps, exprIndex[0], new Timestamp(interval.getStartMillis()));
                getColumnMapping(1).setObject(ps, exprIndex[1], new Timestamp(interval.getEndMillis()));
            }
        }
    }
}