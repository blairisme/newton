/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this class provide access to persisted slave connection
 * details.
 *
 * @author Blair Butterworth
 */
@Repository
public class SlaveRepository
{
    public SlaveRepository() {
    }

    public Collection<SlaveDetails> getSlaveDetails() {
        Collection<SlaveDetails> details = new ArrayList<>();
        details.add(new SlaveDetails("http://51.140.167.6:8080", "", ""));
        return details;
    }
}
