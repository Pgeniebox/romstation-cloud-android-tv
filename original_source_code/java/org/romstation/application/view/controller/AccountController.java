package org.romstation.application.view.controller;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamdev.jxbrowser.cookie.Cookie;
import com.teamdev.jxbrowser.js.JsObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.romstation.application.C0004E;
import org.romstation.application.C0013N;
import org.romstation.application.C0058ae;
import org.romstation.application.C0060ag;
import org.romstation.application.C0107bZ;
import org.romstation.application.C0150cP;
import org.romstation.application.C0152cR;
import org.romstation.application.C0153cS;
import org.romstation.application.C0161ca;
import org.romstation.application.C0162cb;
import org.romstation.application.C0163cc;
import org.romstation.application.C0164cd;
import org.romstation.application.C0168ch;
import org.romstation.application.EnumC0059af;
import org.romstation.application.RomStation;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/AccountController.class */
public class AccountController {

    /* JADX INFO: renamed from: a */
    private C0058ae f775a;

    /* JADX INFO: renamed from: b */
    private static final PseudoClass f776b = PseudoClass.getPseudoClass("regular");

    /* JADX INFO: renamed from: c */
    private static final PseudoClass f777c = PseudoClass.getPseudoClass("premium");

    /* JADX INFO: renamed from: d */
    private static final PseudoClass f778d = PseudoClass.getPseudoClass("platinum");

    @FXML
    private Button connectionButton;

    @FXML
    private ImageView defaultImageView;

    @FXML
    private MenuButton accountMenuButton;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label membershipStatusLabel;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.f775a = C0058ae.m195a();
        this.connectionButton.visibleProperty().bind(this.f775a.m199d().not());
        this.connectionButton.managedProperty().bind(this.f775a.m199d().not());
        this.accountMenuButton.visibleProperty().bind(this.f775a.m199d());
        this.accountMenuButton.managedProperty().bind(this.f775a.m199d());
        this.f775a.m210l().addListener((observableValue, previousValue, currentValue) -> {
            Platform.runLater(() -> {
                switch (currentValue) {
                    case REGULAR:
                        this.membershipStatusLabel.setText(this.resources.getString("application.account.membershipStatus.regular"));
                        this.membershipStatusLabel.pseudoClassStateChanged(f776b, true);
                        this.membershipStatusLabel.pseudoClassStateChanged(f777c, false);
                        this.membershipStatusLabel.pseudoClassStateChanged(f778d, false);
                        break;
                    case PREMIUM:
                        this.membershipStatusLabel.setText(this.resources.getString("application.account.membershipStatus.premium"));
                        this.membershipStatusLabel.pseudoClassStateChanged(f776b, false);
                        this.membershipStatusLabel.pseudoClassStateChanged(f777c, true);
                        this.membershipStatusLabel.pseudoClassStateChanged(f778d, false);
                        break;
                    case PLATINUM:
                        this.membershipStatusLabel.setText(this.resources.getString("application.account.membershipStatus.platinum"));
                        this.membershipStatusLabel.pseudoClassStateChanged(f776b, false);
                        this.membershipStatusLabel.pseudoClassStateChanged(f777c, false);
                        this.membershipStatusLabel.pseudoClassStateChanged(f778d, true);
                        break;
                }
            });
        });
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1263a(C0150cP event) {
        try {
            URL url = new URL(event.m658a().url());
            if (url.getHost().equals(C0217b.m960a())) {
                event.m658a().mainFrame().ifPresent(frame -> {
                    JsObject window = (JsObject) frame.executeJavaScript("window");
                    if (window != null) {
                        window.property("ipsSettings").flatMap(jsObject -> {
                            return ((JsObject) jsObject).property("memberID");
                        }).ifPresent(property -> {
                            int memberID = ((Double) property).intValue();
                            if (memberID != this.f775a.m197c()) {
                                m1264a();
                            }
                        });
                    }
                });
                Optional<Cookie> optionalPremiumCookie = event.m658a().engine().cookieStore().cookies(event.m658a().url()).stream().filter(cookie -> {
                    return cookie.name().equals("rs_membership_status");
                }).findAny();
                optionalPremiumCookie.ifPresent(cookie2 -> {
                    try {
                        JsonParser parser = new JsonParser();
                        JsonElement element = parser.parse(URLDecoder.decode(cookie2.value(), "UTF-8"));
                        Platform.runLater(() -> {
                            C0058ae.m195a().m212a(EnumC0059af.m227a(element.getAsJsonObject().get("value").getAsInt()));
                        });
                    } catch (Exception exception) {
                        RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                    }
                });
            }
        } catch (MalformedURLException exception) {
            RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1264a() {
        try {
            C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/account/login.php");
            builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a)).m974a("os", Integer.valueOf(C0004E.m10c().m7a())).m974a("arch", Integer.valueOf(C0004E.m11d().m6a()));
            C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f());
            C0216a request = new C0216a(builder.m973b());
            C0219d response = request.m959a(post);
            JsonObject member = response.m967b().getAsJsonObject("member");
            int memberID = member.get("id").getAsInt();
            if (memberID == 0) {
                this.f775a.m225v();
                RomStationController.f786a.post(new C0161ca());
            } else {
                this.f775a.m198a(memberID);
                this.f775a.m203a(member.get("pseudo").getAsString());
                this.f775a.m221d(C0217b.m961b() + "/" + member.get("profile_url").getAsString());
                this.f775a.m215b(C0217b.m961b() + "/" + member.get("photo").getAsString());
                this.f775a.m212a(EnumC0059af.m227a(member.get("membership_status").getAsInt()));
                this.f775a.m218c(member.get("session_key").getAsString());
                this.f775a.m206b(member.get("group_id").getAsInt());
                this.f775a.m209a(member.get("ban").getAsInt() == 1);
                this.f775a.m224e(C0217b.m961b() + "/" + member.get("logout").getAsString());
                RomStationController.f786a.post(new C0107bZ());
            }
        } catch (MalformedURLException | InvalidServerResponseException | ServerResponseException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        } catch (NetworkOfflineException exception2) {
            RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1265a(C0162cb event) {
        m1264a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1266a(C0107bZ event) {
        Platform.runLater(() -> {
            this.profileImageView.setImage(new Image(this.f775a.m214o(), true));
            this.nameLabel.setText(this.f775a.m202g());
        });
        if (C0013N.m38a().account.on_sign_in != null) {
            C0013N.m38a().account.on_sign_in.accept(this.f775a);
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1267a(C0161ca event) {
        if (C0013N.m38a().account.on_sign_out != null) {
            C0013N.m38a().account.on_sign_out.accept(this.f775a);
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1268a(C0163cc event) {
        signIn();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1269a(C0164cd event) {
        signOut();
    }

    @FXML
    public void onConnectionAction(ActionEvent event) {
        signIn();
    }

    @FXML
    private void signIn() {
        RomStationController.f786a.post(new C0153cS(C0217b.m961b() + "/login"));
        RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
    }

    @FXML
    private void signOut() {
        RomStationController.f786a.post(new C0153cS(this.f775a.m223u()));
    }

    @FXML
    private void profile() {
        RomStationController.f786a.post(new C0152cR(this.f775a.m220s(), true));
        RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
    }

    @FXML
    private void store() {
        RomStationController.f786a.post(new C0152cR(C0217b.m961b() + "/store", true));
        RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
    }
}
