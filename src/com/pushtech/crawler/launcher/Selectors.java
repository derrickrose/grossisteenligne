package com.pushtech.crawler.launcher;

public class Selectors {

   // product page
   public static final String PRODUCT_PAGE_IDENTIFIER = "div.productViewContent";
   public static final String PRODUCT_NAME = ".productName.noBg>span";
   public static final String PRODUCT_LINK = "head > link";
   public static final String PRODUCT_DESCRIPTION = "meta[name=Description]";
   public static final String PRODUCT_BRAND = "";
   public static final String PRODUCT_KEYWORDS = "meta[name=keywords]";
   public static final String PRODUCT_IDENTIFIER = ".item_submit_button";
   public static final String PRODUCT_CATEGORY = ".crumb0>a";// listing
   public static final String PRODUCT_IMAGE = ".slide > img";
   public static final String PRODUCT_PRICE = "#articlePrice.price >span > span#price";
   public static final String PRODUCT_DECIMAL_PRICE = "#price_decimals";

   public static final String PRODUCT_SHIPPING_COST = ".inlineBlock:contains(Livraison)";
   public static final String PRODUCT_SHIPPING_DELAY = ".availability.inctdclks>p>strong:not([itemprop])";

   public static final String PRODUCT_QUANTITY = ".max_stock";
   public static final String PRODUCT_DELIVERY = "strong.notInStock";
   public static final String PRODUCT_VARIANTE = "span[id*=select_product]>select>option:not(:contains(Choisissez votre))";

   // listing page
   public static final String LISTING_PAGE_IDENTIFIER = "ul#productsList>li";
   public static final String LISTING_PAGE_PRODUCTS = "ul#productsList>li";
   public static final String LISTING_PAGE_PRODUCT_LINK = "div.productBox>a";
   public static final String NEXT_PAGE_LINK = "ul.pagination>li>a:contains(>)";

   // home page
   public static final String ALL_LISTING = "ul.mainNav>li>a"; //
   public static final String HOME_PAGE_IDENTIFIER = "map#map";

}
