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
package org.datanucleus.store.rdbms.mapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.datanucleus.ClassNameConstants;
import org.datanucleus.ExecutionContext;
import org.datanucleus.exceptions.NucleusUserException;
import org.datanucleus.metadata.ColumnMetaData;
import org.datanucleus.metadata.MetaDataUtils;
import org.datanucleus.store.rdbms.mapping.java.SingleFieldMapping;
import org.datanucleus.store.types.converters.TypeConverter;
import org.joda.time.Duration;

/**
 * SCO Mapping for org.joda.time.Duration type.
 * Can be persisted using either
 * <ul>
 * <li>Single column using a String mapping.</li>
 * <li>Single column using a Long mapping.</li>
 * </ul>
 * See http://joda-time.sourceforge.net/
 */
public class JodaDurationMapping extends SingleFieldMapping
{
    public Class getJavaType()
    {
        return Duration.class;
    }

    /**
     * Accessor for the name of the java-type actually used when mapping the particular datastore
     * field. This java-type must have an entry in the datastore mappings.
     * @param index requested datastore field index.
     * @return the name of java-type for the requested datastore field.
     */
    public String getJavaTypeForDatastoreMapping(int index)
    {
        if (datastoreMappings == null || datastoreMappings.length == 0)
        {
            // Not got mappings yet so use column metadata to define
            ColumnMetaData[] colmds = getColumnMetaDataForMember(mmd, roleForMember);
            boolean useString = false;
            if (colmds != null && colmds.length > 0 && colmds[0].getJdbcType() != null && MetaDataUtils.isJdbcTypeString(colmds[0].getJdbcType()))
            {
                useString = true;
            }
            return (useString ? ClassNameConstants.JAVA_LANG_STRING : ClassNameConstants.JAVA_LANG_LONG);
        }
        else if (datastoreMappings[0].isStringBased())
        {
            // Use String
            return ClassNameConstants.JAVA_LANG_STRING;
        }
        else
        {
            // Use Long
            return ClassNameConstants.JAVA_LANG_LONG;
        }
    }

    /**
     * Method to set the object when updating the the datastore.
     * @see org.datanucleus.store.rdbms.mapping.java.SingleFieldMapping#setObject(org.datanucleus.ExecutionContext, PreparedStatement, int[], java.lang.Object)
     */
    public void setObject(ExecutionContext ec, PreparedStatement ps, int[] exprIndex, Object value)
    {
        if (datastoreMappings != null && datastoreMappings.length > 0 && datastoreMappings[0].isStringBased())
        {
            if (value == null)
            {
                getDatastoreMapping(0).setObject(ps, exprIndex[0], null);
            }
            else
            {
                TypeConverter conv = ec.getNucleusContext().getTypeManager().getTypeConverterForType(Duration.class, String.class);
                if (conv != null)
                {
                    Object obj = conv.toDatastoreType(value);
                    getDatastoreMapping(0).setObject(ps, exprIndex[0], obj);
                }
                else
                {
                    throw new NucleusUserException("This type doesn't support persistence as a String");
                }
            }
        }
        else
        {
            if (value == null)
            {
                getDatastoreMapping(0).setObject(ps, exprIndex[0], null);
            }
            else
            {
                getDatastoreMapping(0).setObject(ps, exprIndex[0],
                    Long.valueOf(((Duration)value).getMillis()));
            }
        }
    }

    /**
     * Method to get the object from the datastore and convert to an object.
     * @see org.datanucleus.store.rdbms.mapping.java.SingleFieldMapping#getObject(org.datanucleus.ExecutionContext, ResultSet, int[])
     */
    public Object getObject(ExecutionContext ec, ResultSet resultSet, int[] exprIndex)
    {
        if (exprIndex == null)
        {
            return null;
        }

        Object datastoreValue = getDatastoreMapping(0).getObject(resultSet, exprIndex[0]);
        if (datastoreMappings != null && datastoreMappings.length > 0 && datastoreMappings[0].isStringBased())
        {
            TypeConverter conv = ec.getNucleusContext().getTypeManager().getTypeConverterForType(Duration.class, String.class);
            if (conv != null)
            {
                return conv.toMemberType((String)datastoreValue);
            }
            else
            {
                throw new NucleusUserException("This type doesn't support persistence as a String");
            }
        }
        else
        {
            return new Duration(((Number)datastoreValue).longValue());
        }
    }
}