package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class ServicioUsuariosTest {

    @Autowired
    IServicioUsuarios servicio;

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrearUsuario_entoncesUsuarioValido() throws Exception {
        Usuario nuevo = new Usuario(null, "Ricardo", "r@r.com", LocalDate.now(), true, null, null);
        servicio.crearUsuario(nuevo);

        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId().intValue(), greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrearUsuario_entoncesExcepcion() {
        Usuario nuevo = new Usuario(null, "Ricardo", "r", LocalDate.now(), true, null, null);
        assertThrows(UsuarioException.class, () -> {
            servicio.crearUsuario(nuevo);
        });
    }

    @Test
    @Transactional
    void dadoUnUsuarioValido_cuandoBorrarUsuario_entoncesUsuarioValido() {
        Usuario user = new Usuario(2L, "Gema", "g@g.com", LocalDate.now(), true,null ,null);
        boolean userDelete = servicio.borrarUsuario(user);
        assertThat(userDelete, is(true));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarUsuario_entoncesExcepcion() {
        Usuario user = new Usuario(-1L, "John", "j@j.com", LocalDate.now(), false, null, null);
        assertThrows(UsuarioException.class, () -> {
            servicio.borrarUsuario(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizarUsuario_entoncesUsuarioValido() {
        Long iDUser = 1L;
        Usuario user = new Usuario(iDUser, "Juan", "j@j.com", LocalDate.now(), true, null, null);
        Usuario userUpdate = servicio.actualizarUsuario(user);
        assertThat(userUpdate.getEmail(), is("j@j.com"));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizarUsuario_entoncesExcepcion() {
        Usuario user = new Usuario(1L, "Juan", "j@j.com", LocalDate.now(), false, null, null);
        assertThrows(UsuarioException.class, () -> {
            servicio.actualizarUsuario(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDesinatarios_entoncesUsuariosValidos() {
        int numPosibles = 100;
        Usuario user = new Usuario(1L, "Juan", "j@j.com", LocalDate.now(), true, null, null);

        Set<Usuario> conjuntoDestinatarios = servicio.obtenerPosiblesDesinatarios(user, numPosibles);
        assertThat(conjuntoDestinatarios.size(), lessThanOrEqualTo(numPosibles));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDesinatarios_entoncesExcepcion() {
        Usuario user = new Usuario(-1L, null, null, null, true, null, null);
        int numPosibles = 100;
        assertThrows(UsuarioException.class, () -> {
            servicio.obtenerPosiblesDesinatarios(user, numPosibles);
        });
    }
}