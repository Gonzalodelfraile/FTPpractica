package com.edu.ucam.persistencia;

import com.edu.ucam.modelos.Usuario;

import java.util.HashMap;
import java.util.Map;

public class UsuarioManager {
    private static final String ARCHIVO_USUARIOS = "usuarios.dat";

    // Instancia única de la clase (Singleton)
    private static UsuarioManager instancia;

    private Map<String, Usuario> usuarios;

    // Constructor privado para evitar instanciación externa
    private UsuarioManager() {
        cargarUsuarios();
        if (usuarios == null) {
            System.out.println("No hay usuarios, creando...");
            usuarios = new HashMap<>();
            Usuario usuario = new Usuario("admin", "admin", "admin");
            addUsuario(usuario);
        }
    }


    public static synchronized UsuarioManager getInstance() {
        if (instancia == null) {
            instancia = new UsuarioManager();
        }
        return instancia;
    }

    public Usuario getUsuario(String nickname) {
        return usuarios.get(nickname);
    }

    public void addUsuario(Usuario usuario) {
        usuarios.put(usuario.getNickname(), usuario);
        guardarUsuarios();
    }

    public boolean validarUsuario(String nickname, String password) {
        Usuario usuario = getUsuario(nickname);
        return usuario != null && usuario.getPassword().equals(password);
    }

    @SuppressWarnings("unchecked")
    public void cargarUsuarios() {
        usuarios = (Map<String, Usuario>) Persistencia.cargar(ARCHIVO_USUARIOS);
    }

    public void guardarUsuarios() {
        Persistencia.guardar(ARCHIVO_USUARIOS, usuarios);
    }

    public void mostrarUsuarios() {
        for (Usuario usuario : usuarios.values()) {
            System.out.println(usuario);
        }
    }
}

