package oauth.resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.DelegatingJwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.IssuerClaimVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import oauth.resource.security.SecretKeyProvider;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Value("${oauth.server.url}")
	private String oauthServerUrl;
	
    @Autowired
    private SecretKeyProvider keyProvider;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .anyRequest()
            .permitAll();
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) throws CertificateException, IOException {
        config.tokenServices(tokenServices());
        config.resourceId("foo-resource");
    }

    @Bean
    public TokenStore tokenStore() throws CertificateException, IOException {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() throws CertificateException, IOException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(keyProvider.getKey());
        converter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());
        return converter;
    }

    @Bean
    public JwtClaimsSetVerifier jwtClaimsSetVerifier() throws MalformedURLException {
        return new DelegatingJwtClaimsSetVerifier(Arrays.asList(issuerClaimVerifier(), customJwtClaimVerifier()));
    }

    @Bean
    public JwtClaimsSetVerifier issuerClaimVerifier() throws MalformedURLException {
    	return new IssuerClaimVerifier(new URL(oauthServerUrl));
    }

    @Bean
    public JwtClaimsSetVerifier customJwtClaimVerifier() {
        return new CustomClaimVerifier();
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() throws CertificateException, IOException {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
    
}
