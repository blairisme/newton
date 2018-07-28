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
import org.ucl.newton.common.serialization.JsonSerializer;
import org.ucl.newton.common.serialization.Serializer;

/**
 * Instances of this class implement a REST controller, receiving calls made by
 * {@link ExecutionNodeClient ExecutionNodeClients}.
 *
 * @author Blair Butterworth
 */
@RestController
@SuppressWarnings("unused")
public class ExecutionNodeController
{
    private ExecutionNodeServer delegate;
    private Serializer serializer;

    @Autowired
    public ExecutionNodeController(@Nullable ExecutionNodeServer delegate) {
        this.delegate = delegate;
        this.serializer = new JsonSerializer();
    }

    @PostMapping("/api/experiment/execute")
    public ResponseEntity<?> execute(@RequestBody String requestBody) {
        if (delegate != null) {
            delegate.execute(serializer.deserialize(requestBody, ExecutionRequest.class));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/experiment/cancel")
    public ResponseEntity<?> cancel(@RequestBody String requestBody) {
        if (delegate != null) {
            delegate.execute(serializer.deserialize(requestBody, ExecutionRequest.class));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


