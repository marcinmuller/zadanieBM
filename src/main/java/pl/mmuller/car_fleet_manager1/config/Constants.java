package pl.mmuller.car_fleet_manager1.config;

import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.crypto.SecretKey;


public class Constants {
    public static final SecretKey SECRET = MacProvider.generateKey();
    public static final long EXPIRATION_TIME = 1200000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";
}
