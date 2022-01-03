package com.thewheel.sawatu.database.envers.engine.processors;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPreUpdateEventListenerImpl;
import org.hibernate.event.spi.PreUpdateEvent;

public class AppEnversPreUpdateListener extends EnversPreUpdateEventListenerImpl {

    public AppEnversPreUpdateListener(EnversService enversService) {
        super(enversService);
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
//        event.get
        return super.onPreUpdate(event);
    }
}
