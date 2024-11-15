package com.swmaestro.cotuber.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MutableCookieRequestWrapper extends HttpServletRequestWrapper {
    private List<Cookie> cookies;

    public MutableCookieRequestWrapper(HttpServletRequest request) {
        super(request);
        this.cookies = new ArrayList<>();

        Collections.addAll(this.cookies, request.getCookies());
    }

    @Override
    public Cookie[] getCookies() {
        return this.cookies.toArray(new Cookie[0]);
    }

    public void replaceCookie(Cookie cookie, String replaceTarget) {
        this.cookies.removeIf(c -> c.getName().equals(replaceTarget));
        this.cookies.add(cookie);
    }
}
