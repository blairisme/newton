package org.ucl.FizzyoDataProvider.Fizzyo.model;

import java.util.List;

/**
 * Instances of this class provide org.ucl.FizzyoDataProvider.Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class Records {
    List<PacientRecord> records;

    public List<PacientRecord> getRecords() { return records; }

    public void setRecords(List<PacientRecord> records) { this.records = records; }
}
