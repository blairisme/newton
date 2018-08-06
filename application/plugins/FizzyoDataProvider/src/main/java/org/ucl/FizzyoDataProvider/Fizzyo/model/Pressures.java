package org.ucl.FizzyoDataProvider.Fizzyo.model;

import java.util.List;

/**
 * Represents a record type that stores pressure information.
 *
 * @author Xiaolong Chen
 */
public class Pressures {
    private List<PressureRecord> pressure;

    public List<PressureRecord> getPressure() { return pressure; }

    public void setPressure(List<PressureRecord> pressure) { this.pressure = pressure; }
}
