package com.siteparser;

import com.siteparser.domain.Item;

import com.siteparser.parser.Parser;

/**
 * Created with IntelliJ IDEA.
 * User: OV
 * Date: 18.04.17
 * Time: 23:23
 * To change this template use File | Settings | File Templates.
 */
public class ItemTask implements Runnable {


    Parser parser;
    Item item;

    public ItemTask(Item item, Parser parser) {
        this.item = item;
        this.parser = parser;

    }


    @Override
    public void run() {

//        String itemUrl = UrlBuilder.getItemLink(item.getId());
//
//        if (isDelay) {
//            ItemCrawler.suspendExecution();
//        }
        parser.parse(item);
//        Document itemPage = PageRetriever.getPage(itemUrl);
//        System.out.println("Now parsing item with ID: "+ item.getId());
//        Item parsedItem = parseOneItem(item, itemPage);
//
//        result.add(parsedItem);
//        itemStorage.addParsedItems(result);
//

    }
}
