/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fomky.ratpack.core.session;

import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.util.AsciiString;
import ratpack.http.Request;
import ratpack.http.Response;
import ratpack.session.SessionCookieConfig;
import ratpack.session.SessionId;
import ratpack.session.SessionIdGenerator;

import java.time.Duration;
import java.util.Optional;

public class HeaderBasedSessionId implements SessionId {

  private final Request request;
  private final Response response;
  private final SessionIdGenerator sessionIdGenerator;
  private final SessionHeaderConfig cookieConfig;

  private AsciiString assignedCookieId;
  private Optional<AsciiString> cookieSessionId;
  private boolean terminated;

  public HeaderBasedSessionId(Request request, Response response, SessionIdGenerator sessionIdGenerator, SessionHeaderConfig cookieConfig) {
    this.request = request;
    this.response = response;
    this.sessionIdGenerator = sessionIdGenerator;
    this.cookieConfig = cookieConfig;
  }

  @Override
  public AsciiString getValue() {
    if (assignedCookieId != null) {
      return assignedCookieId;
    }

    return getCookieSessionId().orElseGet(() -> {
      assignedCookieId = assignId();
      return assignedCookieId;
    });
  }

  private Optional<AsciiString> getCookieSessionId() {
    if (cookieSessionId == null) {
      if (terminated) {
        return Optional.empty();
      } else {
        String match = request.getHeaders().get(cookieConfig.getIdName());
        cookieSessionId = match == null ? Optional.empty() : Optional.of(AsciiString.of(match));
      }
    }

    return cookieSessionId;
  }

  private AsciiString assignId() {
    AsciiString id = sessionIdGenerator.generateSessionId();
    setCookie(id.toString(), cookieConfig.getExpires());
    return id;
  }

  private void setCookie(String value, Duration expiration) {
    response.getHeaders().add(cookieConfig.getIdName(),value);
  }

  @Override
  public void terminate() {
    terminated = true;
    setCookie("", Duration.ZERO);
    cookieSessionId = null;
    assignedCookieId = null;
  }
}
