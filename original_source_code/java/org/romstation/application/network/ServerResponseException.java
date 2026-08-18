package org.romstation.application.network;

import java.text.MessageFormat;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/network/ServerResponseException.class */
public class ServerResponseException extends Exception {

    /* JADX INFO: renamed from: a */
    private C0219d f580a;

    public ServerResponseException(C0219d serverResponse) {
        super(MessageFormat.format("the server returned the error code {0}", Integer.valueOf(serverResponse.m965a())));
        this.f580a = serverResponse;
    }

    /* JADX INFO: renamed from: a */
    public C0219d m955a() {
        return this.f580a;
    }
}
