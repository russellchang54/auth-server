package com.aek56.microservice.auth.apis;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aek56.microservice.auth.entity.SysUser;
import com.aek56.microservice.auth.model.security.AuthUser;
import com.aek56.microservice.auth.model.security.AuthUserFactory;
import com.aek56.microservice.auth.security.JwtAuthenticationRequest;
import com.aek56.microservice.auth.security.JwtTokenUtil;
import com.aek56.microservice.auth.security.service.SystemService;
import com.google.gson.Gson;

@RestController
public class AuthController {
	private static final Log logger = LogFactory.getLog(AuthController.class);
	
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public Map<String, Object> createAuthenticationToken(@RequestBody JwtAuthenticationRequest request, Device device, HttpServletResponse response) {
    	logger.debug(device);
    	String deviceId = request.getDeviceId(); 

    	if( deviceId == null || deviceId.length() < 10){//终端编号不能小于10
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("timestamp", new Date());
    		map.put("status", 401);
    		map.put("message", "deviceId 不能为空，且长度须大于10位");
    		return map;
    	}
    	// Perform the security
    	final Authentication authentication = authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(
    					request.getUsername(),
    					request.getPassword()
    					)
    			);
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	AuthUser userDetails = (AuthUser)authentication.getPrincipal();
    	userDetails.setDeviceId(deviceId);
    	final String token = jwtTokenUtil.generateToken(userDetails,device);
    	Map<String, Object> tokenMap = new HashMap<>();
    	tokenMap.put("access_token", token);
    	tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
    	tokenMap.put("token_type", "Bearer");
    	response.setHeader(tokenHeader, token);
    	return tokenMap;
    }

/*    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(refreshedToken);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }*/

    @PostMapping("/perms/{tenantId}")
    public AuthUser getPerms(@PathVariable Long tenantId){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Gson gson = new Gson();
    	AuthUser userDetail = (AuthUser)authentication.getPrincipal();
    	logger.debug(gson.toJson(userDetail));
    	SysUser user=this.systemService.getUserById(userDetail.getId());
		systemService.getPerms(user, tenantId);
    	return AuthUserFactory.create(user);
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProtectedGreeting() {
        return ResponseEntity.ok("只有admin可以访问的资源");
    }

    @RequestMapping("/me")
	public Principal getCurrentLoggedInUser(Principal user) {
		return user;
	}
}
