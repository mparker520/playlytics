package com.mparker.playlytics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwarderController {

    @RequestMapping(value = { "/", "/{path:^(?!api|static|.*\\..*$).*$}", "/**/{path:^(?!api|static|.*\\..*$).*$}" })
    public String forwardToIndex() {
        return "forward:/index.html";
    }

}
