package oauth.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2Controller {
	
	private final String authorizationRequestBaseUri = "oauth2/authorization";
	private Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
	
    private ClientRegistrationRepository clientRegistrationRepository;
	
	public OAuth2Controller(ClientRegistrationRepository crr) {
		this.clientRegistrationRepository = crr;
		Iterable<ClientRegistration> clientRegistrations = null;
	    ResolvableType type = ResolvableType.forInstance(this.clientRegistrationRepository).as(Iterable.class);
	    if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
	        clientRegistrations = (Iterable<ClientRegistration>) this.clientRegistrationRepository;
	    }	 
	    clientRegistrations.forEach(registration -> 
	      oauth2AuthenticationUrls.put(registration.getClientName(), 
	      authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
	}

	@GetMapping("/")
	public String getLoginPage(Model model) {
		model.addAttribute("urls", oauth2AuthenticationUrls);	 
		return "oauth-login";
	}
	
	@GetMapping("/index") 
	public String welcome(Model model, OAuth2AuthenticationToken authentication) {
		model.addAttribute("attributes", authentication.getPrincipal().getAttributes());
		return "index";
	}

}
