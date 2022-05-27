package com.boraozdogan.kekikpanel.controller;

import com.boraozdogan.kekikpanel.api.TasksRoute;
import com.boraozdogan.kekikpanel.model.Task;
import com.boraozdogan.kekikpanel.model.TaskRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserPanelController {
    Logger logger = LoggerFactory.getLogger(UserPanelController.class);

    @Value("${boz.app.name}")
    private String appName;
    @Value("${boz.app.apiurl}")
    private String apiURL;

    @Autowired
    private TasksRoute tasksRoute;

    @PostMapping("/user/createtask")
    public String createTask(
            @RequestParam("owner") String owner,
            @RequestParam("body") String body
    ) {
//        // NOTE(bora): Make internal API call over HTTP
//        var restTemplate = new RestTemplate();
//        var response = restTemplate.postForObject(
//                apiURL + "/tasks",
//                Map.of("owner", owner,
//                        "body", body),
//                Task.class);

        // NOTE(bora): Call internal API directly
        var response = tasksRoute.newTask(
                new TaskRequestModel(owner, body));

        logger.info("Record added: {}", response);
        return "user_panel";
    }

}
