package com.pushtech.crawler.launcher;

import static com.pushtech.crawler.logging.LoggingHelper.logger;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pushtech.commons.Product;
import com.pushtech.crawler.beans.Page;

public class CrawlOffer {
   private static final Locale CURRENT_LOCALE = Locale.FRENCH;

   public Product doAction(Page page) {

      Product product = new Product();
      final Document productPageDocument = page.getDoc();
      String productId = null;
      try {
         productId = getProductId(productPageDocument);
      } catch (Exception e1) {
         logger.error(e1.getMessage() + " on " + page.getUrl());
      }
      logger.debug("Product id : " + productId);

      product.setId(productId);
      product.setParentId(productId);

      String reference = null;
      try {
         reference = getReference(productPageDocument);
      } catch (Exception e1) {
         logger.error(e1.getMessage() + " on " + page.getUrl());
      }
      logger.debug("Reference : " + reference);

      product.setReference(reference);

      String name = null;
      try {
         name = getName(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setName(name);
      logger.debug("Name : " + name);

      String description = null;
      try {
         description = getDescription(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setDescription(description);
      logger.debug("Description : " + description);

      String shortDescription = null;
      try {
         shortDescription = getShortDescription(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setShortDescription(shortDescription);
      logger.debug("Short description : " + shortDescription);

      String brand = "";
      product.setBrand(brand);
      logger.debug("Brand : " + brand);

      String category = null;
      try {
         category = getCategory(productPageDocument);
         product.setCategory(category);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      logger.debug("Category : " + category);

      String image = null;
      try {
         image = getImage(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setImage(image);
      logger.debug("Image : " + image);

      List<String> imagesList = null;
      try {
         imagesList = getImages(image, productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setImages(imagesList);

      float price = -1f;
      try {
         price = getPrice(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setPrice(price);
      logger.debug("Price : " + price);

      String strKeyWord = "Empty";

      product.setKeyWord(strKeyWord);
      logger.debug("KeyWord : " + strKeyWord);

      float shippingCost = getShippingCost(productPageDocument);
      product.setShippingCost(shippingCost);
      logger.debug("Shipping cost : " + shippingCost);

      int shippingDelay = 0;
      shippingDelay = getShippingDelay(getShippingDelayRaw(productPageDocument));
      product.setShippingDelay(shippingDelay);
      logger.debug("Shipping delay : " + shippingDelay);

      int quantity = 0;
      try {
         quantity = getQuantity(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setQuantity(quantity);
      logger.debug("Quantity : " + quantity);

      return product;
   }

   public String getProductId(final Document productPageDocument) throws Exception {
      final Element productIdElement = productPageDocument.select(Selectors.PRODUCT_IDENTIFIER).first();
      String productIdRaw = null;
      if (productIdElement != null) {
         productIdRaw = productIdElement.attr("productid");
      }
      return productIdRaw;
   }

   public String getReference(final Document productPageDocument) throws Exception {
      final Element referenceElement = productPageDocument.select(Selectors.PRODUCT_REFERENCE).first();
      String referenceRaw = null;
      if (referenceElement != null) {
         referenceRaw = referenceElement.text();
      }
      return referenceRaw;
   }

   // example
   private String getName(final Document productPageDocument) throws Exception {
      final Element nameElement = findElement(productPageDocument, Selectors.PRODUCT_NAME); // TODO
      String name = fromElementText(nameElement);
      name = validateField(name, "Name");
      return name;
   }

   private String getLink(final Document productPageDocument) throws Exception {
      final Element linkElement = findElement(productPageDocument, Selectors.PRODUCT_LINK); // TODO
      String link = fromAttribute(linkElement, "href");
      link = validateField(link, "Link");
      return link;
   }

   private String getShortDescription(final Document productPageDocument) throws Exception {
      final Element descriptionElement = findElement(productPageDocument, Selectors.PRODUCT_SHORT_DESCRIPTION); // TODO
      String description = descriptionElement.text();
      description = validateField(description, "Short Description");
      return description;
   }

   private String getDescription(final Document productPageDocument) throws Exception {
      final Element descriptionElement = findElement(productPageDocument, Selectors.PRODUCT_DESCRIPTION); // TODO
      String description = fromAttribute(descriptionElement, "content");
      description = validateField(description, "Description");
      return description;
   }

   private String getKeywords(final Document productPageDocument) throws Exception {
      final Element descriptionElement = findElement(productPageDocument, Selectors.PRODUCT_KEYWORDS); // TODO
      String description = fromAttribute(descriptionElement, "content");
      description = validateField(description, "Description");
      return description;
   }

   private String getBrand(final Document productPageDocument) throws Exception {
      // final Element brandElement = findElement(productPageDocument, Selectors.PRODUCT_BRAND); // TODO
      // String brand = fromElementText(brandElement);
      // brand = validateField(brand, "Brand");
      return "aaaaaaaaaaaa";
   }

   private String getCategory(final Document productPageDocument) throws Exception {
      final Element categoryElement = findElement(productPageDocument, Selectors.PRODUCT_CATEGORY); // TODO
      String category = fromElementText(categoryElement);
      category = cleanCategory(validateField(category, "Category"));
      return category;
   }

   private String cleanCategory(String category) {
      if (category != null && category.contains(">")) {
         category = category.substring(category.lastIndexOf(">") + 1).trim();
         category = category.substring(0, category.indexOf(" ")).trim();
         return category;
      } else return category != null ? category.trim() : null;
   }

   private String getImage(final Document productPageDocument) throws Exception {
      final Element imageElement = findElement(productPageDocument, Selectors.PRODUCT_IMAGE); // TODO
      String image = fromAttribute(imageElement, "src");
      image = validateField(image, "Image");
      image = cleanPath(image);
      return image;
   }

   private List<String> getImages(final String firstImage, final Document productPageDocument) throws Exception {
      final Elements imageElements = findElements(productPageDocument, Selectors.PRODUCT_IMAGE);
      System.out.println("Images " + imageElements.size());
      List<String> imagesList = new ArrayList<String>();
      String image = null;
      for (Element element : imageElements) {
         image = cleanPath(fromAttribute(element, "src"));
         if (image.equals(firstImage)) {
            image = "";
         }
         if (imagesList.size() == 6) break;
         imagesList.add(image);
      }
      return imagesList;
   }

   private float getPrice(final Element element) {
      final Element priceElement = findElement(element, Selectors.PRODUCT_PRICE);
      String priceRaw = fromAttribute(priceElement, "data-exact-price");
      priceRaw = validateField(priceRaw, "Price", 1);
      return parseLocalizedPrice(priceRaw.replace(".", ","));
   }

   private float getShippingCost(final Element element) {
      final Element shippingCostElement = findElement(element, Selectors.PRODUCT_SHIPPING_COST);// TODO
      String ShippingCostRaw = fromElementText(shippingCostElement);
      ShippingCostRaw = validateField(ShippingCostRaw, "Shipping price", 0);
      return parseLocalizedPrice(ShippingCostRaw);
   }

   private int getQuantity(final Element element) throws Exception {
      Element quantityElement = findElement(element, Selectors.PRODUCT_QUANTITY);// TODO
      String quantityRaw = fromElementText(quantityElement);
      quantityRaw = validateField(quantityRaw, "Quantity", 1);
      try {
         return Integer.parseInt(quantityRaw.replaceAll("[^\\d]", ""));
      } catch (Exception e) {
         System.err.println("Unparsable quantity raw : " + quantityRaw);
         return 0;
      }
   }

   private int getShippingDelay(final String delayRaw) {// TODO
      if (StringUtils.isNotBlank(delayRaw)) {
         if (StringUtils.equalsIgnoreCase(delayRaw, "0")) return Integer.getInteger(delayRaw);
         String lcRawDelivery = StringUtils.lowerCase(delayRaw);
         lcRawDelivery = StringUtils.substringBeforeLast(delayRaw, ",");
         Pattern p = Pattern.compile("(\\d+)\\sjour");
         Matcher m = p.matcher(lcRawDelivery);
         if (m.find()) {
            return Integer.parseInt(m.group(1));
         } else {
            logger.error("Delay not parseable [" + delayRaw + "]");
         }

      }
      return 0;
   }

   private String getShippingDelayRaw(final Element element) {
      Element shippingDelayElement = findElement(element, Selectors.PRODUCT_DELIVERY);
      if (shippingDelayElement != null) return "0";
      shippingDelayElement = findElement(element, "strong.inStock+strong");
      String shippingDelayRaw = fromElementText(shippingDelayElement);
      shippingDelayRaw = validateField(shippingDelayRaw, "Raw delivery", 1);
      return shippingDelayRaw;
   }

   private String fromAttribute(final Element element, final String attr) {
      if (element != null) {
         String text = element.attr(attr);
         // text = text.replace(CARACTERE_ESPACE, " ");
         return StringUtils.trim(text);
      }
      return null;
   }

   private float parseLocalizedPrice(final String priceRaw) {
      final String priceText = cleanPrice(priceRaw);
      // logger.warn("price test " + priceRaw);
      if (StringUtils.isNotBlank(priceText)) {
         try {
            NumberFormat priceFormat = NumberFormat.getNumberInstance(CURRENT_LOCALE);
            Number priceNumber = priceFormat.parse(priceText);
            // return (float) (priceNumber.floatValue() * (1.2)));
            return priceNumber.floatValue();
         } catch (ParseException pexc) {
            logger.error("Price number not parseable [" + priceText + "]");
         }
      }
      return -1f;
   }

   private String cleanPrice(final String priceRaw) {
      return priceRaw.replaceAll("[^\\d.,]", "").replace(".", ",");
   }

   private Element findElement(final Element element, final String cssSelector) {
      return element.select(cssSelector).first();
   }

   private Elements findElements(final Element element, final String cssSelector) {
      return element.select(cssSelector);
   }

   private String fromElementText(final Element element) {
      if (element != null) {
         String text = element.text();
         text = StringEscapeUtils.unescapeHtml4(text);
         // text = text.replace(CARACTERE_ESPACE, " ");
         return StringUtils.trim(text);
      }
      return null;
   }

   private String fromOwnElementText(final Element element) {
      if (element != null) {
         String text = element.ownText();
         text = StringEscapeUtils.unescapeHtml4(text);
         return StringUtils.trim(text);
      }
      return null;
   }

   private String validateField(final String value, final String name) throws Exception {
      if (StringUtils.isBlank(value)) {
         throw new NullPointerException(name + " not found");
      }
      return value;
   }

   private String validateField(final String value, final String name, final int log) {
      if (StringUtils.isBlank(value)) {
         if (log == 2) logger.error("" + name + " not found");
         else if (log == 1) logger.warn("" + name + " not found");
         return StringUtils.EMPTY;
      }
      return value;
   }

   private static String cleanPath(String path) {
      if (!StringUtils.startsWith(path, "http:")) {
         return "http://www.grossiste-en-ligne.com" + path;
      }
      return path;
   }

}
