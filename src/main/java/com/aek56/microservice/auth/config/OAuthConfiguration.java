package com.aek56.microservice.auth.config;

import java.security.KeyPair;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;


@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {
/**/
	@Autowired
	private AuthenticationManager auth;

	//@Autowired
//	private DataSource dataSource;

//	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	/*@Bean
	public JdbcTokenStore tokenStore_jdbc() {
		return new JdbcTokenStore(dataSource);
	}*/

	@Bean 
	public JwtTokenStore  tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("k09BQnaF");
	/*	KeyPair keyPair = new KeyStoreKeyFactory(
				new ClassPathResource("keystore.jks"), "foobar".toCharArray())
				.getKeyPair("test");
		converter.setKeyPair(keyPair);*/
		return converter;
	}

/*	@Bean
	protected AuthorizationCodeServices authorizationCodeServices() {//验证码认证
		return new JdbcAuthorizationCodeServices(dataSource);
	}*/

/*	@Override
	public void configure(AuthorizationServerSecurityConfigurer security)
			throws Exception {
		security.passwordEncoder(passwordEncoder);
		
	}*/

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		//endpoints.authorizationCodeServices(authorizationCodeServices())
		endpoints.authenticationManager(auth).tokenStore(tokenStore())
				.approvalStoreDisabled();
	}


	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {
	
		/*clients.jdbc(dataSource)
				.passwordEncoder(passwordEncoder)
				.withClient("aek56.com")
				.authorizedGrantTypes("authorization_code", "client_credentials", 
						"refresh_token","password", "implicit")
				.authorities("ROLE_CLIENT")
				//.resourceIds("apis")
				//.scopes("read")
				.secret("zj@aek56.com")
				.accessTokenValiditySeconds(3000);*/
		clients.inMemory()
		       .withClient("aek56.com")
		       .secret("russellchang")
		       .authorizedGrantTypes("authorization_code", "client_credentials", 
						"refresh_token","password", "implicit");
		      
	
	}

/*	@Configuration
	@Order(Ordered.LOWEST_PRECEDENCE - 20)
	protected static class AuthenticationManagerConfiguration extends
			GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private DataSource dataSource;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off
			auth.jdbcAuthentication().dataSource(dataSource).withUser("dave")
					.password("secret").roles("USER");
			auth.jdbcAuthentication().dataSource(dataSource).withUser("ebey")
					.password("ebey").roles("ADMIN");
                        auth.jdbcAuthentication().dataSource(dataSource).withUser("russell").password("aek56").roles("ADMIN");
			// @formatter:on
		}
	}*/
	
}
