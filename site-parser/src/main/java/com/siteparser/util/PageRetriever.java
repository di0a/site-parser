package com.siteparser.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sundial on 16.04.2017.
 */
public class PageRetriever {

    private static AtomicInteger pageCount = new AtomicInteger(0);



    public void increasePageCount() {
        pageCount.getAndIncrement();

    }

    public static int getPageCount() {
        return pageCount.get();

    }
    /**
    Get the page from the web with a url
    */

    public static Document getPage(String url) {

        Document docResult = null;
        try {
            docResult = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 ((Windows NT 6.1; Win64; x64; rv:25.0;Linux x86_64) Gecko/20100101 Firefox/25.0 Chrome/55.0.2883.75 Safari/537.36")
                    .timeout(4000)
                    .get();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return docResult;
    }
}
