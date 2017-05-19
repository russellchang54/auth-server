package com.aek56.microservice.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.aek56.microservice.auth.model.security.AuthUser;
import com.aek56.microservice.auth.redis.DeviceRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.aek56.microservice.auth.redis.RedisRepository;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "aud";
    static final String CLAIM_KEY_CREATED = "created";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";
    
    /**
     * Token 类型
     */
    public static final String TOKEN_TYPE_BEARER = "Bearer";
    /**
     * 权限缓存前缀
     */
    private static final String REDIS_PREFIX_AUTH = "auth:";
    /**
     * 用户信息缓存前缀
     */
    private static final String REDIS_PREFIX_USER = "user-details:";
    
    /**
     * redis repository
     */
    @Autowired
    private RedisRepository redisRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    
    /**
     * 获取设备
     *
     * @param token Token
     * @return String
     */
    public String getDeviceIdFromToken(String token)
    {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getAudience() : null;
    }
    
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);        
        
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails, Device device) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
        claims.put(CLAIM_KEY_CREATED, new Date());
        
        String token =  Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        
        String key = REDIS_PREFIX_AUTH + userDetails.getUsername();
        redisRepository.setExpire(key, token, expiration);
        putUserDetails(userDetails);
        return token;
        //return generateToken(claims);
    	
    	
    }
    
    /**
     * 生成 Token
     *
     * @param userDetails 用户信息
     * @return String
     */
    public String generateToken(AuthUser userDetails, Device device)
    {
        String token = Jwts.builder()
                .setSubject(userDetails.getMobile()) //统一转换手机号
                .setAudience(generateAudience(device) + ":" + userDetails.getDeviceId())
                .setIssuer("aek56")
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        
        String key = userDetails.getDeviceId() +":"+REDIS_PREFIX_AUTH + userDetails.getUsername();

        DeviceRedis deviceRedis = new DeviceRedis();
        deviceRedis.setInTanentId(userDetails.getTenantId());//所属租户
        deviceRedis.setTanentId(userDetails.getTenantId());//当前租户       
        deviceRedis.setToken(token);
        /*
         * key: <deviceId>:auth:<手机号>
         * value: <所属租户id>,<当前租户id>, <可用租户列表>,<token>
         * */
        redisRepository.setExpire(key, new Gson().toJson(deviceRedis), expiration);
        userDetails.setPassword(null);
        putUserDetails(userDetails, userDetails.getTenantId());
        return token;
    }
    
    
    /**
     * 验证 Token
     *
     * @param token Token
     * @return Boolean
     */
    public Boolean validateToken(String token) {
    	if (token.isEmpty() || token.contains(":")){
    		return false;
    	}
        final String username = getUsernameFromToken(token);
        final String deviceid = getAudienceFromToken(token).split(":")[1];
        String key = deviceid + ":" + REDIS_PREFIX_AUTH + username;
        String redisToken = redisRepository.get(key);
       // return StringHelper.isNotEmpty(token) && !isTokenExpired(token) && token.equals(redisToken);
        return  !isTokenExpired(token) && token.equals(redisToken);

    }

    /**
     * 移除 Token
     *
     * @param token Token
     */
    public void removeToken(String token) {
        final String username = getUsernameFromToken(token);
        final String deviceid = getAudienceFromToken(token).split(":")[1];       
        String key = deviceid + ":" + REDIS_PREFIX_AUTH + username;
        DeviceRedis device = new Gson().fromJson(redisRepository.get(key), DeviceRedis.class);
        redisRepository.del(key);
        
        //读取当前租户的ID
        delUserDetails(username,device.getTanentId());
        
    }

    /**
     * 获得用户信息 Json 字符串
     *
     * @param token Token
     * @return String
     */
    protected String getUserDetailsString(String token) {
        final String username = getUsernameFromToken(token);
        final String deviceid = getAudienceFromToken(token).split(":")[1];
        String key = deviceid + ":" + REDIS_PREFIX_USER + username;
        return redisRepository.get(key);
    }

    /**
     * 获得用户信息
     *
     * @param token Token
     * @return UserDetails
     */
  //  public abstract UserDetails getUserDetails(String token);

    /**
     * 存储用户信息
     *
     * @param userDetails 用户信息
     */
    private void putUserDetails(UserDetails userDetails) {
        String key = REDIS_PREFIX_USER + userDetails.getUsername();
        redisRepository.setExpire(key, new Gson().toJson(userDetails), expiration);
    }    

    /**
     * 存储用户信息
     *
     * @param userDetails 用户信息
     */
    private void putUserDetails(AuthUser userDetails, Long tenantId)
    {
        String key = tenantId + ":" +REDIS_PREFIX_USER  + userDetails.getMobile();
        
        redisRepository.setExpire(key, new Gson().toJson(userDetails), expiration);
    }



    /**
     * 删除用户信息
     *
     * @param username 用户名
     */
    private void delUserDetails(String username,Long tenantId) {
        String key = tenantId + ":" + REDIS_PREFIX_USER + username;
        redisRepository.del(key);
    }


    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

/*    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }*/

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }
    
    public Long getExpiration() {
        return expiration;
    }
    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}