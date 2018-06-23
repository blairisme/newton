/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * list and manage experiments.
 *
 * @author Blair Butterworth
 */
@Controller
@Scope("session")
@RequestMapping("/")
@SuppressWarnings("unused")
public class ExperimentController
{
}
