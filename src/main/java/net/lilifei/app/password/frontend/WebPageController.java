package net.lilifei.app.password.frontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class WebPageController {

    @RequestMapping(value = "/", produces = "text/html")
    public String homePage(final HttpServletRequest httpServletRequest) {
        return "index.html";
    }
}
