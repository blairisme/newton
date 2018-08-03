package org.ucl.FizzyoDataProvider.Fizzyo.model;
/**
 * Instances of this class provide org.ucl.FizzyoDataProvider.Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class FizzyoUser {
    private String id;
    private String firstName;
    private String lastName;
    private String role;
    private String patientRecordId;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String getPatientRecordId() { return patientRecordId; }

    public void setPatientRecordId(String patientRecordId) { this.patientRecordId = patientRecordId; }
}
