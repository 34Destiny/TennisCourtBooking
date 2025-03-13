package bdbt.project.korttenisowy;

import bdbt.project.korttenisowy.klient.KlientDAO;
import bdbt.project.korttenisowy.klient.Klient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final KlientDAO klientDAO;

    public RegisterController(KlientDAO klientDAO) {
        this.klientDAO = klientDAO;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("klient", new Klient());
        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute Klient klient) {
        if (klient.getAdres() == null || klient.getAdres().getKraj() == null) {
            throw new IllegalArgumentException("Dane adresowe są wymagane!");
        }
        klientDAO.save(klient);
        return "redirect:/index";
    }
}
