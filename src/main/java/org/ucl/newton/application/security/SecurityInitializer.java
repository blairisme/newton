/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Instances of this class configure the Spring MVC security frame used to
 * control access to pages provided by the webapp.
 *
 * @author Blair Butterworth
 */
@Order(value = 2)
@SuppressWarnings("unused")
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}