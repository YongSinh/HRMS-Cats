package com.cats.informationmanagementservice;

import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

@SpringBootTest
@RequiredArgsConstructor
class InformationManagementServiceApplicationTests {

    @Test
    void contextLoads() throws JSONException {
        JSONObject item = new JSONObject();
        item.put("information", "test");
        item.put("id", 3);
        item.put("name", "course1");
        System.out.println(item);
    }

}
