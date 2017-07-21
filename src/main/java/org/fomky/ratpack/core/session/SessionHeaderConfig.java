package org.fomky.ratpack.core.session;


import java.time.Duration;

public class SessionHeaderConfig {
    private Duration expires;
    private String domain;
    private String path = "/";
    private String idName = "JSESSIONID";
    private boolean httpOnly = true;
    private boolean secure;

    public Duration getExpires() {
        return expires;
    }

    public void setExpires(Duration expires) {
        this.expires = expires;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }
    /**
     * Set max age of the cookies related to session management.
     *
     * @param expiresDuration the duration, max age, of the cookies related to session management
     * @return the config
     */
    public SessionHeaderConfig expires(Duration expiresDuration) {
        this.expires = expiresDuration;
        return this;
    }

    /**
     * Set the {@code domain} for session cookie.
     * <p>
     * Define the scope of the cookie
     *
     * @param domain a domain to which session cokkie will be attached to
     * @return the config
     */
    public SessionHeaderConfig domain(String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * Set the {@code path} for session cookie.
     * <p>
     * Define the scope of the cookie.
     *
     * @param path a path to which session cookie will be attached to
     * @return the config
     */
    public SessionHeaderConfig path(String path) {
        this.path = path;
        return this;
    }

    /**
     * Set the name of the cookie for session id.
     *
     * @param idName the name of the cookie for session id
     * @return the config
     */
    public SessionHeaderConfig idName(String idName) {
        this.idName = idName;
        return this;
    }

    /**
     * Set session cookies attribute {@code HttpOnly}.
     *
     * @param httpOnly if true client side session cookies are {@code HttpOnly}
     * @return the config
     */
    public SessionHeaderConfig httpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    /**
     * Set session cookies attribute {@code Secure}.
     *
     * @param secure if true client side session cookies can be transmitted only over encrypted connection
     * @return the config
     */
    public SessionHeaderConfig secure(boolean secure) {
        this.secure = secure;
        return this;
    }
}
