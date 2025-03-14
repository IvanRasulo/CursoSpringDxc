package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ServicioUsuariosImpl implements IServicioUsuarios {

    @Autowired
    private IServicioUsuarios servUsuarios;

    @Override
    public Usuario obtener(int id) throws UsuarioException {
        return servUsuarios.obtener(id);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException {
        return servUsuarios.crearUsuario(usuario);
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        return servUsuarios.borrarUsuario(usuario);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        return servUsuarios.actualizarUsuario(usuario);
    }

    @Override
    public Set<Usuario> obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException {
        return servUsuarios.obtenerPosiblesDesinatarios(usuario, max);
    }
}
