package com.thewheel.sawatu.shared.config;

import com.thewheel.sawatu.database.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@PropertySource("classpath:tokenSignatures.properties")
@Slf4j
public class GeneralConfiguration {

    @Value("${spring.datasource.hikari.schema}")
    private String schema;

    private String dictionaryLanguage = "french";

    @Autowired
    private JdbcTemplate template;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<User> auditorAware() {
        return new AppAuditAware();
    }

    // We initiallize database we some datas if they're not yet set
    @Bean
    CommandLineRunner run() {
        return args -> {

            // Since we cannot set tsvector type with jpa without creating specific type
            // for jpa, we decided to use jdbc instead. Of cause, it's a bit silly to use both
            // data jpa and data jdbc, but hey, there's no other way
            try {
                template.execute(
                        "alter table if exists " + schema +
                        ".product add column if not exists text_search tsvector");
                template.execute(
                        "create index if not exists text_search__idx on " + schema +
                        ".product using gin(text_search)"
                                );
                template.execute(
                        "CREATE OR REPLACE FUNCTION product_search_trigger() " +
                        "RETURNS trigger " +
                        "LANGUAGE plpgsql AS" +
                        " $$ " +
                        "begin " +
                        "new.text_search := to_tsvector('" + dictionaryLanguage + "', coalesce(new.label, '')" +
                        " || ' ' || coalesce(new.description, '')" +
                        " || ' ' ||  coalesce(new.usage, '') || ' '" +
                        " || coalesce(new.characteristics, '') || ' '" +
                        " || coalesce(new.vendor_id, '')); " +
                        "return new; " +
                        "end " +
                        "$$"
                                );
                // Drop and create the trigger
                template.execute(
                        "DROP TRIGGER if exists search_text_trigger ON " + schema + ".product; " +
                        "CREATE TRIGGER search_text_trigger BEFORE INSERT OR UPDATE " +
                        " ON test.product FOR EACH ROW EXECUTE PROCEDURE product_search_trigger();"
                                );
            } catch (Exception exception) {
                log.info(exception.toString());
            }
        };
    }

}
