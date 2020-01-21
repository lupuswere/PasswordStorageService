package net.lilifei.app.password.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebPageController {

    @RequestMapping(value = "/", produces = "text/html")
    public String homePage() {
        return "index.html";
    }
}
