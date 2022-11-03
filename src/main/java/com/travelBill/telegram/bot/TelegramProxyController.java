package com.travelBill.telegram.bot;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class TelegramProxyController {
    /*
    * The main purpose of this proxy controller is to proxy request to telegram proxy webhook. Details:
    * Heroku allow us to use 1 port (e.g. 80). And TG bot webhook launch proxy server at 8090, which should be public.
    * So, this controller do:
    * Internet -> TB API:80/tg-proxy/callback/telegram -> WEBHOOK proxy:8090 -> TB API:80/callback/telegram -> (handler)
    */

    @RequestMapping("/tg-proxy/**")
    @ResponseBody
    public String mirrorRest(@RequestBody Object body, HttpMethod method, HttpServletRequest request) throws URISyntaxException
    {
        String url = Arrays.stream(request.getRequestURI().split("/")).skip(2).collect(Collectors.joining("/"));
        System.out.println(url);
        URI uri = new URI("http", null, "localhost", 8090, "/".concat(url), request.getQueryString(), null);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(uri, method, new HttpEntity<Object>(body) , String.class);

        return responseEntity.getBody();
    }
}
