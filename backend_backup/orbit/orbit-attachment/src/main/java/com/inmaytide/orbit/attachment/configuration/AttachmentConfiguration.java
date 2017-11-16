package com.inmaytide.orbit.attachment.configuration;

import com.inmaytide.orbit.attachment.handler.AttachmentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Moss
 * @since October 26, 2017
 */
@Configuration
public class AttachmentConfiguration {


    @Bean
    public AttachmentHandler attachmentHandler() {
        return new AttachmentHandler();
    }


    public static void main(String... args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("java.io.tmpdir"));
    }




}
