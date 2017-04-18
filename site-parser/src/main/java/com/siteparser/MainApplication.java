package com.siteparser;

import com.siteparser.domain.Item;
import com.siteparser.generator.XmlGenerator;
import com.siteparser.parser.ItemCrawler;
import com.siteparser.parser.Parser;
import com.siteparser.util.PageRetriever;
import com.siteparser.util.UrlBuilder;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.TimeUnit;

/**
 * Created by sundial on 17.04.2017.
 */
public class MainApplication {

    public static void main(String[] args) throws Exception {

        String searchKeyword = "griter";
        System.out.println("Search keyword: " + searchKeyword);
        ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(UrlBuilder.getProperty("count.of.threads")));
        ItemCrawler crawler = new ItemCrawler();
        List<Item> parsedItems = crawler.showAllItemsFromSearchResult(searchKeyword, executor);

        //List<Item> itemsToParse = itemStorage.getFoundItems();
        //System.out.println("Found " + itemsToParse.size() + " items");
        // int itemToParse = itemsToParse.size();

        while (executor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.out.println("hello");
        }

        XmlGenerator xmlGenerator = new XmlGenerator();
        xmlGenerator.generateXml(parsedItems);

        //  System.out.println("Number of HTTP requests: " + (PageRetriever.getPageCount() + itemStorage.getCountParsedItems()));
        //  System.out.println("Extracted items: " + itemStorage.getCountParsedItems());

    }
}



