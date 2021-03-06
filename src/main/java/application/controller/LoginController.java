package application.controller;

import application.entity.User;
import application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * Метод перехода на страницу аутентификации
     * @return Модель страницы
     */
    @GetMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * Метод перехода на страницу регистрации
     * @return Модель перехода
     */
    @GetMapping(value = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    /**
     * Метод для регистрации пользователя и добавления данных в БД
     * @param user Пользователь
     * @param bindingResult Получение ошибок для последующего вывода
     * @return Модель страницыы
     */
    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        String message = "There is already a user registered with the user name provided";
        if (userService.findUserByEmailOrUserName(user.getEmail(), user.getUserName()) != null) {
            bindingResult.rejectValue("userName", "error.user", message);
        }

        message = "User has been registered successfully";
        if (!bindingResult.hasErrors()) {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", message);
            modelAndView.addObject("user", new User());
        }

        modelAndView.setViewName("registration");
        return modelAndView;
    }
}