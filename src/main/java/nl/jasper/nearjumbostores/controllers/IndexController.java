package nl.jasper.nearjumbostores.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Hidden;

import java.net.URI;

@Controller
public class IndexController {

    @Get("/")
    @Hidden
    public HttpResponse<?> index() {
        return HttpResponse.redirect(URI.create("/swagger-ui"));
    }

}
