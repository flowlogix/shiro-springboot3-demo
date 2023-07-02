package com.flowlogix.shirodemo;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
