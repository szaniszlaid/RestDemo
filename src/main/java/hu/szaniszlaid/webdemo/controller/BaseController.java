package hu.szaniszlaid.webdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import static hu.szaniszlaid.webdemo.controller.BaseController.API_URL;

@RequestMapping(API_URL)
public class BaseController {
    public static final String API_URL = "/api/";
}
