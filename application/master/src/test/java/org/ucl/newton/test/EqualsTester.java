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

/**
 * Instances of this class test an objects equals and hashcode methods.
 *
 * @param <T> the type of object being tested.
 *
 * @author Blair Butterworth
 */
public class EqualsTester <T>
{
    private T objectA;
    private T objectB;
    private T objectC;
    private T objectD;

    public EqualsTester() {
    }

    public void forType(Class<T> type) {
        T sameObject = ObjectBuilder.newInstance(type);
        T diffObject = ObjectBuilder.newInstance(type);

        this.objectA = sameObject;
        this.objectB = sameObject;
        this.objectC = sameObject;
        this.objectD = diffObject;
    }

    public void setEqualObjects(T objectA, T objectB, T objectC) {
        this.objectA = objectA;
        this.objectB = objectB;
        this.objectC = objectC;
    }

    public void setDifferentObject(T objectD) {
        this.objectD = objectD;
    }

    public void test() {
        Validate.notNull(objectA);
        Validate.notNull(objectB);
        Validate.notNull(objectC);
        Validate.notNull(objectD);

        reflexiveTest();
        symmetricTest();
        transitiveTest();
        nullTest();
        typeTest();
        equalsTest();
        hashCodeTest();
    }

    private void reflexiveTest() {
        Assert.assertEquals("Object not reflexively equal", objectA, objectA);
    }

    private void symmetricTest() {
        Assert.assertEquals("Objects not symmetrically equal", objectA, objectB);
        Assert.assertEquals("Objects not symmetrically equal", objectB, objectA);
    }

    private void transitiveTest() {
        Assert.assertEquals("Objects not transitively equal", objectA, objectB);
        Assert.assertEquals("Objects not transitively equal", objectB, objectC);
        Assert.assertEquals("Objects not transitively equal", objectC, objectA);
    }

    @SuppressWarnings("all")
    private void nullTest() {
        Assert.assertFalse("Object shouldn't equal null", objectA.equals(null));
    }

    private void typeTest() {
        Assert.assertFalse("Objects should not be equal", objectA.equals(123));
    }

    private void equalsTest() {
        Assert.assertFalse("Objects should not be equal", objectA.equals(objectD));
    }

    private void hashCodeTest() {
        Assert.assertEquals("Objects should be equal", objectA, objectB);
        Assert.assertEquals("Hash codes should be the equal for equal objects", objectA.hashCode(), objectB.hashCode());
    }
}
