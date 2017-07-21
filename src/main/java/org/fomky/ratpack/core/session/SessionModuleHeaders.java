package org.fomky.ratpack.core.session;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.inject.*;
import com.google.inject.name.Names;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.AsciiString;
import ratpack.func.Action;
import ratpack.guice.BindingsSpec;
import ratpack.guice.ConfigurableModule;
import ratpack.guice.RequestScoped;
import ratpack.http.Request;
import ratpack.http.Response;
import ratpack.session.*;
import ratpack.session.internal.*;
import ratpack.util.Types;

import javax.inject.Named;
import java.util.function.Consumer;

public class SessionModuleHeaders extends ConfigurableModule<SessionHeaderConfig> {

    /**
     * The name of the binding for the {@link Cache} implementation that backs the in memory session store.
     *
     * @see #memoryStore(Consumer)
     */
    public static final String LOCAL_MEMORY_SESSION_CACHE_BINDING_NAME = "localMemorySessionCache";

    /**
     * The key of the binding for the {@link Cache} implementation that backs the in memory session store.
     *
     * @see #memoryStore(Consumer)
     */
    public static final Key<Cache<AsciiString, ByteBuf>> LOCAL_MEMORY_SESSION_CACHE_BINDING_KEY = Key.get(
            new TypeLiteral<Cache<AsciiString, ByteBuf>>() {},
            Names.named(LOCAL_MEMORY_SESSION_CACHE_BINDING_NAME)
    );

    /**
     * A builder for an alternative cache for the default in memory store.
     * <p>
     * This method is intended to be used with the {@link BindingsSpec#binder(Action)} method.
     * <pre class="java">{@code
     * import ratpack.guice.Guice;
     * import ratpack.session.SessionModule;
     *
     * public class Example {
     *   public static void main(String... args) {
     *     Guice.registry(b -> b
     *         .binder(SessionModule.memoryStore(c -> c.maximumSize(100)))
     *     );
     *   }
     * }
     * }</pre>
     *
     * @param config the cache configuration
     * @return an action that binds the cache
     * @see #memoryStore(Binder, Consumer)
     */
    public static Action<Binder> memoryStore(Consumer<? super CacheBuilder<AsciiString, ByteBuf>> config) {
        return b -> memoryStore(b, config);
    }

    /**
     * A builder for an alternative cache for the default in memory store.
     * <p>
     * This method can be used from within a custom {@link Module}.
     * <pre class="java">{@code
     * import com.google.inject.AbstractModule;
     * import ratpack.session.SessionModule;
     *
     * public class CustomSessionModule extends AbstractModule {
     *   protected void configure() {
     *     SessionModule.memoryStore(binder(), c -> c.maximumSize(100));
     *   }
     * }
     * }</pre>
     * }<p>
     * This method binds the built cache with the {@link #LOCAL_MEMORY_SESSION_CACHE_BINDING_KEY} key.
     * It also implicitly registers a {@link RemovalListener}, that releases the byte buffers as they are discarded.
     *
     * @param binder the guice binder
     * @param config the cache configuration
     */
    public static void memoryStore(Binder binder, Consumer<? super CacheBuilder<AsciiString, ByteBuf>> config) {
        binder.bind(LOCAL_MEMORY_SESSION_CACHE_BINDING_KEY).toProvider(() -> {
            CacheBuilder<AsciiString, ByteBuf> cacheBuilder = Types.cast(CacheBuilder.newBuilder());
            cacheBuilder.removalListener(n -> n.getValue().release());
            config.accept(cacheBuilder);
            return cacheBuilder.build();
        }).in(Scopes.SINGLETON);
    }

    @Override
    protected void configure() {
        memoryStore(binder(), s -> s.maximumSize(1000));
    }

    @Provides
    @Singleton
    SessionStore sessionStoreAdapter(@Named(LOCAL_MEMORY_SESSION_CACHE_BINDING_NAME) Cache<AsciiString, ByteBuf> cache) {
        return new LocalMemorySessionStore(cache);
    }

    @Provides
    SessionIdGenerator sessionIdGenerator() {
        return new DefaultSessionIdGenerator();
    }

    @Provides
    @RequestScoped
    SessionId sessionId(Request request, Response response, SessionIdGenerator idGenerator, SessionHeaderConfig cookieConfig) {
        return new HeaderBasedSessionId(request, response, idGenerator, cookieConfig);
    }

    @Provides
    SessionSerializer sessionValueSerializer(JavaSessionSerializer sessionSerializer) {
        return sessionSerializer;
    }

    @Provides
    JavaSessionSerializer javaSessionSerializer() {
        return new JavaBuiltinSessionSerializer();
    }

    @Provides
    @RequestScoped
    Session sessionAdapter(SessionId sessionId, SessionStore store, Response response, ByteBufAllocator bufferAllocator, SessionSerializer defaultSerializer, JavaSessionSerializer javaSerializer) {
        return new DefaultSession(sessionId, bufferAllocator, store, response, defaultSerializer, javaSerializer);
    }
}
