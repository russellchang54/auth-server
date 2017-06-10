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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import org.springframework.stereotype.Service;

import com.aek56.microservice.auth.entity.SysUser;
import com.aek56.microservice.auth.model.security.AuthUserFactory;
import com.aek56.microservice.auth.security.service.SystemService;


@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {
/**/
	@Autowired
	private AuthenticationManager auth;

	//@Autowired
	private DataSource dataSource;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	/*@Bean
	public JdbcTokenStore tokenStore_jdbc() {
		return new JdbcTokenStore(dataSource);
	}*/
    @Autowired
    private UserDetailsService userDetailsService;
    
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

	@Bean
	protected AuthorizationCodeServices authorizationCodeServices() {//验证码认证
		return new JdbcAuthorizationCodeServices(dataSource);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security)
			throws Exception {
		security.passwordEncoder(passwordEncoder)//加密client secret
		        .tokenKeyAccess("permitAll()"); //开发接口/oauth/token_key。 client 可以获取公钥，以做验签
		
		
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)//配置Authorization Server 的token授予方式(Grant Types)
			throws Exception {
		endpoints.authenticationManager(auth)//密码授予token
		         .authorizationCodeServices(authorizationCodeServices())//授权码授予token
		         .userDetailsService(userDetailsService) //通过refresh token授予的token时，检测userdetails,确保账户是active
		         .tokenStore(tokenStore())
		         .pathMapping("/oauth/token", "/auth/token")
				.approvalStoreDisabled();
	}

/*几个endpoint: 默认都是protected
 * /oauth/authorize 授权endpoint
 * /oauth/token 签发token endpoint。如果被保护了，必须提供 client secret 才能访问此token
 * /oauth/confirm_access 
 * /oauth/error
 * /oauth/check_token 资源服务器 decode access token
 * /oauth/token_key
 * (non-Javadoc)
 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
 */
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
	

	@Configuration
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
		
	}
	
	@Service
	public class JwtUserDetailsServiceImpl implements UserDetailsService {

		/**
		 * 系统服务
		 */
		@Autowired
		private SystemService systemService;

		@Override
		public UserDetails loadUserByUsername(String loginName) {
			SysUser user = systemService.getUserByLoginName(loginName);

			if (user == null) {
				throw new UsernameNotFoundException(String.format("No user found with username '%s'.", loginName));
			}

			Long tenantId = user.getTenantId();
			systemService.getPerms(user, tenantId);
			return AuthUserFactory.create(user);
		}
	}
	
}
