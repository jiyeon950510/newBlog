package shop.mtcoding.newblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class tempController {

    @GetMapping("/temp")
    public String temp() {
        return "temp";
    }
}
