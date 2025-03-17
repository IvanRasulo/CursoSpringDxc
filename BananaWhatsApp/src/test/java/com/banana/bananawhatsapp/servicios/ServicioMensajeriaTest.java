package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class ServicioMensajeriaTest {
    @Autowired
    IUsuarioJPARepository repoUsuario;
    @Autowired
    IServicioMensajeria servicio;

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }

    @Test
    @Transactional
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() throws Exception {
        Usuario remitente =  repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        Usuario destinatario =  repoUsuario.findById(2L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        String texto = "Felices Fiestas!";
        Mensaje message = servicio.enviarMensaje(remitente, destinatario, texto);
        assertThat(message.getId().intValue(), greaterThan(0));
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() throws Exception {
        Usuario remitente =  repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        Usuario destinatario =  repoUsuario.findById(2L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        String texto = "SMS < 10";
        assertThrows(Exception.class, () -> {
            servicio.enviarMensaje(remitente, destinatario, texto);
        });
    }


    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() throws Exception {
        Usuario remitente =  repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        Usuario destinatario =  repoUsuario.findById(2L).orElseThrow(() -> new Exception("Usuario no encontrado"));

        List<Mensaje> userMessages = servicio.mostrarChatConUsuario(remitente, destinatario);
        assertNotNull(userMessages);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() throws Exception {
        Usuario remitente =  repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        Usuario destinatario = new Usuario(2L, null, null, null, false, null, null);
        assertThrows(Exception.class, () -> {
            List<Mensaje> userMessages = servicio.mostrarChatConUsuario(remitente, destinatario);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() throws Exception {
        Usuario remitente =  repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        Usuario destinatario =  repoUsuario.findById(2L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        boolean borrarChat = servicio.borrarChatConUsuario(remitente, destinatario);
        assertTrue(borrarChat);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        Usuario destinatario = new Usuario(2L, null, null, null, false, null, null);
        assertThrows(Exception.class, () -> {
            boolean borrarChat = servicio.borrarChatConUsuario(remitente, destinatario);
        });
    }
}