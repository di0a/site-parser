package com.siteparser.parser;

import com.siteparser.domain.Item;
import com.siteparser.util.PageRetriever;
import com.siteparser.util.UrlBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by sundial on 16.04.2017.
 */
public class Parser {


    // private ItemStorage itemStorage;  //убрать
    //  private List<Item> itemsToParse;
    private boolean isDelay;


//    ItemTask итем один парсит - вітягивает страницу с итемом
//     CrawlTask    в котором есть итем и парсер и результат этой фигни
//    privet list itemto parse


    public Parser() {
        //  this.itemStorage = itemStorage;
        // this.itemsToParse = itemsToParse;

    }
//
//    @Override
//    public void run() {
//        parseItems(itemsToParse, itemStorage);
//
//    }

    /**
     * Method that takes concrete item, concatenate base url with item id and retrieve html page with item details,
     * after set all properties for this item and add  in the result list
     */
    //  public void parseItems(Item item) {

//        List<Item> result = new LinkedList<Item>();
//
//        for (Item item : itemsToParse) {
//            String itemUrl = UrlBuilder.getItemLink(item.getId());
//
//            if (isDelay) {
//                ItemCrawler.suspendExecution();
//            }
//
//            Document itemPage = PageRetriever.getPage(itemUrl);
//            System.out.println("Now parsing item with ID: "+ item.getId());
//            Item parsedItem = parseOneItem(item, itemPage);
//
//            result.add(parsedItem);
//            itemStorage.addParsedItems(result);
//
//        }
//    }

    /**
     * Method that parse one item
     */

    public void parse(Item item) {

        String itemUrl = UrlBuilder.getItemLink(item.getId());

        if (isDelay) {
            ItemCrawler.suspendExecution();
        }

        Document itemPage = PageRetriever.getPage(itemUrl);
        System.out.println("Now parsing item with ID: " + item.getId());

        Element elementDiv = itemPage.select("div[class='js-adp-product-prices']").first();
        String price = getItemPrice(elementDiv);
        String color = getItemColor(itemPage);
        String actualPrice = getItemActualPrice(itemPage);
        String description = getItemDescription(itemPage);

        item.setPrice(price);
        item.setActualPrice(actualPrice);
        item.setColor(color);
        item.setDescription(description);


    }


    private String getItemColor(Document itemPage) {
        Element el = itemPage.getElementsByClass("adp-stylepicker-selected").first();
        return el.attr("title");

    }


    private String getItemDescription(Document itemPage) {
        String description = itemPage.select("div[itemprop='description']").first().text();
        if (description != null) {
            return description;
        } else {
            return "";
        }
    }


    private String getItemPrice(Element elementDiv) {
        Element el = elementDiv.select("h2[itemprop='price']").first();
        return el.attr("content");


    }


    private String getItemActualPrice(Element element) {
        Element el = element.getElementsByClass("actual-price").first();
        return el.attr("content");


    }
}
