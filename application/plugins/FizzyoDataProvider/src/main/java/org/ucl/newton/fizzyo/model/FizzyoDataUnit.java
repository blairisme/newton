package org.ucl.newton.fizzyo.model;

import java.util.List;

/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public interface FizzyoDataUnit {
    List<String> getKeys();
    List<String> getValues();
}
