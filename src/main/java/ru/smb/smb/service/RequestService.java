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
import org.springframework.stereotype.Service;
import ru.smb.smb.model.User;
import ru.smb.smb.repository.UserRepository;

@Service
public class RequestService {

  @Autowired
  private UserRepository userRepository;

  public RequestService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

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
    try {
      User user = userRepository.getByLogin(username);
      URL url = new URL(user.getPath() + postPath);
      System.out.println(url.getHost());

      url.toString().replace(url.getPath(), "");
      StringBuilder postData = new StringBuilder();

      for (Map.Entry<String, List<String>> pair : params.entrySet()) {
        for (String str : pair.getValue()) {
          postDataAppend(postData, pair.getKey(), str);
        }
      }
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setAuthenticator(auth);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);
      if (postData.length() > 0) {
        postData.append("]");
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.getOutputStream().write(postDataBytes);
      }

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

  private void postDataAppend(StringBuilder postData, String key, String date) {
    if (postData.length() == 0) {
      postData.append("[{");
    } else {
      postData.append(",{");
    }
    postData.append('\"' + key + '\"');
    postData.append(':');
    postData.append('\"' + date + '\"');
    postData.append('}');
  }

}
