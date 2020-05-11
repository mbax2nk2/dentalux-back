//package kz.dentalux.webapp.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//@Configuration
//@EnableAuthorizationServer
//
//public class OAuthConfig extends AuthorizationServerConfigurerAdapter {
//
//    private final AuthenticationManager authenticationManager;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final UserDetailsService userService;
//
//    @Value("${jwt.clientId:dentalux}")
//    private String clientId;
//
//    @Value("${jwt.client-secret:dentaluxSecret}")
//    private String clientSecret;
//
//    @Value("${jwt.signing-key:123}")
//    private String jwtSigningKey;
//
//    @Value("${jwt.accessTokenValidititySeconds:43200}") // 12 hours
//    private int accessTokenValiditySeconds;
//
//    @Value("${jwt.authorizedGrantTypes:password,authorization_code,refresh_token}")
//    private String[] authorizedGrantTypes;
//
//    @Value("${jwt.refreshTokenValiditySeconds:2592000}") // 30 days
//    private int refreshTokenValiditySeconds;
//
//    public OAuthConfig(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
//        UserDetailsService userService) {
//        this.authenticationManager = authenticationManager;
//        this.passwordEncoder = passwordEncoder;
//        this.userService = userService;
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//            .withClient(clientId)
//            .secret(passwordEncoder.encode(clientSecret))
//            .accessTokenValiditySeconds(accessTokenValiditySeconds)
//            .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
//            .authorizedGrantTypes(authorizedGrantTypes)
//            .redirectUris("http://localhost")
//            .scopes("read", "write")
//            .resourceIds("api");
//    }
//
//    @Override
//    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints
//            .accessTokenConverter(accessTokenConverter())
//            .userDetailsService(userService)
//            .authenticationManager(authenticationManager)
//            .tokenStore(tokenStore())
//        ;
//    }
//
//    @Bean
//    JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(jwtSigningKey);
//        return converter;
//    }
//
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//}
