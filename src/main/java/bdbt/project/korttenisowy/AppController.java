package bdbt.project.korttenisowy;

import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AppController implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin/admin");
        registry.addViewController("/profile").setViewName("user/profile");
        registry.addViewController("/reservation").setViewName("user/reservation");

        registry.addViewController("/register").setViewName("register");
    }

    @RequestMapping(value={"/admin"})
    public String showAdminPage(Model model) {
        return "admin/admin";
    }

    @RequestMapping(value={"/profile"})
    public String showUserPage(Model model) {
        return "user/profile";
    }


}


