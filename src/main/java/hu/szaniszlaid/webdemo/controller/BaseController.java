package hu.szaniszlaid.webdemo.controller;

import hu.szaniszlaid.webdemo.domain.BaseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import static hu.szaniszlaid.webdemo.controller.BaseController.API_URL;

@RequestMapping(API_URL)
public class BaseController {
    static final String API_URL = "/api/";

    void validateEntityPost(BaseEntity entity) {
        if (entity.getId() != null) {
            //TODO log.v validation error
            throw new ConflictException("New entity during POST must not contain id value.");
        }
    }
}
