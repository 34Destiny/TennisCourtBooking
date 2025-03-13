package bdbt.project.korttenisowy;

import bdbt.project.korttenisowy.klient.Klient;
import bdbt.project.korttenisowy.klient.KlientDAO;
import bdbt.project.korttenisowy.kort.Kort;
import bdbt.project.korttenisowy.kort.KortDAO;
import bdbt.project.korttenisowy.rezerwacja.Rezerwacja;
import bdbt.project.korttenisowy.rezerwacja.RezerwacjaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final KlientDAO klientDAO;
    private final KortDAO kortDAO;
    private final RezerwacjaDAO rezerwacjaDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminController(KlientDAO klientDAO, KortDAO kortDAO,RezerwacjaDAO rezerwacjaDAO) {
        this.klientDAO = klientDAO;
        this.kortDAO = kortDAO;
        this.rezerwacjaDAO = rezerwacjaDAO;
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("klienci", klientDAO.list());
        model.addAttribute("korty", kortDAO.findAll());
        model.addAttribute("rezerwacje", rezerwacjaDAO.findAll());
        return "admin/admin";
    }

    @GetMapping("/details/{id}")
    public String clientDetails(@PathVariable int id, Model model) {
        Klient klient = klientDAO.get(id);
        model.addAttribute("klient", klient);
        return "admin/klient_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteKlient(@PathVariable("id") int idKlient) {
        klientDAO.delete(idKlient);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateKlient(@ModelAttribute Klient klient) {
        if (klient.getHaslo() != null && !klient.getHaslo().isEmpty()) {
            klient.setHaslo(passwordEncoder.encode(klient.getHaslo()));
        } else {
            String existingHaslo = klientDAO.get(klient.getIdKlient()).getHaslo();
            klient.setHaslo(existingHaslo);
        }
        klientDAO.update(klient);
        return "redirect:/admin";
    }

    @GetMapping("/courts/add")
    public String addKortForm(Model model) {
        model.addAttribute("kort", new Kort());
        return "korty/add";
    }

    @PostMapping("/courts/save")
    public String saveKort(@ModelAttribute Kort kort) {
        kortDAO.save(kort);
        return "redirect:/admin";
    }

    @GetMapping("/courts/edit/{id}")
    public String editKortForm(@PathVariable int id, Model model) {
        Kort kort = kortDAO.findById(id);
        model.addAttribute("kort", kort);
        return "admin/kort_details";
    }

    @PostMapping("/courts/update")
    public String updateKort(@ModelAttribute Kort kort) {
        kortDAO.update(kort);
        return "redirect:/admin";
    }

    @GetMapping("/courts/delete/{id}")
    public String deleteKort(@PathVariable int id) {
        kortDAO.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/rezerwacje/usun/{id}")
    public String deleteReservation(@PathVariable int id) {
        rezerwacjaDAO.delete(id);
        return "redirect:/admin";
    }

}
