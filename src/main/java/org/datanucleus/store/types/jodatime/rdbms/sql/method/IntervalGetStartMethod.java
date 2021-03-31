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

import org.datanucleus.store.rdbms.sql.SQLStatement;
import org.datanucleus.store.rdbms.sql.expression.SQLExpression;

/**
 * SQL Method to enable the use the start instant of an JodaTime <i>Interval</i> in a filter.
 * 
 * All the actual work happens in the {@link IntervalInstantMethod}.
 */
public class IntervalGetStartMethod extends IntervalInstantMethod
{
    public SQLExpression getExpression(SQLStatement stmt, SQLExpression expr, List args)
    {
        return getExpressionForSingleMapping(stmt, expr, args, 0);
    }
}