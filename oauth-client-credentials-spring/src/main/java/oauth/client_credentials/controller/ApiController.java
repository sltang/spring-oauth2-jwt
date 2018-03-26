package oauth.client_credentials.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Value("${resource.server.url}")
	private String resourceServerUrl;
	
	@Autowired
	private OAuth2RestTemplate oauthRestTemplate;
	
	@GetMapping(value = "/foo/{id}")
	public Map<String, Object> getMessage(@PathVariable int id) {
	    return oauthRestTemplate.getForObject(resourceServerUrl + "/api/foos/" + id, Map.class);
	}

}
