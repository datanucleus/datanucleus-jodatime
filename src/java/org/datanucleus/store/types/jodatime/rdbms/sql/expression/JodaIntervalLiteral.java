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
package org.datanucleus.store.types.jodatime.rdbms.sql.expression;

import org.datanucleus.exceptions.NucleusException;
import org.datanucleus.store.rdbms.mapping.java.JavaTypeMapping;
import org.datanucleus.store.rdbms.sql.SQLStatement;
import org.joda.time.Interval;

/**
 * Representation of an JodaTime "Interval" literal.
 */
public class JodaIntervalLiteral extends JodaLiteral
{

    protected final Interval value;

    /**
     * Constructor for an Interval literal with a value.
     * @param stmt the SQL statement
     * @param mapping the mapping
     * @param value the value
     * @param parameterName Name of the parameter that this represents if any (as JDBC "?")
     */
    public JodaIntervalLiteral(SQLStatement stmt, JavaTypeMapping mapping, Object value, String parameterName)
    {
        super(stmt, mapping, parameterName);

        if (value == null)
        {
            this.value = null;
        }
        else if (value instanceof Interval)
        {
            this.value = (Interval) value;
        }
        else
        {
            throw new NucleusException("Cannot create " + this.getClass().getName() + " for value of type " + 
                value.getClass().getName());
        }
    }

    /**
     * {@inheritDoc}
     */
    public Object getValue()
    {
        return value;
    }
}