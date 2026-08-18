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
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/database/entity/Game.class */
@Table(name = "GAME")
@Entity
@Access(AccessType.PROPERTY)
@NamedQueries({@NamedQuery(name = Game.f439a, query = "select count(game) from Game game"), @NamedQuery(name = Game.f440b, query = "select game from Game game where game.rid = :rid")})
public class Game extends RemoteEntity {

    /* JADX INFO: renamed from: a */
    public static final String f439a = "Game.countAll";

    /* JADX INFO: renamed from: b */
    public static final String f440b = "Game.findByRID";

    /* JADX INFO: renamed from: c */
    private String f441c;

    /* JADX INFO: renamed from: d */
    private System f442d;

    /* JADX INFO: renamed from: g */
    private Integer f445g;

    /* JADX INFO: renamed from: h */
    private Developer f446h;

    /* JADX INFO: renamed from: i */
    private Publisher f447i;

    /* JADX INFO: renamed from: k */
    private Integer f449k;

    /* JADX INFO: renamed from: l */
    private I18n f450l;

    /* JADX INFO: renamed from: m */
    private Image f451m;

    /* JADX INFO: renamed from: n */
    private boolean f452n;

    /* JADX INFO: renamed from: q */
    private String f455q;

    /* JADX INFO: renamed from: u */
    private Long f459u;

    /* JADX INFO: renamed from: v */
    private Long f460v;

    /* JADX INFO: renamed from: e */
    private List<Language> f443e = new LinkedList();

    /* JADX INFO: renamed from: f */
    private List<Genre> f444f = new LinkedList();

    /* JADX INFO: renamed from: j */
    private List<Series> f448j = new LinkedList();

    /* JADX INFO: renamed from: o */
    private List<Tag> f453o = new LinkedList();

    /* JADX INFO: renamed from: p */
    private List<Link> f454p = new LinkedList();

    /* JADX INFO: renamed from: r */
    private List<Script> f456r = new LinkedList();

    /* JADX INFO: renamed from: s */
    private List<GameFile> f457s = new LinkedList();

    /* JADX INFO: renamed from: t */
    private Map<String, String> f458t = new HashMap();

    @Override // org.romstation.application.database.entity.AbstractC0189a
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAME_GENERATOR")
    @SequenceGenerator(name = "GAME_GENERATOR", sequenceName = "GAME_SEQUENCE", allocationSize = 1)
    public Integer getId() {
        return super.getId();
    }

    public String getTitle() {
        return this.f441c;
    }

    public void setTitle(String title) {
        this.f441c = title;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public System getSystem() {
        return this.f442d;
    }

    public void setSystem(System system) {
        this.f442d = system;
    }

    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "LANGUAGE_ID")})
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<Language> getLanguages() {
        return this.f443e;
    }

    public void setLanguages(List<Language> languages) {
        this.f443e = languages;
    }

    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "GENRE_ID")})
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<Genre> getGenres() {
        return this.f444f;
    }

    public void setGenres(List<Genre> genres) {
        this.f444f = genres;
    }

    public Integer getPlayers() {
        return this.f445g;
    }

    public void setPlayers(Integer players) {
        this.f445g = players;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Developer getDeveloper() {
        return this.f446h;
    }

    public void setDeveloper(Developer developer) {
        this.f446h = developer;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Publisher getPublisher() {
        return this.f447i;
    }

    public void setPublisher(Publisher publisher) {
        this.f447i = publisher;
    }

    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "SERIES_ID")})
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<Series> getSeries() {
        return this.f448j;
    }

    public void setSeries(List<Series> series) {
        this.f448j = series;
    }

    @Column(name = "\"YEAR\"")
    public Integer getYear() {
        return this.f449k;
    }

    public void setYear(Integer year) {
        this.f449k = year;
    }

    @JoinColumn(name = "DESCRIPTION_I18N_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public I18n getDescription() {
        return this.f450l;
    }

    public void setDescription(I18n description) {
        this.f450l = description;
    }

    @JoinColumn(name = "GRAPHIC_IMAGE_ID")
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Image getGraphic() {
        return this.f451m;
    }

    public void setGraphic(Image cover) {
        this.f451m = cover;
    }

    public boolean isManaged() {
        return this.f452n;
    }

    public void setManaged(boolean managed) {
        this.f452n = managed;
    }

    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "TAG_ID")})
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<Tag> getTags() {
        return this.f453o;
    }

    public void setTags(List<Tag> tags) {
        this.f453o = tags;
    }

    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "LINK_ID")})
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<Link> getLinks() {
        return this.f454p;
    }

    public void setLinks(List<Link> links) {
        this.f454p = links;
    }

    public String getDirectory() {
        return this.f455q;
    }

    public void setDirectory(String directory) {
        this.f455q = directory;
    }

    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "SCRIPT_ID")})
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<Script> getScripts() {
        return this.f456r;
    }

    public void setScripts(List<Script> scripts) {
        this.f456r = scripts;
    }

    @JoinColumn(name = "GAME_ID")
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<GameFile> getFiles() {
        return this.f457s;
    }

    public void setFiles(List<GameFile> files) {
        this.f457s = files;
    }

    @CollectionTable(name = "GAME_META", joinColumns = {@JoinColumn(name = "GAME_ID")})
    @MapKeyColumn(name = "\"KEY\"")
    @ElementCollection
    @Column(name = "VALUE", length = 32672)
    public Map<String, String> getMetas() {
        return this.f458t;
    }

    public void setMetas(Map<String, String> meta) {
        this.f458t = meta;
    }

    @Column(name = "LAST_USE")
    public Long getLastUse() {
        return this.f459u;
    }

    public void setLastUse(Long lastUse) {
        this.f459u = lastUse;
    }

    public Long getPlayed() {
        return this.f460v;
    }

    public void setPlayed(Long played) {
        this.f460v = played;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Game for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Game.prePersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Game for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_persist
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Game.postPersist():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Game for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Game.preUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Game for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_update
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Game.postUpdate():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Game for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_pre_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Game.preRemove():void");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.romstation.application.database.entity.Game for r5v1 'this'  java.lang.Object
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
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            if (r0 == 0) goto L70
            org.romstation.application.O r0 = org.romstation.application.C0013N.m38a()
            org.romstation.application.V r0 = r0.database
            org.romstation.application.script.api.database.adapter.GameAdapter r0 = r0.game
            java.util.function.Consumer<T extends org.romstation.application.database.entity.a> r0 = r0.on_post_remove
            r1 = r5
            r0.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.romstation.application.database.entity.Game.postRemove():void");
    }

    public String toString() {
        return "[" + getId() + "] " + this.f441c;
    }
}
