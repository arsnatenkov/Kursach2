package application.controller;

import application.entity.Favourite;
import application.entity.Offer;
import application.entity.User;
import application.service.FavouriteService;
import application.service.OfferService;
import application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private OfferService offerService;
    @Autowired
    private UserService userService;
    @Autowired
    private FavouriteService favouriteService;

    @GetMapping(value = "/visitor/account")
    public ModelAndView account() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<Offer> offers = offerService.findByHostId(user.getId());
        List<Favourite> favourites = favouriteService.findByUserId(user.getId());
        StringBuilder sb = new StringBuilder();

        sb.append("<ul>");
        for (Offer offer : offers)
            sb.append("<li class=\"offer-list\">")
                    .append(offer.linkTitle("list-norm-font", "messages"))
                    .append("&nbsp;&nbsp;").append(offer.editBtn()).append("</li><br/>");
        sb.append("</ul>");

        modelAndView.addObject("hostedOffers", sb.toString());
        sb = new StringBuilder();

        sb.append("<ul>");
        for (Favourite favourite : favourites)
            sb.append("<li class=\"offer-list\">").append(offerService.findById(favourite.getOfferId())
                    .linkTitle("list-norm-font", "conversation"))
                    .append("</li><br/>");
        sb.append("</ul>");

        modelAndView.addObject("favouriteOffers", sb.toString());

        modelAndView.setViewName("/visitor/account");
        return modelAndView;
    }
}
