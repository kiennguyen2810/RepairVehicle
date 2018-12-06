package com.nuce.repairvehiclemap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nuce.repairvehiclemap.rest.CustomAccessDeniedHandler;
import com.nuce.repairvehiclemap.rest.JwtAuthenticationTokenFilter;
import com.nuce.repairvehiclemap.rest.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	protected void configure(HttpSecurity http) throws Exception {
//		// Disable crsf cho đường dẫn /rest/**
//		http.csrf().ignoringAntMatchers("/**");
		http.csrf().disable();

//		http.authorizeRequests().antMatchers("/account/login**").permitAll();
//		http.authorizeRequests().antMatchers("/admin/index**").permitAll();
//		http.authorizeRequests().antMatchers("/admin/login**").permitAll();

//		http.antMatcher("/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
//				.antMatchers("/account/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//				.antMatchers("/historyrepair/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//				.antMatchers("/service/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//				.antMatchers("/shop/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')").and()
//				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").and()
//				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
		
//		// Cấu hình cho Login Form.
//		http.authorizeRequests().and().formLogin()//
//				.loginProcessingUrl("/admin/j_spring_security_login")//
//				.loginPage("/admin/login")//
//				.defaultSuccessUrl("admin/index")//
//				.failureUrl("/admin/login?message=error")//
//				.usernameParameter("username")//
//				.passwordParameter("password")
//				// Cấu hình cho Logout Page.
//				.and().logout().logoutUrl("/admin/j_spring_security_logout").logoutSuccessUrl("/login?message=logout");
	}
}
