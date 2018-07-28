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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucl.newton.common.serialization.JsonSerializer;
import org.ucl.newton.common.serialization.Serializer;

/**
 * Instances of this class implement a REST controller, receiving calls made by
 * {@link ExecutionCoordinatorClient ExecutionCoordinatorClients}.
 *
 * @author Blair Butterworth
 */
@RestController
@SuppressWarnings("unused")
public class ExecutionCoordinatorController
{
    private ExecutionCoordinatorServer delegate;
    private Serializer serializer;

    @Autowired(required = false)
    public ExecutionCoordinatorController(@Nullable ExecutionCoordinatorServer delegate) {
        this.delegate = delegate;
        this.serializer = new JsonSerializer();
    }

    @RequestMapping("/api/experiment/complete")
    public ResponseEntity<?> executionComplete(@RequestBody String requestBody) {
        if (delegate != null) {
            delegate.executionComplete(serializer.deserialize(requestBody, ExecutionResult.class));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping("/api/experiment/failed")
    public ResponseEntity<?> executionFailed(@RequestBody String error) {
        if (delegate != null) {
            delegate.executionFailed(error);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
