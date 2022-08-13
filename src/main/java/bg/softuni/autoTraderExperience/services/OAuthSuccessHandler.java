package bg.softuni.autoTraderExperience.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    public OAuthSuccessHandler(UserService userService) {
        this.userService = userService;
        setDefaultTargetUrl("/");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {

        if (authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
            String email = oAuth2AuthenticationToken.getPrincipal().getAttribute("email").toString();
            userService.createUserIfNotExist(email);
            userService.login(email);

        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
