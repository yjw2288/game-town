package com.gametown.web.helper;

import com.github.jknack.handlebars.Handlebars;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class ResourceHelper {
    public static long TIMESTAMP = System.currentTimeMillis();

    public CharSequence resources(String path) {
         return new Handlebars.SafeString("/resources/" +path + "?v=" + TIMESTAMP);
    }
}
