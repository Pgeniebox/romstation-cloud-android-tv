package org.romstation.application.view.control;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import org.romstation.application.RomStation;
import org.romstation.application.network.ServerResponseException;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/ServerErrorAlert.class */
public class ServerErrorAlert extends ApplicationAlert {
    public ServerErrorAlert(ServerResponseException exception) {
        super(Alert.AlertType.ERROR);
        ResourceBundle resources = RomStation.m44d();
        switch (exception.m955a().m965a()) {
            case -98:
                setHeaderText(resources.getString("connectionRequiredAlert.header"));
                setContentText(resources.getString("connectionRequiredAlert.content"));
                break;
            case -92:
                setHeaderText(resources.getString("cloudServersUnavailableAlert.header"));
                setContentText(resources.getString("cloudServersUnavailableAlert.content"));
                break;
            case -91:
                setHeaderText(resources.getString("cloudServiceDisabledAlert.header"));
                setContentText(resources.getString("cloudServiceDisabledAlert.content"));
                break;
            case -89:
                setHeaderText(resources.getString("dedicatedServerCreationDisabledAlert.header"));
                setContentText(resources.getString("dedicatedServerCreationDisabledAlert.content"));
                break;
            case -87:
                setHeaderText(resources.getString("applicationUpdateRequiredAlert.header"));
                setContentText(resources.getString("applicationUpdateRequiredAlert.content"));
                break;
            case -81:
                setHeaderText(resources.getString("dedicatedServerCreationLimitReachedAlert.header"));
                setContentText(resources.getString("dedicatedServerCreationLimitReachedAlert.content"));
                break;
            case -78:
                setHeaderText(resources.getString("netplayInvalidServerPasswordAlert.header"));
                setContentText(resources.getString("netplayInvalidServerPasswordAlert.content"));
                break;
            case -49:
                setHeaderText(resources.getString("netplayServerLockedAlert.header"));
                setContentText(resources.getString("netplayServerLockedAlert.content"));
                break;
            case -43:
                setHeaderText(resources.getString("netplayServerFullAlert.header"));
                setContentText(resources.getString("netplayServerFullAlert.content"));
                break;
            case -40:
                setHeaderText(resources.getString("netplayBannedAlert.header"));
                setContentText(resources.getString("netplayBannedAlert.content"));
                break;
            case -38:
                setHeaderText(resources.getString("netplayServerNotFoundAlert.header"));
                setContentText(resources.getString("netplayServerNotFoundAlert.content"));
                break;
            case -35:
                setHeaderText(resources.getString("netplayServiceUnavailableAlert.header"));
                setContentText(resources.getString("netplayServiceUnavailableAlert.content"));
                break;
            case -26:
                setHeaderText(resources.getString("uploadDisabledAlert.header"));
                setContentText(resources.getString("uploadDisabledAlert.content"));
                break;
            case -24:
                setHeaderText(resources.getString("uploadDuplicateAlert.header"));
                setContentText(resources.getString("uploadDuplicateAlert.content"));
                break;
            default:
                setHeaderText(resources.getString("unknownErrorAlert.header"));
                setContentText(String.format(resources.getString("unknownErrorAlert.content"), Integer.valueOf(exception.m955a().m965a())));
                break;
        }
    }
}
