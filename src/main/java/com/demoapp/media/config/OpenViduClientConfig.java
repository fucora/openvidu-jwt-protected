package com.demoapp.media.config;

import io.openvidu.java.client.OpenVidu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure OpenVidu client.
 *
 * @author Anton Pelykh
 */
@Configuration
public class OpenViduClientConfig {

    @Value("${application.openVidu.serverUrl}")
    private String openViduServerUrl;
    @Value("${application.openVidu.secret}")
    private String openViduSecret;

    @Bean
    public OpenVidu openVidu() {
        return new OpenVidu(this.openViduServerUrl, this.openViduSecret);
    }
}
