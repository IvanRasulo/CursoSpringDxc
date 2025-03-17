package com.banana.bananawhatsapp;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class BananaWhatsAppApplicationTest {
    @Test
    public void load() {
        DBUtil.reloadDB();
        assertTrue(true);
    }
}