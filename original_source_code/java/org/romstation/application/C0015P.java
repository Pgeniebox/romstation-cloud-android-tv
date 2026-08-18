package org.romstation.application;

import java.util.function.Consumer;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: renamed from: org.romstation.application.P */
/* JADX INFO: compiled from: Account.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/P.class */
public class C0015P {
    public Consumer<C0058ae> on_sign_in;
    public Consumer<C0058ae> on_sign_out;

    public boolean isLogged() {
        return C0058ae.m195a().m200e();
    }

    public void signIn() {
        RomStationController.f786a.post(new C0163cc());
    }

    public void signOut() {
        RomStationController.f786a.post(new C0164cd());
    }

    public C0058ae getAccount() {
        if (C0058ae.m195a().m200e()) {
            return C0058ae.m195a();
        }
        return null;
    }
}
