package com.boraozdogan.kekikpanel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class AdminPanelController {
    @Value("${boz.app.name}")
    private String appName;



}
