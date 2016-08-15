package com.pushtech.crawler.connection;

import static com.pushtech.crawler.logging.LoggingHelper.logger;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;

public class ConnectionHandler {

   public static HttpResponse getResponse(String url, HashMap<String, String> parameters, HashMap<String, String> headers, EngineContext.MethodType method) {

      HttpResponse response = null;

      EngineConnection engineConnection = EngineConnection.getDefaultEngineConnection();
      EngineContext engineContext = EngineContext.getEngineContext(url, parameters, headers, engineConnection);

      try {
         DefaultHttpClient httpClient = engineConnection.getHttpClient();
         response = httpClient.execute(engineContext.getFormedRequest(engineContext, method), engineContext.createDefaultPolicy());
      } catch (ClientProtocolException e) {
         // TODO Auto-generated catch block
         logger.fatal(e.getMessage());
      } catch (IOException e) {
         // TODO Auto-generated catch block
         logger.fatal(e.getMessage());
      }

      return response;
   }
}
