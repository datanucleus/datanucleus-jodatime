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
import org.datanucleus.store.rdbms.sql.expression.BooleanExpression;
import org.datanucleus.store.rdbms.sql.expression.SQLExpression;
import org.datanucleus.store.rdbms.sql.expression.SQLLiteral;
import org.datanucleus.store.rdbms.sql.expression.StringTemporalExpression;
import org.joda.time.Interval;

/**
 * Representation of an JodaTime "Interval" literal.
 */
public class JodaIntervalLiteral extends StringTemporalExpression implements SQLLiteral
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
        super(stmt, null, mapping);
        this.parameterName = parameterName;

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

    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.sql.expression.DelegatedExpression#eq(org.datanucleus.store.rdbms.sql.expression.SQLExpression)
     */
    @Override
    public BooleanExpression eq(SQLExpression expr)
    {
        if (expr instanceof JodaIntervalLiteral)
        {
            return super.eq(((JodaIntervalLiteral) expr).delegate);
        }
        return super.eq(expr);
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.sql.expression.DelegatedExpression#ge(org.datanucleus.store.rdbms.sql.expression.SQLExpression)
     */
    @Override
    public BooleanExpression ge(SQLExpression expr)
    {
        if (expr instanceof JodaIntervalLiteral)
        {
            return super.ge(((JodaIntervalLiteral) expr).delegate);
        }
        return super.ge(expr);
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.sql.expression.DelegatedExpression#gt(org.datanucleus.store.rdbms.sql.expression.SQLExpression)
     */
    @Override
    public BooleanExpression gt(SQLExpression expr)
    {
        if (expr instanceof JodaIntervalLiteral)
        {
            return super.gt(((JodaIntervalLiteral) expr).delegate);
        }
        return super.gt(expr);
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.sql.expression.DelegatedExpression#le(org.datanucleus.store.rdbms.sql.expression.SQLExpression)
     */
    @Override
    public BooleanExpression le(SQLExpression expr)
    {
        if (expr instanceof JodaIntervalLiteral)
        {
            return super.le(((JodaIntervalLiteral) expr).delegate);
        }
        return super.le(expr);
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.sql.expression.DelegatedExpression#lt(org.datanucleus.store.rdbms.sql.expression.SQLExpression)
     */
    @Override
    public BooleanExpression lt(SQLExpression expr)
    {
        if (expr instanceof JodaIntervalLiteral)
        {
            return super.lt(((JodaIntervalLiteral) expr).delegate);
        }
        return super.lt(expr);
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.sql.expression.DelegatedExpression#ne(org.datanucleus.store.rdbms.sql.expression.SQLExpression)
     */
    @Override
    public BooleanExpression ne(SQLExpression expr)
    {
        if (expr instanceof JodaIntervalLiteral)
        {
            return super.ne(((JodaIntervalLiteral) expr).delegate);
        }
        return super.ne(expr);
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.sql.expression.SQLExpression#isParameter()
     */
    @Override
    public boolean isParameter()
    {
        return delegate.isParameter();
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.sql.expression.SQLLiteral#setNotParameter()
     */
    public void setNotParameter()
    {
        ((SQLLiteral)delegate).setNotParameter();
    }

    public Object getValue()
    {
        return value;
    }
}