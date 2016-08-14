package com.pushtech.commons;

import java.util.List;

public class Product implements Cloneable {

   private String id = null, parentId = null;
   private String reference = null;
   private String name = null, link = null, description = null, shortDescription = null, brand = null, category = null, image = null;

   public String getReference() {
      return reference;
   }

   public void setReference(String reference) {
      this.reference = reference;
   }

   private String keyWord = null;
   private String updated = null;
   private float price = -1f, shippingCost = -1f, previousPrice = -1;
   private int shippingDelay = 0, quantity = 0;
   private String colorName = null;
   private String SizeName = null;
   private List<String> images = null;

   public String getColorName() {
      return colorName;
   }

   public void setColorName(String colorName) {
      this.colorName = colorName;
   }

   public String getShortDescription() {
      return shortDescription;
   }

   public void setShortDescription(String shortDescription) {
      this.shortDescription = shortDescription;
   }

   public List<String> getImages() {
      return images;
   }

   public void setImages(List<String> images) {
      this.images = images;
   }

   public String getSizeName() {
      return SizeName;
   }

   public void setSizeName(String sizeName) {
      SizeName = sizeName;
   }

   public void setKeyWord(String keyWord) {
      this.keyWord = keyWord;
   }

   public String getUpdated() {
      return updated;
   }

   public void setUpdated(String date) {
      this.updated = date;
   }

   public String getKeyWord() {
      return keyWord;
   }

   public String getId() {
      return id;
   }

   public String getParentId() {
      return parentId;
   }

   public String getName() {
      return name;
   }

   public String getLink() {
      return link;
   }

   public String getDescription() {
      return description;
   }

   public String getBrand() {
      return brand;
   }

   public String getCategory() {
      return category;
   }

   public String getImage() {
      return image;
   }

   public float getPrice() {
      return price;
   }

   public float getShippingCost() {
      return shippingCost;
   }

   public int getShippingDelay() {
      return shippingDelay;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setParentId(String parentId) {
      this.parentId = parentId;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setLink(String link) {
      this.link = link;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public void setPrice(float price) {
      this.price = price;
   }

   public void setShippingCost(float shippingCost) {
      this.shippingCost = shippingCost;
   }

   public void setShippingDelay(int shippingDelay) {
      this.shippingDelay = shippingDelay;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public float getPreviousPrice() {
      return previousPrice;
   }

   public void setPreviousPrice(float previousPrice) {
      this.previousPrice = previousPrice;
   }

   public String toString() {
      return "Product : " + id + " - " + name;
   }

   public Object clone() {
      Product product = null;
      try {
         product = (Product) super.clone();
      } catch (CloneNotSupportedException e) {
         e.printStackTrace();
      }
      return product;
   }
}
