package com.flowlogix.shirodemo;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroBeanAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroNoRealmConfiguredFailureAnalyzer;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebFilterConfiguration;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebMvcAutoConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@ImportAutoConfiguration({ShiroWebAutoConfiguration.class, ShiroWebFilterConfiguration.class, ShiroWebMvcAutoConfiguration.class,
		ShiroBeanAutoConfiguration.class, ShiroAutoConfiguration.class, ShiroAnnotationProcessorAutoConfiguration.class,
		ShiroNoRealmConfiguredFailureAnalyzer.class})
@SpringBootApplication
public class ShiroDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShiroDemoApplication.class, args);
	}

	@Bean
	public Realm textRealm() {
		TextConfigurationRealm realm = new TextConfigurationRealm();
		realm.setUserDefinitions("joe.coder=password,user\n" +
				"jill.coder=password,admin");

		realm.setRoleDefinitions("admin=read,write\n" +
				"user=read");
		realm.setCachingEnabled(true);
		return realm;
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

		// logged in users with the 'admin' role
		chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

		// logged in users with the 'document:read' permission
		chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

		chainDefinition.addPathDefinition("/error", "anon");

		// all other paths require a logged in user
		chainDefinition.addPathDefinition("/**", "authc");
		return chainDefinition;
	}
}
