package cz.kalas.samples.dogstation;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
//@Slf4j
public class CustomHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse, FilterChain chain) throws
            IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) httpResponse;
        HttpServletRequest request = (HttpServletRequest) httpRequest;

        response.addHeader("X-Accel-Buffering", "no");

//        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Methods", "GET,OPTIONS,PUT,DELETE");

//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");


        response.setHeader("Content-Type", "text/event-stream");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // print all response headers
        for (String headerName : response.getHeaderNames()) {
//            log.debug("response header [" + headerName + "] val [" + response.getHeader(headerName) + "]");
        }

        chain.doFilter(request, response);
    }

}

