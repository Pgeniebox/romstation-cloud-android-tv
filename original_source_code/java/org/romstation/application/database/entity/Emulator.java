package org.romstation.application.database.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Emulator.class */
@Table(name = "EMULATOR")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = Emulator.f412a, query = "select count(emulator) from Emulator emulator"), @NamedQuery(name = Emulator.f413b, query = "select distinct emulator from Emulator emulator join emulator.files file join file.profiles profile where :system member of profile.systems order by emulator.name asc"), @NamedQuery(name = Emulator.f414c, query = "select emulator from Emulator emulator where emulator.rid = :rid")})
public class Emulator extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f412a = "Emulator.countAll";

    /* JADX INFO: renamed from: b */
    public static final String f413b = "Emulator.findForSystem";

    /* JADX INFO: renamed from: c */
    public static final String f414c = "Emulator.findByRid";

    /* JADX INFO: renamed from: d */
    private String f415d;

    /* JADX INFO: renamed from: e */
    private String f416e;

    /* JADX INFO: renamed from: f */
    private Image f417f;

    /* JADX INFO: renamed from: g */
    private boolean f418g;

    /* JADX INFO: renamed from: h */
    private List<Link> f419h = new LinkedList();

    /* JADX INFO: renamed from: i */
    private List<Script> f420i = new LinkedList();

    /* JADX INFO: renamed from: j */
    private List<EmulatorFile> f421j = new LinkedList();

    /* JADX INFO: renamed from: k */
    private Map<String, String> f422k = new HashMap();

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMULATOR_GENERATOR")
    @SequenceGenerator(name = "EMULATOR_GENERATOR", sequenceName = "EMULATOR_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return this.f415d;
    }

    public void setName(String name) {
        this.f415d = name;
    }

    public String getDirectory() {
        return this.f416e;
    }

    public void setDirectory(String directory) {
        this.f416e = directory;
    }

    @JoinColumn(name = "GRAPHIC_IMAGE_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Image getGraphic() {
        return this.f417f;
    }

    public void setGraphic(Image icon) {
        this.f417f = icon;
    }

    public boolean isManaged() {
        return this.f418g;
    }

    public void setManaged(boolean managed) {
        this.f418g = managed;
    }

    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "LINK_ID")})
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<Link> getLinks() {
        return this.f419h;
    }

    public void setLinks(List<Link> links) {
        this.f419h = links;
    }

    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "SCRIPT_ID")})
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<Script> getScripts() {
        return this.f420i;
    }

    public void setScripts(List<Script> scripts) {
        this.f420i = scripts;
    }

    @JoinColumn(name = "EMULATOR_ID")
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<EmulatorFile> getFiles() {
        return this.f421j;
    }

    public void setFiles(List<EmulatorFile> files) {
        this.f421j = files;
    }

    @CollectionTable(name = "EMULATOR_META", joinColumns = {@JoinColumn(name = "EMULATOR_ID")})
    @MapKeyColumn(name = "\"KEY\"")
    @ElementCollection
    @Column(name = "VALUE", length = 32672)
    public Map<String, String> getMetas() {
        return this.f422k;
    }

    public void setMetas(Map<String, String> meta) {
        this.f422k = meta;
    }

    @Transient
    public List<System> getSystems() {
        return (List) this.f421j.stream().map((v0) -> {
            return v0.getProfiles();
        }).flatMap((v0) -> {
            return v0.stream();
        }).map((v0) -> {
            return v0.getSystems();
        }).flatMap((v0) -> {
            return v0.stream();
        }).distinct().collect(Collectors.toList());
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Emulator for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Emulator.prePersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Emulator for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Emulator.postPersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Emulator for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Emulator.preUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Emulator for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Emulator.postUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Emulator for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Emulator.preRemove():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Emulator for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.EmulatorAdapter r0 = r0.emulator
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Emulator.postRemove():void");
    }

    public String toString() {
        return "[" + getId() + "] " + this.f415d;
    }
}
