package com.thewheel.sawatu.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Slf4j
public class Application {

    @Autowired
    private JdbcTemplate template;

    private String dictionaryLanguage = "french";

    @Bean
    CommandLineRunner run() {
        return args -> {

            // Since we cannot set tsvector type with jpa without creating specific type
            // for jpa, we decided to use jdbc instead. Of cause, it's a bit silly to use both
            // data jpa and data jdbc, but hey, there's no other way
            try {
                template.execute(
                        "alter table if exists " + "product add column if not exists text_search tsvector"
                                );
                template.execute(
                        "create index if not exists text_search__idx on " + "product using gin(text_search)"
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
                template.execute("DROP TRIGGER if exists search_text_trigger ON " + "product; " +
                                 "CREATE TRIGGER search_text_trigger BEFORE INSERT OR UPDATE " +
                                 " ON test.product FOR EACH ROW EXECUTE PROCEDURE product_search_trigger();"
                                );
            } catch (Exception exception) {
                log.error(exception.toString());
            }
        };
    }

}
