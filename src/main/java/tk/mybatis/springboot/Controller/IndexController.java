package tk.mybatis.springboot.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanguang
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public String home()
    {
        return "Hello myBatis!";
    }

}
