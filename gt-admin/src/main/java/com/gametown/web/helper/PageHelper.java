package com.gametown.web.helper;

import com.gametown.page.PageDto;
import com.github.jknack.handlebars.Handlebars;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class PageHelper {
    public CharSequence page(PageDto<?> page) {
        String pageBody = "<nav aria-label=\"Page navigation example\">\n" +
                "        <ul class=\"pagination\">\n" +
                "           %s" +
                "        </ul>\n" +
                "    </nav>";

        int pageNow = page.getPageNumber() + 1;
        int start = getStartPage(page);
        int end = getEndPage(start, page.getTotalPages());

        StringBuilder pageContentsBuilder = new StringBuilder();
        if (start != 1) {
            pageContentsBuilder.append("<li class=\"page-item \"><a class=\"page-link\" href=\"?page=").append(start - 2).append("\">이전</a></li>\n");
        }

        for (int i = start; i <= end; i++) {
            String active = (pageNow == i) ? "active" : "";
            pageContentsBuilder.append("<li class=\"page-item ").append(active).append("\"><a class=\"page-link\" href=\"?page=").append(i - 1).append("\">").append(i).append("</a></li>\n");
        }

        if (end != page.getTotalPages()) {
            pageContentsBuilder.append("<li class=\"page-item\"><a class=\"page-link\" href=\"?page=").append(end).append("\">다음</a></li>\n");
        }

        return new Handlebars.SafeString(String.format(pageBody, pageContentsBuilder.toString()));
    }

    private int getStartPage(PageDto<?> page) {
        return 10 * (page.getPageNumber() / 10) + 1;
    }

    private int getEndPage(int start, int totalPages) {
        int end = start + 9;
        return Math.min(totalPages, end);
    }
}
