package org.ucl.FizzyoDataProvider.Fizzyo.model;
/**
 * Instances of this class provide org.ucl.FizzyoDataProvider.Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class PressureRaw {
    PressureRawRecord pressure;

    public PressureRawRecord getPressure() { return pressure; }
    public void setPressure(PressureRawRecord pressure) { this.pressure = pressure; }
}