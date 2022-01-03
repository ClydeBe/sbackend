package com.thewheel.sawatu.database.envers;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPreUpdateEventListenerImpl;

public class AppEnversPreUpdateEventListener extends EnversPreUpdateEventListenerImpl {

    public AppEnversPreUpdateEventListener(EnversService enversService) {
        super(enversService);
    }
}
