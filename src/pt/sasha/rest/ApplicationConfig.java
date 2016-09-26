package pt.sasha.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import pt.sasha.rest.service.CsvService;
import pt.sasha.rest.service.UserService;

@ApplicationPath("/")
public class ApplicationConfig extends Application {
	private Set<Object> singletons = new HashSet<Object>();
    public ApplicationConfig() {
        singletons.add(new UserService());
        singletons.add(new CsvService());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}