package com.pushtech.crawler.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import liquibase.util.StreamUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.pushtech.crawler.beans.Page;

public class PageParser implements ParsingTemplate {

   protected PageParser() {

   }

   @Override
   public Page parse(Object entry, Object object, HashMap<String, String> parameters) {
      if ((object instanceof HttpResponse)) { // refa httpResponse n miditra d bean page n mivoaka

         int statusCode = 0;
         String url = null;
         String redirectionUrl = null;
         String content = null;
         Document doc = null;
         statusCode = ((HttpResponse) object).getStatusLine().getStatusCode();

         if (statusCode == 200) {
            try {
               content = getContent((HttpResponse) object);
            } catch (Exception e) {
               e.printStackTrace();
            }
            doc = getDocument(content);
            return new Page(statusCode, content, doc);
         }

         return null;

      }
      return null;
   }

   // TODO enlever le UTF-8
   private String getContent(HttpResponse response) throws UnsupportedEncodingException, IllegalStateException, IOException {
      Header contentEncodingHeader = response.getFirstHeader("Content-Encoding");
      if (contentEncodingHeader != null) {
         String encoding = contentEncodingHeader.getValue();
         if (encoding.contains("gzip")) {
            InputStreamReader reader = new InputStreamReader(new GzipDecompressingEntity(response.getEntity()).getContent(), "iso-8859-1");
            String content = StreamUtil.getReaderContents(reader);
            return StringUtils.trim(validateContent(content));

         }
      }
      return StringUtils.trim(validateContent(EntityUtils.toString(response.getEntity(), "utf-8")));
   }

   private String validateContent(String content) {
      String START_KEY_WORD = "#productsList";
      String END_KEY_WORD = "img.lazy";
      content = content.replace("\\'", "'").replace("\n", " ").replace("\\n", " ").replace("\\\"", "\"").replace("\\/", "/");
      if (content.contains(START_KEY_WORD) && content.contains(END_KEY_WORD)) {
         content = content.substring(content.indexOf(START_KEY_WORD), content.indexOf(END_KEY_WORD));
         content = content.substring(content.indexOf("<"), content.lastIndexOf(">") + 1);
      }
      return content;
   }

   private Document getDocument(String content) {
      return Jsoup.parse(validateContent(content));
   }

   @Override
   public ParsingTemplate getAppropriateParsingTemplate(Object object) {
      // TODO Auto-generated method stub
      return null;
   }

}
