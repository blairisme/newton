package org.ucl.newton.fizzyo.model;
/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class BasicData {
    private String time;
    private int value;

    @Override
    public String toString() {
        return time+":"+value;
    }

}
