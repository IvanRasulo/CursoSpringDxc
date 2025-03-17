package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioMensajeriaImpl implements IServicioMensajeria {

    @Autowired
    private IMensajeJPARepository repoMensajeria;

    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException {
        if (remitente == null || destinatario == null) {
            throw new UsuarioException("Los usuarios no pueden ser nulos.");
        }
        if (texto == null || texto.trim().isEmpty()) {
            throw new MensajeException("El mensaje no puede estar vacío.");
        }
        Mensaje mensajeNuevo = new Mensaje(null, remitente, destinatario, texto, LocalDate.now());
        return repoMensajeria.save(mensajeNuevo);
    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        return repoMensajeria.findByRemitenteAndDestinatario(remitente, destinatario);
    }

    @Transactional
    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
    if (remitente == null || destinatario == null || !repoMensajeria.existsByRemitenteAndDestinatario(remitente, destinatario)) {
        throw new UsuarioException("Los usuarios no existen en la base de datos.");
    }
    repoMensajeria.deleteByRemitenteAndDestinatario(remitente, destinatario);
    return true;
    }
}
