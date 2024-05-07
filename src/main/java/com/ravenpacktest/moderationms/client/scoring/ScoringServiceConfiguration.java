package com.ravenpacktest.moderationms.client.scoring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class ScoringServiceConfiguration {

    @Bean
    public Random random() {
        return new Random();
    }

}
