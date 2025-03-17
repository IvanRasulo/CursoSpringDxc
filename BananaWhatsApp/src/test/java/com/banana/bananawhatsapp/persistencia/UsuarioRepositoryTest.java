package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class IUsuarioJPARepositoryTest {

    @Autowired
    IUsuarioJPARepository repoUsuario;

    @Autowired
    IMensajeJPARepository repoMensaje;

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();  // Método para limpiar y recargar datos de la base de datos entre pruebas.
    }

    @Test
    @Order(1)
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() {
        Usuario nuevo = new Usuario(null, "Ricardo", "r@r.com", LocalDate.now(), true, null, null);
        Usuario usuarioGuardado = repoUsuario.save(nuevo);

        assertThat(usuarioGuardado, notNullValue());
        assertThat(usuarioGuardado.getId().intValue(), greaterThan(0));
    }

    @Test
    @Order(2)
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() {
        Usuario nuevo = new Usuario(null, "Ricardo", "r", LocalDate.now(), true, null, null);
        assertThrows(Exception.class, () -> repoUsuario.save(nuevo));
    }

    @Test
    @Order(3)
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
        Long idUsuario = 1L;
        Usuario user = new Usuario(idUsuario, "Juan", "j@j.com", LocalDate.now(), true, null, null);
        Usuario usuarioActualizado = repoUsuario.save(user);

        assertThat(usuarioActualizado.getNombre(), is("Juan"));
    }

    @Test
    @Order(4)
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        Long idUsuarioInvalido = -1L;
        Usuario user = new Usuario(idUsuarioInvalido, "Juan", "j@j.com", LocalDate.now(), true, null, null);

        assertThrows(Exception.class, () -> repoUsuario.save(user));
    }

    @Test
    @Order(5)
    @Transactional
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() {
        Usuario user = new Usuario(1L, null, null, null, true, null, null);
        repoMensaje.deleteByRemitente(user);
        repoMensaje.deleteByDestinatario(user);
        repoUsuario.delete(user);

        Usuario usuarioBorrado = repoUsuario.findById(user.getId()).orElse(null);

        assertNull(usuarioBorrado, "El usuario debería haber sido eliminado.");
    }

    @Test
    @Order(6)
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() {
        Usuario user = new Usuario(-1L, null, null, null, true, null, null);

        assertThrows(Exception.class, () -> repoUsuario.delete(user));
    }
}