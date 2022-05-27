package com.boraozdogan.kekikpanel.controller;

import com.boraozdogan.kekikpanel.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Controller
public class UserPanelController {
    @Value("${boz.app.name}")
    private String appName;

    @Value("${boz.app.apiurl}")
    private String apiURL;

    @PostMapping("/user/createtask")
    public String createTask(
            @RequestParam("owner") String owner,
            @RequestParam("body") String body
    ) {
        // NOTE(bora): Make internal API call over HTTP
        var restTemplate = new RestTemplate();
        var response = restTemplate.postForObject(
                apiURL + "/tasks",
                Map.of("owner", owner,"body", body),
                Task.class);

        System.out.println("[UserPanelController#createTask]:: Record added: " + response);

        return "user_panel";
    }

}
