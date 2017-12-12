/**
 * Copyright (c) 2014,2017 by the respective copyright holders.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.woehlke;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link WoehlkeBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Lukas Smirek - Initial contribution
 */
 @NonNullByDefault
public class WoehlkeBindingConstants {

    private static final String BINDING_ID = "woehlke";

    // List of all Thing Type UIDs
    public static final String THING_TYPE_WEBSTECKDOSE = "websteckdose";

    // List of all Channel ids
    public static final String CHANNEL_POWER_OUTLET_1 = "powerOutlet1";
    public static final String CHANNEL_POWER_OUTLET_2 = "powerOutlet2";
    public static final String CHANNEL_POWER_OUTLET_3 = "powerOutlet3";
    public static final String CHANNEL_temperature = "temperature3";
}


}
