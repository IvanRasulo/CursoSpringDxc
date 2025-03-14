package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioJPARepository extends JpaRepository<Usuario, Long> {

    //public Usuario obtener(int id) throws SQLException;
    //public Usuario crear(Usuario usuario) throws SQLException;

    //public Usuario actualizar(Usuario usuario) throws SQLException;

   // public boolean borrar(Usuario usuario) throws SQLException;

   // public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException;
}