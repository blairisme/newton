/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Instances of this class implement a REST controller, receiving calls made by
 * {@link ExecutionCoordinatorClient ExecutionCoordinatorClients}.
 *
 * @author Blair Butterworth
 */
@RestController
public class ExecutionCoordinatorController
{
    private ExecutionCoordinatorServer delegate;

    @Autowired(required = false)
    public ExecutionCoordinatorController(@Nullable ExecutionCoordinatorServer delegate) {
        this.delegate = delegate;
    }

    @PostMapping("/complete")
    public ResponseEntity<?> executionComplete(@RequestBody ExecutionResult executionResult) {
        if (delegate != null) {
            delegate.executionComplete(executionResult);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
