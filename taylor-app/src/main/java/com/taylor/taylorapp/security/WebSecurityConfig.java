package com.taylor.taylorapp.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.taylor.taylorapp.bmi.BMICalculatorInMetric;
import com.taylor.taylorapp.dao.FabricDAO;
import com.taylor.taylorapp.dao.OrderDAO;
import com.taylor.taylorapp.entities.Notification;
import com.taylor.taylorapp.entities.Userprofile;
import com.taylor.taylorapp.model.StorePFInfo;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }


    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
            .authorizeRequests()
                .antMatchers("/","/static/**", "/styles/**", "/js/**", "/navbar","/plugins/**", "/resources/**", "/bootstrap-4.1.2/**", "/layouts/**", "/success", "/category", "/images/**", "/update-profile","/registration", "/console/**","/actuator").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(myAuthenticationSuccessHandler())
                .and()
            .logout()
                .permitAll();
                
       http.csrf().disable();
       http.headers().frameOptions().disable();
       
    
    }

    
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler successHandlers() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("/index");
        return successHandler;
    }


 

    
    

    
    @Bean
    public UserDetailsService userDetailsService() {
        return this.userDetailsService;
    }
    
    @Bean
    public Userprofile modelMapper() {
    	Userprofile modelMapper = new Userprofile();
       return modelMapper;
    }
    @Bean
    public OrderDAO order() {
    	OrderDAO modelorder = new OrderDAO();
       return modelorder;
    }
    @Bean
    public FabricDAO fabric() {
    	FabricDAO modelfabric = new FabricDAO();
       return modelfabric;
    }
    @Bean
    public StorePFInfo store() {
    	StorePFInfo store = new StorePFInfo();
       return store;
    }
    @Bean  
    public Notification notif() {
    	Notification Notify = new Notification();
       return Notify;
    }
    

    
    @Bean
    public BMICalculatorInMetric bmi() {
    	BMICalculatorInMetric modelfabric = new BMICalculatorInMetric();
       return modelfabric;
    }

    
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
    

    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
   



    /*
	@Bean
	public PasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	}
    */
}