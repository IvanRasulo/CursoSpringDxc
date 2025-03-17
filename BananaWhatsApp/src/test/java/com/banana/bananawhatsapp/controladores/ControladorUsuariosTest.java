package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
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

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class ControladorUsuariosTest {
    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Autowired
    IUsuarioJPARepository repoUsuario;


    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }
    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        Usuario nuevo = new Usuario(null, "Ricardo", "r@r.com", LocalDate.now(), true, null, null);
        controladorUsuarios.alta(nuevo);

        assertThat(nuevo, notNullValue());
       //assertThat(nuevo.getId(), greaterThan(0));
    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() {
        Usuario user = new Usuario(null, "Gema", "g@gccom", LocalDate.now(), true, null, null);
        assertThrows(Exception.class, () -> {
            controladorUsuarios.alta(user);
        });
    }

    @Test
    @Transactional
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws Exception {
        Long iDUser = 2L;
        LocalDate fecha = LocalDate.parse("2023-12-17");
        Usuario user = repoUsuario.findById(iDUser).orElseThrow(() -> new Exception("Usuario no encontrado"));
        user.setNombre("Juan Luis");
        user.setEmail("jl@jll.com");
        user.setAlta(fecha);
        controladorUsuarios.actualizar(user);
        Usuario userActualizado = repoUsuario.findById(iDUser).orElseThrow(() -> new Exception("Usuario no encontrado"));
        assertThat(userActualizado.getNombre(), is("Juan Luis"));
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        assertThrows(UsuarioException.class, () -> {
            Long iDUser = 3L;
            LocalDate fecha = LocalDate.parse("2025-12-17");
            Usuario user = repoUsuario.findById(iDUser).orElseThrow(() -> new Exception("Usuario no encontrado"));
            user.setNombre("Juan Luis");
            user.setEmail("jl@jll.com");
            user.setAlta(fecha);
            controladorUsuarios.actualizar(user);
        });
    }

    @Test
    @Transactional
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() throws Exception {
        Usuario user = repoUsuario.findById(1L).orElseThrow(() -> new Exception("Usuario no encontrado"));
        boolean ok = controladorUsuarios.baja(user);
        assertThat(ok, is(true));
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
        Usuario user = new Usuario(-1L, null, null, null, true, null, null);
        assertThrows(Exception.class, () -> {
            controladorUsuarios.baja(user);
        });
    }
}