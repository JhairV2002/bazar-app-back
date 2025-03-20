package jv.bazar.amacame.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class HeadersResponseInterceptor implements HandlerInterceptor {

    Logger log = LogManager.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            log.info("HeadersResponseInterceptor preHandle");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            return true;
        } catch (Exception e) {
            log.error("Error in HeadersResponseInterceptor {}", e.getMessage());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            log.info("HeadersResponseInterceptor postHandle");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers",
                    "Content-Type, Origin, Access-Control-Allow-Headers, cache-control, X-Requested-With");
        } catch (Exception e) {
            log.error("Error in HeadersResponseInterceptor {}", e.getMessage());
        }
    }
}
