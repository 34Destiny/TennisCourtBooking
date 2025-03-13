package bdbt.project.korttenisowy;

import bdbt.project.korttenisowy.klient.Klient;
import bdbt.project.korttenisowy.klient.KlientDAO;
import bdbt.project.korttenisowy.rezerwacja.Rezerwacja;
import bdbt.project.korttenisowy.rezerwacja.RezerwacjaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    private KlientDAO klientDAO;

    @Autowired
    private RezerwacjaDAO rezerwacjaDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Klient klient = klientDAO.findByEmail(email);
        List<Rezerwacja> rezerwacje = rezerwacjaDAO.findByClientId(klient.getIdKlient());

        model.addAttribute("user", klient);
        model.addAttribute("rezerwacje", rezerwacje);

        return "user/profile";
    }


    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Klient user, Authentication authentication) {
        Klient currentUser = klientDAO.get(user.getIdKlient());

        if (user.getHaslo() == null || user.getHaslo().isEmpty()) {
            user.setHaslo(currentUser.getHaslo());
        } else {
            user.setHaslo(passwordEncoder.encode(user.getHaslo()));
        }

        String currentEmail = currentUser.getKontakt().getEmail();
        String newEmail = user.getKontakt().getEmail();
        boolean emailChanged = !currentEmail.equals(newEmail);

        klientDAO.update(user);

        if (emailChanged) {
            return "redirect:/logout";
        }
        return "redirect:/profile";
    }
}
