package ru.smb.smb.control;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import ru.smb.smb.TestUtil;
import ru.smb.smb.control.json.JsonUtil;
import ru.smb.smb.model.SmbBox;
import ru.smb.smb.to.SmbBoxTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.smb.smb.control.RestBox.REST_URL;


/*
 * @author Alexandr.Yakubov
 **/

public class RestBoxTest extends AbstractControllerTest {

    @Test
    void PutAndGetAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"box\":\"messageBox1\"},{\"box\":\"messageBox2\"},{\"box\":\"messageBox3\"},{\"box\":\"messageBox4\"}]")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("login2", "login2")))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("login2", "login2")))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

    @Test
    void createWithLocation() throws Exception {
        String bodyJson = "[";
        int repeat = 10;
        for (int i = 0; i < repeat; i++) {
            bodyJson = bodyJson + JsonUtil.writeValue(new SmbBoxTo("test"+i, null));
        }
        bodyJson = bodyJson + "]";

        for (int i = 0; i < repeat; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJson.replace("}{", "},{"))
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("login2", "login2")))
                    .andDo(print())
                    .andExpect(status().isCreated());

            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("login2", "login2")))
                .andDo(print())
                .andExpect(status().isAccepted());

            List<SmbBox> list = TestUtil.readListFromJsonMvcResult(actions.andReturn(), SmbBox.class);
            assertEquals(list.size(), 10);
        }
    }
}

