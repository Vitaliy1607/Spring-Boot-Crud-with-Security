package user_base.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter

@ConfigurationProperties(prefix = "app.security")
@Configuration("appProperties")
public class AppProperties {
        private String key;
        private Long tokenValidity;
        private String tokenPrefix;
        private String headerString;
        private String authoritiesKey;

}
