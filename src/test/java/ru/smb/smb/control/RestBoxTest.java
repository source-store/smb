package ru.smb.smb.control;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.smb.smb.control.json.JsonUtil;
import ru.smb.smb.to.SmbBoxTo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.smb.smb.control.RestBox.REST_URL;


/*
 * @author Alexandr.Yakubov
 **/

public class RestBoxTest extends AbstractControllerTest {

    @Test
    void getAll() {
    }

    @Test
    void createWithLocation() throws Exception {
        String bodyJson = "[";
        for (int i = 0; i < 10; i++) {
            bodyJson = bodyJson + JsonUtil.writeValue(new SmbBoxTo("test1", null));
        }
        bodyJson = bodyJson + "]";

        for (int i = 0; i < 10; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJson.replace("}{", "},{"))
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("login2", "login2")))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

    }
}

