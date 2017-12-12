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
package org.openhab.binding.woehlke.handler;

import static org.openhab.binding.woehlke.WoehlkeBindingConstants.*;

import java.math.BigDecimal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.woehlke.WoehlkeBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lusm.whoelke.electricityoutlet.ElectricityOutletHandler;

/**
 * The {@link WoehlkeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Lukas Smirek - Initial contribution
 */
@NonNullByDefault
public class WoehlkeHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(WoehlkeHandler.class);

    org.openhab.binding.woehlke.internal.ElectricityOutletHandler eoh;

    public WoehlkeHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

        boolean setting;
        if (command instanceof OnOffType) {
            setting = OnOffType.ON.equals(command);
        }
        switch (channelUID.getId()) {

            case WoehlkeBindingConstants.CHANNEL_POWER_OUTLET_1:
                eoh.setSocket1(setting);
                break;

            case WoehlkeBindingConstants.CHANNEL_POWER_OUTLET_2:
                eoh.setSocket2(setting);
                break;

            case WoehlkeBindingConstants.CHANNEL_POWER_OUTLET_3:
                eoh.setSocket3(setting);
                break;

        }
        // Note: if communication with thing fails for some reason,
        // indicate that by setting the status with detail information
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
        // "Could not control device at IP address x.x.x.x");
    }

    }

    @Override
    public void initialize() {

        Configuration config = getThing().getConfiguration();
    String host =( (String) config.get(Constants.PROPERTY_HOST));
        String port = (BigDecimal) config.get(PROPERTY_NTP_SERVER_PORT);
        refreshInterval = (BigDecimal) config.get(PROPERTY_REFRESH_INTERVAL);
        eoh = new org.openhab.binding.woehlke.internal.ElectricityOutletHandler(host,port)

        // Long running initialization should be done asynchronously in background.
        updateStatus(ThingStatus.ONLINE);

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }
}
