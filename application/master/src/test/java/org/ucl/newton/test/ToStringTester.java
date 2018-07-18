/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.test;

import org.apache.commons.lang3.Validate;
import org.junit.Assert;

public class ToStringTester <T>
{
    private T object;

    public ToStringTester(){
    }

    public void forType(Class<T> type) {
        this.object = ObjectBuilder.newInstance(type);
    }

    public void setObject(T object){
        this.object = object;
    }

    public void test() {
        Validate.notNull(object);
        Assert.assertNotNull(object.toString());
    }
}
