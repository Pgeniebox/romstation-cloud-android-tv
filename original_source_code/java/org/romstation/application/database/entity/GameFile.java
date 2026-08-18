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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/GameFile.class */
@Table(name = "GAME_FILE")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = GameFile.f461a, query = "select gameFile from GameFile gameFile where gameFile.rid = :rid")})
public class GameFile extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f461a = "GameFile.findByRID";

    /* JADX INFO: renamed from: b */
    private String f462b;

    /* JADX INFO: renamed from: c */
    private String f463c;

    /* JADX INFO: renamed from: d */
    private Game f464d;

    /* JADX INFO: renamed from: e */
    private boolean f465e;

    /* JADX INFO: renamed from: f */
    private List<GameProfile> f466f = new LinkedList();

    /* JADX INFO: renamed from: g */
    private List<Script> f467g = new LinkedList();

    /* JADX INFO: renamed from: h */
    private Map<String, String> f468h = new HashMap();

    public GameFile() {
    }

    public GameFile(GameFile entity) {
        setRid(entity.getRid());
        this.f462b = entity.getName();
        this.f463c = entity.getDirectory();
        this.f464d = entity.getGame();
        this.f465e = false;
        entity.getProfiles().forEach(gameProfile -> {
            this.f466f.add(new GameProfile(gameProfile));
        });
        this.f467g.addAll(entity.getScripts());
        this.f468h.putAll(entity.getMetas());
    }

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAME_FILE_GENERATOR")
    @SequenceGenerator(name = "GAME_FILE_GENERATOR", sequenceName = "GAME_FILE_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return this.f462b;
    }

    public void setName(String name) {
        this.f462b = name;
    }

    public String getDirectory() {
        return this.f463c;
    }

    public void setDirectory(String directory) {
        this.f463c = directory;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Game getGame() {
        return this.f464d;
    }

    public void setGame(Game game) {
        this.f464d = game;
    }

    public boolean isManaged() {
        return this.f465e;
    }

    public void setManaged(boolean managed) {
        this.f465e = managed;
    }

    @JoinColumn(name = "GAME_FILE_ID")
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<GameProfile> getProfiles() {
        return this.f466f;
    }

    public void setProfiles(List<GameProfile> profiles) {
        this.f466f = profiles;
    }

    @JoinTable(joinColumns = {@JoinColumn(name = "GAME_FILE_ID")}, inverseJoinColumns = {@JoinColumn(name = "SCRIPT_ID")})
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<Script> getScripts() {
        return this.f467g;
    }

    public void setScripts(List<Script> scripts) {
        this.f467g = scripts;
    }

    @CollectionTable(name = "GAME_FILE_META", joinColumns = {@JoinColumn(name = "GAME_FILE_ID")})
    @MapKeyColumn(name = "\"KEY\"")
    @ElementCollection
    @Column(name = "VALUE", length = 32672)
    public Map<String, String> getMetas() {
        return this.f468h;
    }

    public void setMetas(Map<String, String> meta) {
        this.f468h = meta;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameFile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameFile.prePersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameFile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameFile.postPersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameFile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameFile.preUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameFile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameFile.postUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameFile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameFile.preRemove():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.GameFile for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameFileAdapter r0 = r0.game_file
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.GameFile.postRemove():void");
    }

    public String toString() {
        return this.f462b;
    }
}
