package ru.smb.smb.service;

/*
 * @author Alexandr.Yakubov
 **/

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smb.smb.model.User;
import ru.smb.smb.repository.UserRepository;

@Service
public class RequestService {

  @Autowired
  private final UserRepository userRepository;

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


  public HttpURLConnection PostRequest(Map<String, List<String>> params, String postPath)
      throws IOException {
    Authenticator auth = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password.toCharArray());
      }
    };
    User user = userRepository.getByLogin(username);
    URL url = new URL(user.getPath() + postPath);

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
      byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
      conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
      conn.getOutputStream().write(postDataBytes);
    }
    conn.disconnect();
    return conn;
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
