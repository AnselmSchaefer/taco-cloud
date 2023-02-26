package tacos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.security.User;
import tacos.security.UserRepository;


@Configuration
public class SecurityConfig {

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			User user = userRepo.findByUsername(username);
			if(user != null) return user;
			
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeRequests()
				.antMatchers("/design", "/orders").hasRole("USER")
				.anyRequest().permitAll()
			.and()
				.formLogin()
					.loginPage("/login")
			// Make H2-Console non-secured; for debug purposes
			.and()
	        	.csrf()
	        		.ignoringAntMatchers("/h2-console/**")
	        // Allow pages to be loaded in frames from the same origin; needed for H2-Console
	        .and()  
	        .headers()
	          .frameOptions()
	            .sameOrigin()
	            
	       .and()
	       .build();
	}
}
