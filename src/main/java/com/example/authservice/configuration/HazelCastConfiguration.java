package com.example.authservice.configuration;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelCastConfiguration {

    @Bean
    public Config hazelCastConfig() {
        return new Config().setInstanceName("token-instance")
                .addMapConfig(new MapConfig().setName("lineNumbers")
                        .setAsyncBackupCount(1)
                        .setEvictionConfig(new EvictionConfig().
                                setEvictionPolicy(EvictionPolicy.LFU)
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE))
                        .setTimeToLiveSeconds(2000)
                );
    }
}
