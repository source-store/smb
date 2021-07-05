package ru.smb.smb;

/*
 * @author Alexandr.Yakubov
 **/

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.smb.smb.control.json.JsonUtil;
import ru.smb.smb.model.User;

public class TestUtil {
  public static String getContent(MvcResult result) throws UnsupportedEncodingException {
    return result.getResponse().getContentAsString();
  }

  public static <T> T readFromJsonResultActions(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
    return readFromJsonMvcResult(action.andReturn(), clazz);
  }

  public static <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
    return JsonUtil.readValues(getContent(result), clazz);
  }

  public static <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
    return JsonUtil.readValue(getContent(result), clazz);
  }

  public static RequestPostProcessor userHttpBasic(User user) {
    return SecurityMockMvcRequestPostProcessors.httpBasic(user.getLogin(), user.getPassword());
  }
}
