package bg.softuni.autoTraderExperience.interceptors;

import bg.softuni.autoTraderExperience.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class LoggingInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info(request.getRequestURI());
        logger.info(request.getAuthType() + request.getPathInfo());
        Iterator<String> iter = request.getHeaderNames().asIterator();
        while (iter.hasNext()) {
            String header = iter.next();
            String headerValue = request.getHeader(header);
            logger.info(header + headerValue);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        if (modelAndView == null) {
            return;
        }
        Map<String, Object> model = modelAndView.getModel();
        for (String key : model.keySet()) {
            logger.info(modelAndView.getModel().get(key).toString());
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info(String.valueOf(response.getStatus()));
        logger.info(Arrays.toString(request.getCookies()));
    }
}
