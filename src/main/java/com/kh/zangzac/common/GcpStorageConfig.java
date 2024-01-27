package com.kh.zangzac.common;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class GcpStorageConfig {

    @Bean
    public Storage storage() throws IOException {
    	ClassPathResource resource = new ClassPathResource("zangzac-3cd950393504.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
				String projectId = "zangzac";
        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}