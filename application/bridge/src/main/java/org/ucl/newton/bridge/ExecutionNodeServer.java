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

/**
 * Instances of this class represent a remote system capable of executing an
 * experiment.
 *
 * @author Blair Butterworth
 */
@RestController
public class ExecutionNodeServer
{
    private ExecutionNode delegate;

    @Autowired
    public ExecutionNodeServer(@Nullable @Qualifier(value="server") ExecutionNode delegate) {
        this.delegate = delegate;
    }

    @PostMapping("/execute")
    public ResponseEntity<?> execute(@RequestBody ExecutionRequest executionRequest)
    {
        if (delegate != null) {
            delegate.execute(executionRequest);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
