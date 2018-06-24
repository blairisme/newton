package org.ucl.newton.application;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(value = 2)
@SuppressWarnings("unused")
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}