package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class MensajeRepositoryTest {

    @Autowired
    IUsuarioJPARepository repoUsuario;

    @Autowired
    IMensajeJPARepository repoMensaje;

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }

    @Test
    @Order(1)
    @Transactional
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        Usuario destinatario = repoUsuario.findById(2L).orElseThrow(() -> new Exception("Usuario no encontrado"));

        Mensaje message = new Mensaje(null, remitente, destinatario, "De acuerdo Juana. Un saludo.", LocalDate.now());

        repoMensaje.save(message);
        assertThat(message, notNullValue());
    }

    @Test
    @Order(2)
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() throws Exception {
        Usuario remitente = new Usuario(1L, null, null, null, true, null, null);
        Usuario destinatario = new Usuario(2L, null, null, null, true, null, null);
        Mensaje message = new Mensaje(null, destinatario, remitente, "SMS < 10", LocalDate.now());
        assertThrows(Exception.class, () -> {
            repoMensaje.save(message);
        });
    }

    @Test
    @Order(3)
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajesEnviados() throws Exception {
        Usuario user = repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));

        Usuario userEncontrado = repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        assertNotNull(userEncontrado.getMensajesEnviados());
    }

    @Test
    @Order(4)
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() throws Exception {
        assertThrows(UsuarioException.class, () -> {
               repoUsuario.findById(1L).orElseThrow(() -> new UsuarioException("Usuario no encontrado"));
        });
    }

    @Test
    @Order(5)
    @Transactional
    void dadoUnRemitenteValido_cuandoBorrarEntre_entoncesOK() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        Usuario destinatario = repoUsuario.findById(2L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        assertTrue(repoMensaje.existsByRemitenteAndDestinatario(remitente, destinatario), "Debe haber mensajes entre los usuarios antes de borrar.");

        repoMensaje.deleteByRemitenteAndDestinatario(remitente, destinatario);

        assertFalse(repoMensaje.existsByRemitenteAndDestinatario(remitente, destinatario), "Los mensajes entre los usuarios no fueron eliminados correctamente.");

    }

    @Test
    @Order(6)
    void dadoUnRemitenteNOValido_cuandoBorrarEntre_entoncesExcepcion() throws Exception {
    Usuario remitente = repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
    remitente.setActivo(false);
    repoUsuario.save(remitente);
    Usuario destinatario = repoUsuario.findById(2L).orElseThrow(() -> new Exception("Usuario no encontrado"));
    assertThrows(UsuarioException.class, () -> {
        repoMensaje.deleteByRemitenteAndDestinatario(remitente, destinatario);
    });
    }

    @Test
    @Order(7)
    @Transactional
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() throws Exception {
    Usuario user = repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
    assertTrue(repoMensaje.existsByRemitenteOrDestinatario(user, user), "El usuario debe tener mensajes antes de borrarlos.");
    repoMensaje.borrarTodos(user);
    assertFalse(repoMensaje.existsByRemitenteOrDestinatario(user, user), "Los mensajes del usuario no fueron eliminados.");
    }

    @Test
    @Order(8)
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() throws Exception {
        Usuario user = new Usuario(-1L, null, null, null, true, null, null);
        assertThrows(UsuarioException.class, () -> {
            repoMensaje.borrarTodos(user);
        });
    }

}