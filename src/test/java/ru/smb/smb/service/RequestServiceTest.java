package ru.smb.smb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.smb.smb.control.AbstractControllerTest;


/*
 * @author Alexandr.Yakubov
 **/

class RequestServiceTest extends AbstractControllerTest {

  @Test
  void postRequest() {
    Map<String, List<String>> params = new HashMap<>();
    List<String> list = new ArrayList<>();
    params.put("box", list);
    params.get("box").add("Freddie the Fish");
    params.get("box").add("fishie@seamail.example.com");
    params.get("box").add("10394");
    params.get("box").add(
        "Shark attacks in Botany Bay have gotten out of control. We need more defensive dolphins to protect the schools here, but Mayor Porpoise is too busy stuffing his snout with lobsters. He's so shellfish.");

    requestService.setUsername("login2");
    requestService.setPassword("login2");
    requestService.PostRequest(params, "/smb/rest/box");
  }
}