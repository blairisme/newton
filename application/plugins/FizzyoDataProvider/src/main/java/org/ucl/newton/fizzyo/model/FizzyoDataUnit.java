package org.ucl.newton.fizzyo.model;

import java.util.List;
/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */

public abstract class FizzyoDataUnit {
    public abstract List<String> getKeys();
    public abstract List<String> getValues();
}
