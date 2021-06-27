package beathub.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("index")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getLoginView() {
        return "index.html";
    }

}
