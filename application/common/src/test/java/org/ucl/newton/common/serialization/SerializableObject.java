/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.serialization;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Utility class used by serialization test classes.
 *
 * @author Blair Butterworth
 */
@XmlRootElement(name = "serialized")
@SuppressWarnings("unused")
class SerializableObject {
    private String name;
    private String address;

    public SerializableObject() {
    }

    public SerializableObject(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
