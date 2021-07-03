package ru.smb.smb.service;

/*
 * @author Alexandr.Yakubov
 **/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import ru.smb.smb.repository.UserRepository;

@Component
public class RequestService {

  @Autowired
  private UserRepository userRepository;


  private String username;
  private String password;

  public void setUsername(String username) {
    this.username = username;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public void PostRequest(Map<String, List<String>> params, String postPath) {
    Authenticator auth = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password.toCharArray());
      }
    };

//    User user = userRepository.getByLogin(username);

    try {
//      URL url = new URL(user.getUser().getPath()+postPath);
      URL url = new URL("http://localhost:8080/smb/rest/box");
      System.out.println(url.getHost());

      url.toString().replace(url.getPath(), "");
      StringBuilder postData = new StringBuilder();

      for (Map.Entry<String, List<String>> pair : params.entrySet()) {
        for (String str : pair.getValue()) {
          if (postData.length() == 0) {
            postData.append("[{");
          } else {
            postData.append(",{");
          }
          postData.append('\"' + pair.getKey() + '\"');
          postData.append(':');
          postData.append('\"' + str + '\"');
          postData.append('}');
        }
      }
      if (postData.length() > 0) {
        postData.append("]");
      }
      System.out.println(postData);
      byte[] postDataBytes = postData.toString().getBytes("UTF-8");

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setAuthenticator(auth);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
      conn.setDoOutput(true);
      conn.getOutputStream().write(postDataBytes);

      if (conn.getResponseCode() >= 300) {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
      String output;
      System.out.println("Server output .... \n");
      while ((output = br.readLine()) != null) {
        System.out.println(output);
      }
      conn.disconnect();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}
