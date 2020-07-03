package com.gametown.web.helper;

import com.github.jknack.handlebars.Handlebars;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@HandlebarsHelper
public class UrlEncoderHelper {
    public CharSequence urlEncoder(String url) throws UnsupportedEncodingException {
        return new Handlebars.SafeString(URLEncoder.encode(url, "UTF-8"));
    }
}
