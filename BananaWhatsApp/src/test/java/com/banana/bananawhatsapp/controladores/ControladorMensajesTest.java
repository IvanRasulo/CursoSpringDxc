package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.persistencia.IUsuarioJPARepository;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class ControladorMensajesTest {

    @Autowired
    ControladorMensajes controladorMensajes;

    @Autowired
    IUsuarioJPARepository repoUser;

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }
    @Test
    @Transactional
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() {
        Long remitente = 1L;
        Long destinatario = 2L;
        String texto = "Perfecto! Muchas gracias!";
        boolean sendMessage = controladorMensajes.enviarMensaje(remitente, destinatario, texto);
        assertTrue(sendMessage);
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() {
        Long remitente = 1L;
        Long destinatario = 2L;
        String texto = "SMS < 10";
        assertThrows(Exception.class, () -> {
            controladorMensajes.enviarMensaje(remitente, destinatario, texto);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() {
        Long remitente = 1L;
        Long destinatario = 2L;
        boolean mostrarChat = controladorMensajes.mostrarChat(remitente, destinatario);
        assertTrue(mostrarChat);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() {
        Long remitente = 1L;
        Long destinatario = - 2L;
        assertThrows(Exception.class, () -> {
            controladorMensajes.mostrarChat(remitente, destinatario);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() {
        Long remitente = 1L;
        Long destinatario = 2L;
        boolean eliminarChat = controladorMensajes.eliminarChatConUsuario(remitente, destinatario);
        assertTrue(eliminarChat);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() {
        Long remitente = -1L;
        Long destinatario = 5L;
        assertThrows(Exception.class, () -> {
            controladorMensajes.eliminarChatConUsuario(remitente, destinatario);
        });
    }
}