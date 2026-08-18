package org.romstation.application.database.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/GameProfile.class */
@Table(name = "GAME_PROFILE")
@Entity
@Access(AccessType.PROPERTY)
public class GameProfile extends AbstractC0189a {

    /* JADX INFO: renamed from: a */
    private String f469a;

    /* JADX INFO: renamed from: b */
    private String f470b;

    /* JADX INFO: renamed from: c */
    private GameFile f471c;

    /* JADX INFO: renamed from: d */
    private EmulatorProfile f472d;

    /* JADX INFO: renamed from: e */
    private String f473e;

    /* JADX INFO: renamed from: f */
    private List<Script> f474f = new LinkedList();

    /* JADX INFO: renamed from: g */
    private Map<String, String> f475g = new HashMap();

    public GameProfile() {
    }

    public GameProfile(GameProfile entity) {
        this.f469a = entity.getName();
        this.f470b = entity.getPath();
        this.f471c = entity.getGameFile();
        this.f472d = entity.getEmulatorProfile();
        this.f473e = entity.getParameters();
        this.f475g.putAll(entity.getMetas());
        this.f474f.addAll(entity.getScripts());
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAME_PROFILE_GENERATOR")
    @SequenceGenerator(name = "GAME_PROFILE_GENERATOR", sequenceName = "GAME_PROFILE_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return this.f469a;
    }

    public void setName(String name) {
        this.f469a = name;
    }

    public String getPath() {
        return this.f470b;
    }

    public void setPath(String path) {
        this.f470b = path;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "GAME_FILE_ID")
    public GameFile getGameFile() {
        return this.f471c;
    }

    public void setGameFile(GameFile gameFile) {
        this.f471c = gameFile;
    }

    @JoinColumn(name = "EMULATOR_PROFILE_ID")
    @OneToOne
    public EmulatorProfile getEmulatorProfile() {
        return this.f472d;
    }

    public void setEmulatorProfile(EmulatorProfile emulatorProfile) {
        this.f472d = emulatorProfile;
    }

    public String getParameters() {
        return this.f473e;
    }

    public void setParameters(String parameters) {
        this.f473e = parameters;
    }

    @JoinTable(joinColumns = {@JoinColumn(name = "GAME_PROFILE_ID")}, inverseJoinColumns = {@JoinColumn(name = "SCRIPT_ID")})
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<Script> getScripts() {
        return this.f474f;
    }

    public void setScripts(List<Script> scripts) {
        this.f474f = scripts;
    }

    @CollectionTable(name = "GAME_PROFILE_META", joinColumns = {@JoinColumn(name = "GAME_PROFILE_ID")})
    @MapKeyColumn(name = "\"KEY\"")
    @ElementCollection
    @Column(name = "VALUE", length = 32672)
    public Map<String, String> getMetas() {
        return this.f475g;
    }

    public void setMetas(Map<String, String> meta) {
        this.f475g = meta;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameProfile for r5v1 'this'  java.lang.Object
        	at jadx.core.dex.instructions.args.SSAVar.setType(SSAVar.java:114)
        	at jadx.core.dex.instructions.args.RegisterArg.setType(RegisterArg.java:52)
        	at jadx.core.dex.visitors.ModVisitor.removeCheckCast(ModVisitor.java:417)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:152)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    @javax.persistence.PrePersist
    private void prePersist() {
        /*
            r5 = this;
            r0 = r5
            java.util.List r0 = r0.getScripts()
            java.util.Iterator r0 = r0.iterator()
            r6 = r0
        La:
            r0 = r6
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L4f
            r0 = r6
            java.lang.Object r0 = r0.next()
            org.romstation.application.database.entity.Script r0 = (org.romstation.application.database.entity.Script) r0
            r7 = r0
            r0 = r7
            org.jruby.RubyObject r0 = org.romstation.application.C0013N.m39a(r0)     // Catch: java.lang.Exception -> L3d
            r8 = r0
            r0 = r8
            java.lang.String r1 = "on_pre_persist"
            boolean r0 = r0.respondsTo(r1)     // Catch: java.lang.Exception -> L3d
            if (r0 == 0) goto L3a
            r0 = r8
            java.lang.String r1 = "on_pre_persist"
            r2 = r8
            org.jruby.Ruby r2 = r2.getRuntime()     // Catch: java.lang.Exception -> L3d
            r3 = r5
            org.jruby.runtime.builtin.IRubyObject r2 = org.jruby.javasupport.JavaUtil.convertJavaToRuby(r2, r3)     // Catch: java.lang.Exception -> L3d
            org.jruby.runtime.builtin.IRubyObject r0 = r0.callMethod(r1, r2)     // Catch: java.lang.Exception -> L3d
        L3a:
            goto L4c
        L3d:
            r8 = move-exception
            java.util.logging.Logger r0 = org.romstation.application.RomStation.m42b()
            java.util.logging.Level r1 = java.util.logging.Level.WARNING
            r2 = r8
            java.lang.String r2 = r2.getMessage()
            r3 = r8
            r0.log(r1, r2, r3)
        L4c:
            goto La
        L4f:
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameProfile.prePersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameProfile for r5v1 'this'  java.lang.Object
        	at jadx.core.dex.instructions.args.SSAVar.setType(SSAVar.java:114)
        	at jadx.core.dex.instructions.args.RegisterArg.setType(RegisterArg.java:52)
        	at jadx.core.dex.visitors.ModVisitor.removeCheckCast(ModVisitor.java:417)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:152)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    @javax.persistence.PostPersist
    private void postPersist() {
        /*
            r5 = this;
            r0 = r5
            java.util.List r0 = r0.getScripts()
            java.util.Iterator r0 = r0.iterator()
            r6 = r0
        La:
            r0 = r6
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L4f
            r0 = r6
            java.lang.Object r0 = r0.next()
            org.romstation.application.database.entity.Script r0 = (org.romstation.application.database.entity.Script) r0
            r7 = r0
            r0 = r7
            org.jruby.RubyObject r0 = org.romstation.application.C0013N.m39a(r0)     // Catch: java.lang.Exception -> L3d
            r8 = r0
            r0 = r8
            java.lang.String r1 = "on_post_persist"
            boolean r0 = r0.respondsTo(r1)     // Catch: java.lang.Exception -> L3d
            if (r0 == 0) goto L3a
            r0 = r8
            java.lang.String r1 = "on_post_persist"
            r2 = r8
            org.jruby.Ruby r2 = r2.getRuntime()     // Catch: java.lang.Exception -> L3d
            r3 = r5
            org.jruby.runtime.builtin.IRubyObject r2 = org.jruby.javasupport.JavaUtil.convertJavaToRuby(r2, r3)     // Catch: java.lang.Exception -> L3d
            org.jruby.runtime.builtin.IRubyObject r0 = r0.callMethod(r1, r2)     // Catch: java.lang.Exception -> L3d
        L3a:
            goto L4c
        L3d:
            r8 = move-exception
            java.util.logging.Logger r0 = org.romstation.application.RomStation.m42b()
            java.util.logging.Level r1 = java.util.logging.Level.WARNING
            r2 = r8
            java.lang.String r2 = r2.getMessage()
            r3 = r8
            r0.log(r1, r2, r3)
        L4c:
            goto La
        L4f:
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameProfile.postPersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameProfile for r5v1 'this'  java.lang.Object
        	at jadx.core.dex.instructions.args.SSAVar.setType(SSAVar.java:114)
        	at jadx.core.dex.instructions.args.RegisterArg.setType(RegisterArg.java:52)
        	at jadx.core.dex.visitors.ModVisitor.removeCheckCast(ModVisitor.java:417)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:152)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    @javax.persistence.PreUpdate
    private void preUpdate() {
        /*
            r5 = this;
            r0 = r5
            java.util.List r0 = r0.getScripts()
            java.util.Iterator r0 = r0.iterator()
            r6 = r0
        La:
            r0 = r6
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L4f
            r0 = r6
            java.lang.Object r0 = r0.next()
            org.romstation.application.database.entity.Script r0 = (org.romstation.application.database.entity.Script) r0
            r7 = r0
            r0 = r7
            org.jruby.RubyObject r0 = org.romstation.application.C0013N.m39a(r0)     // Catch: java.lang.Exception -> L3d
            r8 = r0
            r0 = r8
            java.lang.String r1 = "on_pre_update"
            boolean r0 = r0.respondsTo(r1)     // Catch: java.lang.Exception -> L3d
            if (r0 == 0) goto L3a
            r0 = r8
            java.lang.String r1 = "on_pre_update"
            r2 = r8
            org.jruby.Ruby r2 = r2.getRuntime()     // Catch: java.lang.Exception -> L3d
            r3 = r5
            org.jruby.runtime.builtin.IRubyObject r2 = org.jruby.javasupport.JavaUtil.convertJavaToRuby(r2, r3)     // Catch: java.lang.Exception -> L3d
            org.jruby.runtime.builtin.IRubyObject r0 = r0.callMethod(r1, r2)     // Catch: java.lang.Exception -> L3d
        L3a:
            goto L4c
        L3d:
            r8 = move-exception
            java.util.logging.Logger r0 = org.romstation.application.RomStation.m42b()
            java.util.logging.Level r1 = java.util.logging.Level.WARNING
            r2 = r8
            java.lang.String r2 = r2.getMessage()
            r3 = r8
            r0.log(r1, r2, r3)
        L4c:
            goto La
        L4f:
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameProfile.preUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameProfile for r5v1 'this'  java.lang.Object
        	at jadx.core.dex.instructions.args.SSAVar.setType(SSAVar.java:114)
        	at jadx.core.dex.instructions.args.RegisterArg.setType(RegisterArg.java:52)
        	at jadx.core.dex.visitors.ModVisitor.removeCheckCast(ModVisitor.java:417)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:152)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    @javax.persistence.PostUpdate
    private void postUpdate() {
        /*
            r5 = this;
            r0 = r5
            java.util.List r0 = r0.getScripts()
            java.util.Iterator r0 = r0.iterator()
            r6 = r0
        La:
            r0 = r6
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L4f
            r0 = r6
            java.lang.Object r0 = r0.next()
            org.romstation.application.database.entity.Script r0 = (org.romstation.application.database.entity.Script) r0
            r7 = r0
            r0 = r7
            org.jruby.RubyObject r0 = org.romstation.application.C0013N.m39a(r0)     // Catch: java.lang.Exception -> L3d
            r8 = r0
            r0 = r8
            java.lang.String r1 = "on_post_update"
            boolean r0 = r0.respondsTo(r1)     // Catch: java.lang.Exception -> L3d
            if (r0 == 0) goto L3a
            r0 = r8
            java.lang.String r1 = "on_post_update"
            r2 = r8
            org.jruby.Ruby r2 = r2.getRuntime()     // Catch: java.lang.Exception -> L3d
            r3 = r5
            org.jruby.runtime.builtin.IRubyObject r2 = org.jruby.javasupport.JavaUtil.convertJavaToRuby(r2, r3)     // Catch: java.lang.Exception -> L3d
            org.jruby.runtime.builtin.IRubyObject r0 = r0.callMethod(r1, r2)     // Catch: java.lang.Exception -> L3d
        L3a:
            goto L4c
        L3d:
            r8 = move-exception
            java.util.logging.Logger r0 = org.romstation.application.RomStation.m42b()
            java.util.logging.Level r1 = java.util.logging.Level.WARNING
            r2 = r8
            java.lang.String r2 = r2.getMessage()
            r3 = r8
            r0.log(r1, r2, r3)
        L4c:
            goto La
        L4f:
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameProfile.postUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameProfile for r5v1 'this'  java.lang.Object
        	at jadx.core.dex.instructions.args.SSAVar.setType(SSAVar.java:114)
        	at jadx.core.dex.instructions.args.RegisterArg.setType(RegisterArg.java:52)
        	at jadx.core.dex.visitors.ModVisitor.removeCheckCast(ModVisitor.java:417)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:152)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    @javax.persistence.PreRemove
    private void preRemove() {
        /*
            r5 = this;
            r0 = r5
            java.util.List r0 = r0.getScripts()
            java.util.Iterator r0 = r0.iterator()
            r6 = r0
        La:
            r0 = r6
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L4f
            r0 = r6
            java.lang.Object r0 = r0.next()
            org.romstation.application.database.entity.Script r0 = (org.romstation.application.database.entity.Script) r0
            r7 = r0
            r0 = r7
            org.jruby.RubyObject r0 = org.romstation.application.C0013N.m39a(r0)     // Catch: java.lang.Exception -> L3d
            r8 = r0
            r0 = r8
            java.lang.String r1 = "on_pre_remove"
            boolean r0 = r0.respondsTo(r1)     // Catch: java.lang.Exception -> L3d
            if (r0 == 0) goto L3a
            r0 = r8
            java.lang.String r1 = "on_pre_remove"
            r2 = r8
            org.jruby.Ruby r2 = r2.getRuntime()     // Catch: java.lang.Exception -> L3d
            r3 = r5
            org.jruby.runtime.builtin.IRubyObject r2 = org.jruby.javasupport.JavaUtil.convertJavaToRuby(r2, r3)     // Catch: java.lang.Exception -> L3d
            org.jruby.runtime.builtin.IRubyObject r0 = r0.callMethod(r1, r2)     // Catch: java.lang.Exception -> L3d
        L3a:
            goto L4c
        L3d:
            r8 = move-exception
            java.util.logging.Logger r0 = org.romstation.application.RomStation.m42b()
            java.util.logging.Level r1 = java.util.logging.Level.WARNING
            r2 = r8
            java.lang.String r2 = r2.getMessage()
            r3 = r8
            r0.log(r1, r2, r3)
        L4c:
            goto La
        L4f:
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameProfile.preRemove():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameProfile for r5v1 'this'  java.lang.Object
        	at jadx.core.dex.instructions.args.SSAVar.setType(SSAVar.java:114)
        	at jadx.core.dex.instructions.args.RegisterArg.setType(RegisterArg.java:52)
        	at jadx.core.dex.visitors.ModVisitor.removeCheckCast(ModVisitor.java:417)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:152)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    @javax.persistence.PostRemove
    private void postRemove() {
        /*
            r5 = this;
            r0 = r5
            java.util.List r0 = r0.getScripts()
            java.util.Iterator r0 = r0.iterator()
            r6 = r0
        La:
            r0 = r6
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L4f
            r0 = r6
            java.lang.Object r0 = r0.next()
            org.romstation.application.database.entity.Script r0 = (org.romstation.application.database.entity.Script) r0
            r7 = r0
            r0 = r7
            org.jruby.RubyObject r0 = org.romstation.application.C0013N.m39a(r0)     // Catch: java.lang.Exception -> L3d
            r8 = r0
            r0 = r8
            java.lang.String r1 = "on_post_remove"
            boolean r0 = r0.respondsTo(r1)     // Catch: java.lang.Exception -> L3d
            if (r0 == 0) goto L3a
            r0 = r8
            java.lang.String r1 = "on_post_remove"
            r2 = r8
            org.jruby.Ruby r2 = r2.getRuntime()     // Catch: java.lang.Exception -> L3d
            r3 = r5
            org.jruby.runtime.builtin.IRubyObject r2 = org.jruby.javasupport.JavaUtil.convertJavaToRuby(r2, r3)     // Catch: java.lang.Exception -> L3d
            org.jruby.runtime.builtin.IRubyObject r0 = r0.callMethod(r1, r2)     // Catch: java.lang.Exception -> L3d
        L3a:
            goto L4c
        L3d:
            r8 = move-exception
            java.util.logging.Logger r0 = org.romstation.application.RomStation.m42b()
            java.util.logging.Level r1 = java.util.logging.Level.WARNING
            r2 = r8
            java.lang.String r2 = r2.getMessage()
            r3 = r8
            r0.log(r1, r2, r3)
        L4c:
            goto La
        L4f:
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameProfileAdapter r0 = r0.game_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameProfile.postRemove():void");
    }

    public String toString() {
        return "[" + getId() + "] " + this.f469a;
    }
}
