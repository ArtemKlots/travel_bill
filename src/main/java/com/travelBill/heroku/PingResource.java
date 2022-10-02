package com.travelBill.heroku;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class PingResource {

    @GetMapping()
    public String ping() {
        return "PONG";
    }

    @RequestMapping("/tg-proxy/**")
    @ResponseBody
    public String mirrorRest(@RequestBody Object body, HttpMethod method, HttpServletRequest request) throws URISyntaxException
    {
        String url = Arrays.stream(request.getRequestURI().split("/")).skip(2).collect(Collectors.joining("/"));
        System.out.println(url);
        URI uri = new URI("http", null, "localhost", 8090, "/".concat(url), request.getQueryString(), null);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(uri, method, new HttpEntity<Object>(body), String.class);

        return responseEntity.getBody();
    }
}
