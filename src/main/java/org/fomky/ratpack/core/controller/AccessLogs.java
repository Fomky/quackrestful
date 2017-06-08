package org.fomky.ratpack.core.controller;

import ratpack.handling.Chain;
import ratpack.handling.RequestLogger;
import ratpack.handling.RequestOutcome;

public abstract class AccessLogs implements CoreHandle {
    @Override
    public void execute(Chain chain) throws Exception {
        chain.all(RequestLogger.of(this::access));
    }

    abstract void access(RequestOutcome outcome);
}
