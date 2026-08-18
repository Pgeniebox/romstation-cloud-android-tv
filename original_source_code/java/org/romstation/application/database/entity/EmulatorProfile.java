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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/EmulatorProfile.class */
@Table(name = "EMULATOR_PROFILE")
@Entity
@Access(AccessType.PROPERTY)
public class EmulatorProfile extends AbstractC0189a {

    /* JADX INFO: renamed from: a */
    private String f431a;

    /* JADX INFO: renamed from: b */
    private String f432b;

    /* JADX INFO: renamed from: c */
    private String f433c;

    /* JADX INFO: renamed from: d */
    private String f434d;

    /* JADX INFO: renamed from: e */
    private EmulatorFile f435e;

    /* JADX INFO: renamed from: f */
    private List<System> f436f = new LinkedList();

    /* JADX INFO: renamed from: g */
    private List<Script> f437g = new LinkedList();

    /* JADX INFO: renamed from: h */
    private Map<String, String> f438h = new HashMap();

    public EmulatorProfile() {
    }

    public EmulatorProfile(EmulatorProfile entity) {
        this.f431a = entity.getName();
        this.f435e = entity.getEmulatorFile();
        this.f434d = entity.getParameters();
        this.f433c = entity.getWorkingDirectory();
        this.f432b = entity.getPath();
        this.f436f.addAll(entity.getSystems());
        this.f438h.putAll(entity.getMetas());
        this.f437g.addAll(entity.getScripts());
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMULATOR_PROFILE_GENERATOR")
    @SequenceGenerator(name = "EMULATOR_PROFILE_GENERATOR", sequenceName = "EMULATOR_PROFILE_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return this.f431a;
    }

    public void setName(String name) {
        this.f431a = name;
    }

    public String getPath() {
        return this.f432b;
    }

    public void setPath(String path) {
        this.f432b = path;
    }

    @Column(name = "WORKING_DIRECTORY")
    public String getWorkingDirectory() {
        return this.f433c;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.f433c = workingDirectory;
    }

    public String getParameters() {
        return this.f434d;
    }

    public void setParameters(String parameters) {
        this.f434d = parameters;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "EMULATOR_FILE_ID")
    public EmulatorFile getEmulatorFile() {
        return this.f435e;
    }

    public void setEmulatorFile(EmulatorFile emulatorFile) {
        this.f435e = emulatorFile;
    }

    @JoinTable(joinColumns = {@JoinColumn(name = "EMULATOR_PROFILE_ID")}, inverseJoinColumns = {@JoinColumn(name = "SYSTEM_ID")})
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<System> getSystems() {
        return this.f436f;
    }

    public void setSystems(List<System> systems) {
        this.f436f = systems;
    }

    @JoinTable(joinColumns = {@JoinColumn(name = "EMULATOR_PROFILE_ID")}, inverseJoinColumns = {@JoinColumn(name = "SCRIPT_ID")})
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<Script> getScripts() {
        return this.f437g;
    }

    public void setScripts(List<Script> scripts) {
        this.f437g = scripts;
    }

    @CollectionTable(name = "EMULATOR_PROFILE_META", joinColumns = {@JoinColumn(name = "EMULATOR_PROFILE_ID")})
    @MapKeyColumn(name = "\"KEY\"")
    @ElementCollection
    @Column(name = "VALUE", length = 32672)
    public Map<String, String> getMetas() {
        return this.f438h;
    }

    public void setMetas(Map<String, String> meta) {
        this.f438h = meta;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.EmulatorProfile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.EmulatorProfile.prePersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.EmulatorProfile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.EmulatorProfile.postPersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.EmulatorProfile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.EmulatorProfile.preUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.EmulatorProfile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.EmulatorProfile.postUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.EmulatorProfile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.EmulatorProfile.preRemove():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.EmulatorProfile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorProfileAdapter r0 = r0.emulator_profile
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.EmulatorProfile.postRemove():void");
    }

    public String toString() {
        return "[" + getId() + "] " + this.f431a;
    }
}
