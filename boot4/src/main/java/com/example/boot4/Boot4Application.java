package com.example.boot4;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.util.Locale;
import java.util.Map;

@Import(LocaleConfiguration.class)
@SpringBootApplication
public class Boot4Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot4Application.class, args);
    }

    @Bean
    ApplicationRunner runner(Map<String, LocaleAwareCart> carts) {
        return args ->
                carts.forEach((k, cart) -> System.out.println(k + " : " + cart));
    }

}


class LocaleConfiguration implements BeanRegistrar {

    @Override
    public void register(BeanRegistry registry, Environment env) {
        if (env.getActiveProfiles().length == 0) {
            // ...
        }
        Locale.availableLocales().forEach(locale -> {
            registry.registerBean(LocaleAwareCart.class, spec -> spec
                    .description("LocaleAwareCart for " + locale)
                    .supplier((context) -> new LocaleAwareCart(locale))
            );
        });

    }
}

class LocaleAwareCart {

    private final Locale locale;

    LocaleAwareCart(Locale locale) {
        this.locale = locale;
    }

}

