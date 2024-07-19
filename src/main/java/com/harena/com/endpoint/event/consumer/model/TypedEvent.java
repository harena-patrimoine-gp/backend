package com.harena.com.endpoint.event.consumer.model;

import com.harena.com.PojaGenerated;
import com.harena.com.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
