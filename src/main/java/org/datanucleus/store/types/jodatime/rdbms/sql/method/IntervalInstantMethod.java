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

import java.util.List;

import org.datanucleus.exceptions.NucleusException;
import org.datanucleus.store.rdbms.mapping.datastore.DatastoreMapping;
import org.datanucleus.store.rdbms.sql.SQLStatement;
import org.datanucleus.store.rdbms.sql.expression.SQLExpression;
import org.datanucleus.store.rdbms.sql.expression.StringTemporalExpression;
import org.datanucleus.store.rdbms.sql.expression.TemporalExpression;
import org.datanucleus.store.rdbms.sql.method.SQLMethod;
import org.datanucleus.store.types.jodatime.rdbms.mapping.JodaIntervalMapping;

/**
 * SQL Method base class to enable the use either the start or end <tt>Instant</tt> of a JodaTime <tt>Interval</tt> in a filter.
 */
public abstract class IntervalInstantMethod implements SQLMethod
{
    protected SQLExpression getExpressionForSingleMapping(SQLStatement stmt, SQLExpression expr, List args, int mappingIndex)
    {
        if (args != null && !args.isEmpty())
        {
            throw new NucleusException("Cannot invoke getStart()/getEnd() with arguments.");
        }

        if (!(expr instanceof StringTemporalExpression))
        {
            throw new NucleusException("Can only be used with 'StringTemporalExpression' expression types.");
        }

        DatastoreMapping datastoreMapping = expr.getJavaTypeMapping().getDatastoreMapping(0);
        Object javaTypeMapping = datastoreMapping.getJavaTypeMapping();
        if (!(javaTypeMapping instanceof JodaIntervalMapping))
        {
            throw new NucleusException("Can only be used with 'JodaIntervalMapping' Java Type mappings.");
        }

        JodaIntervalMapping jodaIntervalMapping = (JodaIntervalMapping) javaTypeMapping;
        JodaIntervalInstantMapping startEndMapping = new JodaIntervalInstantMapping(jodaIntervalMapping, mappingIndex);
        return new TemporalExpression(stmt, expr.getSQLTable(), startEndMapping);
    }
}