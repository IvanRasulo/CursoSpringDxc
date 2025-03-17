package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeJPARepository;
import com.banana.bananawhatsapp.persistencia.IUsuarioJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ServicioUsuariosImpl implements IServicioUsuarios {

    @Autowired
    private IUsuarioJPARepository servUsuarios;

    @Autowired
    private IMensajeJPARepository repoMensaje;

    @Override
    public Usuario obtener(Long id) throws UsuarioException {
        return servUsuarios.findById(id).orElseThrow(() -> new UsuarioException("No se ha encontrado el usuario"));
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException {
        return servUsuarios.save(usuario);
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        Optional<Usuario> usuarioExistente = servUsuarios.findById(usuario.getId());

          if (!usuarioExistente.isPresent()) {
                throw new UsuarioException("El usuario no existe.");
            }
        repoMensaje.deleteByRemitente(usuario);
        repoMensaje.deleteByDestinatario(usuario);

        servUsuarios.delete(usuario);
        return true;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        return servUsuarios.save(usuario);
    }

    @Override
    public Set<Usuario> obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException {
        //return servUsuarios.obtenerPosiblesDesinatarios(usuario, max);
        return Set.of();
    }
}
