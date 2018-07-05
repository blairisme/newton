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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.ucl.newton.bridge.ExecutionCoordinator;
import org.ucl.newton.bridge.ExecutionResult;

/**
 * Instances of this class represent the system controlling remote execution on
 * {@link ExecutionNode ExecutionNodes}.
 *
 * @author Blair Butterworth
 */
@RestController
public class ExecutionCoordinatorServer
{
    private ExecutionCoordinator delegate;

    @Autowired(required = false)
    public ExecutionCoordinatorServer(@Nullable @Qualifier(value="server") ExecutionCoordinator delegate) {
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
