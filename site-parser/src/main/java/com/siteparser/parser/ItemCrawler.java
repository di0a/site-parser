package com.siteparser.parser;

import com.siteparser.ItemTask;
import com.siteparser.domain.Item;
import com.siteparser.util.PageRetriever;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


import static com.siteparser.util.UrlBuilder.createLinkForNextPage;
import static com.siteparser.util.UrlBuilder.createLinkForRequest;


/**
 * Created by sundial on 16.04.2017.
 */
public class ItemCrawler {

    private PageRetriever pageRetriever = new PageRetriever();
    private Parser parser = new Parser();
    private boolean isDelay;

    /**
     * Method takes url with keyword for search and retrieve html page DOM for this search request.
     * Method grabbed all items from search result page, set major properties that occur - id, name and brand properties
     */

    public List<Item> showAllItemsFromSearchResult(String keyword, ExecutorService executor) {
        Queue<String> queuePages = new LinkedList<String>();
        List<Item> result = new LinkedList<Item>();
        String requestLink = createLinkForRequest(keyword);
        Document pageSearchResult = PageRetriever.getPage(requestLink);
        result.addAll(processPage(pageSearchResult, executor, queuePages));
        while (!queuePages.isEmpty()) {
            String linkForNextPage = createLinkForNextPage(queuePages.poll());
            Document htmlPageSearchResult = PageRetriever.getPage(linkForNextPage);
            pageRetriever.increasePageCount();
            result.addAll(processPage(htmlPageSearchResult, executor, queuePages));
            System.out.println("Searching items on page number: " + PageRetriever.getPageCount());

        }
        return result;
    }

    private List<Item> processPage(Document pageSearchResult, ExecutorService executor, Queue<String> queue) {
        List<Item> grabbedItems = grabbedItemsFromPage(pageSearchResult);
        for (Item item : grabbedItems) {
            executor.submit(new ItemTask(item, parser));
        }
        String nextPage = getNextPage(pageSearchResult);
        if (!nextPage.equals("")) {
            queue.add(nextPage);
        }

        return grabbedItems;
    }

    /**
     * Method set random interval between getting new page with Jsoup.
     */
    public static void suspendExecution() {
        try {
            int randomNum = ThreadLocalRandom.current().nextInt(5, 7 + 1);
            TimeUnit.SECONDS.sleep(randomNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Iterates throw items list and for all found items creates new object Item, set properties and add to collection.
     */

    private List<Item> grabbedItemsFromPage(Document pageSearchResult) {
        Elements elements = pageSearchResult.select("div[itemProp='itemListElement']");
        List<Item> foundItems = new LinkedList<Item>();
        for (Element element : elements) {
            Element article = element.getElementsByTag("article").first();
            String itemId = article.attr("data-product-id");
            Item item = new Item();
            item.setId(itemId);
            item.setName(getItemNameFromElement(element));
            item.setBrand(getItemBrandFromElement(element));
            foundItems.add(item);
        }
        return foundItems;
    }


    private String getItemBrandFromElement(Element element) {
        String brand = element.select("div[itemprop='brand']").text();
        if (brand != null) {
            return brand;
        } else {
            System.out.println("Brand not found!");
            return "";
        }

    }


    private String getItemNameFromElement(Element element) {
        String name = element.select("div[itemprop='name']").text();
        if (name != null) {
            return name;

        } else {
            System.out.println("Name not found!");
            return "";
        }

    }


    private String getNextPage(Element pageWithResults) {
        Element pagerBottom = pageWithResults.select("ul[id='pl-pager-bottom']").first();
        if (pagerBottom == null) return "";
        Element nextButton = pagerBottom.select("li[class='next']").first();
        if (nextButton != null) {
            return nextButton.getElementsByTag("a").first().attr("href");
        } else return "";

    }


}
