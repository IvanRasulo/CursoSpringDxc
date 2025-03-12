package com.myshoppingcart.config;


import com.myshoppingcart.persistence.*;
import com.myshoppingcart.service.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class ReposConfig {


    @Bean
    public IProductoRepository getProductRepositoryBean(){
        System.out.println("Creando ProductRepository...");
        return new ProductoDBRepository();
    }

    @Bean
    public IUsuarioRepository getUserRepositoryBean(){
        System.out.println("Creando UserRepository...");
        return new UsuarioDBRepository();
    }

    @Bean
    public ICompraRepository getCompraRepositoryBean(){
        System.out.println("Creando CompraRepository...");
        return new CompraDBRepository();
    }

}
