package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface IMensajeJPARepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findByRemitenteAndDestinatario(Usuario remitente, Usuario destinatario);
    boolean existsByRemitenteAndDestinatario(Usuario remitente, Usuario destinatario);
    boolean existsByRemitenteOrDestinatario(Usuario remitente, Usuario destinatario);
    void deleteByRemitenteAndDestinatario(Usuario remitente, Usuario destinatario)throws UsuarioException;

    void deleteByRemitente(Usuario user);
    void deleteByDestinatario(Usuario user);

    @Transactional
    @Modifying
    @Query("DELETE FROM Mensaje m WHERE m.remitente = :usuario OR m.destinatario = :usuario")
    void borrarTodos(@Param("usuario") Usuario usuario) throws UsuarioException;

}
