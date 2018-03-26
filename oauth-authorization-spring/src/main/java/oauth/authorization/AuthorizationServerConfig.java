package oauth.authorization;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import oauth.authorization.security.SecretKeyProvider;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
    private DataSource dataSource;
	
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private SecretKeyProvider keyProvider;
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() throws CertificateException, IOException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(keyProvider.getKey());
        return converter;
    }
    
    @Bean
    public TokenStore tokenStore() throws CertificateException, IOException {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }
    
    @Bean
    public DefaultTokenServices tokenServices() throws CertificateException, IOException {
    	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    	tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        return tokenServices;
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .authenticationManager(authenticationManager)//need this for "password" grant type
            .tokenServices(tokenServices())
            .tokenStore(tokenStore());
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    	oauthServer
    		.tokenKeyAccess("permitAll()")
    		.checkTokenAccess("isAuthenticated()");
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	clients.jdbc(dataSource);
    }
    

}
