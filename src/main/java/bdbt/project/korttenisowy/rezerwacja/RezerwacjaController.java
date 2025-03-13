package bdbt.project.korttenisowy.rezerwacja;

import bdbt.project.korttenisowy.klient.Klient;
import bdbt.project.korttenisowy.klient.KlientDAO;
import bdbt.project.korttenisowy.kort.Kort;
import bdbt.project.korttenisowy.kort.KortDAO;
import bdbt.project.korttenisowy.rezerwacja.RezerwacjaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RezerwacjaController {

    private final KortDAO kortDAO;
    private final RezerwacjaDAO rezerwacjaDAO;

    @Autowired
    private KlientDAO klientDAO;

    public RezerwacjaController(KortDAO kortDAO,RezerwacjaDAO rezerwacjaDAO) {
        this.kortDAO = kortDAO;
        this.rezerwacjaDAO = rezerwacjaDAO;
    }

    @GetMapping("/reservation")
    public String showReservationPage(Model model) {
        model.addAttribute("korty", kortDAO.findAll());
        return "user/reservation";
    }

    @PostMapping("/reserve")
    public String reserveKort(@ModelAttribute Rezerwacja rezerwacja) {
        rezerwacjaDAO.save(rezerwacja);
        return "redirect:/reservation";
    }

    @GetMapping("/reserve/{idKort}")
    public String showReservationPage(@PathVariable int idKort, Model model) {
        // Pobierz aktualnie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Email użytkownika

        // Pobierz ID klienta na podstawie emaila
        Klient klient = klientDAO.findByEmail(email);
        int klientId = klient.getIdKlient();

        Kort kort = kortDAO.findById(idKort);
        if (kort == null) {
            throw new IllegalArgumentException("Kort o podanym ID nie istnieje");
        }

        model.addAttribute("kort", kort);
        model.addAttribute("klientId", klientId);
        return "user/details_reservation";
    }

    @GetMapping("/rezerwacje/usun/{id}")
    public String deleteReservation(@PathVariable int id) {
        rezerwacjaDAO.delete(id);
        return "redirect:/profile";
    }
}
