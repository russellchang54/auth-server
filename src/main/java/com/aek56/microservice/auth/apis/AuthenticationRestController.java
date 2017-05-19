package com.aek56.microservice.auth.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aek56.microservice.auth.model.security.AuthUser;
import com.aek56.microservice.auth.security.JwtAuthenticationRequest;
import com.aek56.microservice.auth.security.JwtTokenUtil;
import com.aek56.microservice.auth.security.JwtUser;
import com.aek56.microservice.auth.security.service.JwtAuthenticationResponse;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /*@Autowired
    private UserDetailsService userDetailsService;*/

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public Map<String, Object> createAuthenticationToken(  @RequestBody JwtAuthenticationRequest authenticationRequest, Device device) {
    	String deviceId = authenticationRequest.getDeviceId(); 
    	
    	if( deviceId == null || deviceId.length() < 10){//终端编号不能小于10
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("timestamp", new Date());
    		map.put("status", 401);
    		map.put("message", "deviceId 不能为空，且长度须大于10");
    		return map;
    	}
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        

        // Reload password post-security so we can generate token
     //   final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
     //   final String token = jwtTokenUtil.generateToken(userDetails, device);
     //   final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         final AuthUser userDetails = (AuthUser)authentication.getPrincipal();
         final String token = jwtTokenUtil.generateToken(userDetails,device);
        

        // Return the token
        //return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", token);
        tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
        tokenMap.put("token_type", "Bearer");
        return tokenMap;
    }

/*    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }*/

}
