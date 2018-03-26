package oauth.resource.controller;

import java.util.concurrent.ThreadLocalRandom;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oauth.resource.model.Foo;

@RestController
@RequestMapping("/api")
public class FooController {
	
	final static int RANDOM_MAX = 1000;

    @PreAuthorize("#oauth2.hasScope('foo') and #oauth2.hasScope('read')")
    @GetMapping(path = "/foos/{id}")
    public Foo findById(@PathVariable final long id) {
    	long nextId = ThreadLocalRandom.current().nextLong(1, RANDOM_MAX);
		byte[] secret = new byte[4];
		new SecureRandom().nextBytes(secret);
    	String name = new String(Base64.getEncoder().encode(secret));
        return new Foo(nextId, name);
    }

}
