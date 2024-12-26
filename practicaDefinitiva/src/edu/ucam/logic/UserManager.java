package edu.ucam.logic;

import edu.ucam.logic.persistance.FileManager;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static final String USER_FILE = "usuarios.dat";

    private static UserManager instance = null;
    private Map<String, User> users;

    private UserManager() {
        // Cargar usuarios
        loadUsers();

        if(users == null) {
            Log.getInstance().error("No se han podido cargar los usuarios.Creando usuarios por defecto...");
            // Crear usuarios por defecto
            users = new HashMap<>();
            addUser(new User("admin", "admin", "admin"));
            addUser(new User("user", "user", "user"));
        }

    }

    public static UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void addUser(User user) {
        Log.getInstance().debug("Añadiendo usuario: " + user + "...");
        if(searchUser(user.getName()) != null) {
            Log.getInstance().error("El usuario " + user.getName() + " ya existe.");
            return;
        }
        users.put(user.getName(), user);
        Log.getInstance().info("Usuario añadido correctamente: " + user);
        saveUsers();
    }

    public void removeUser(String name) {
        Log.getInstance().debug("Eliminando usuario: " + name + "...");
        User user = searchUser(name);
        if(user != null) {
            users.remove(name);
            Log.getInstance().info("Usuario eliminado correctamente: " + user);
            saveUsers();
        } else {
            Log.getInstance().error("El usuario " + name + " no existe.");
        }
    }

    public String listUsers() {
        StringBuilder sb = new StringBuilder();
        for (User user : users.values()) {
            sb.append(user).append("\n");
        }
        return sb.toString();
    }

    public void saveUsers() {
        Log.getInstance().debug("Guardando usuarios en " + USER_FILE + "...");
        FileManager.save(USER_FILE, users);
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        Log.getInstance().debug("Cargando usuarios desde " + USER_FILE + "...");
        users = (Map<String, User>) FileManager.load(USER_FILE);
    }

    public User login(User user) {
        String name = user.getName();
        String password = user.getPassword();

        Log.getInstance().debug("Iniciando sesión...");
        User tempUser = searchUser(name);
        if(tempUser != null && tempUser.getPassword().equals(password)) {
            Log.getInstance().info("Usuario autenticado: " + tempUser);
            return tempUser;
        } else if (tempUser == null) {
            Log.getInstance().error("Usuario no encontrado: " + name);
        } else {
            Log.getInstance().error("Contraseña incorrecta para el usuario: " + name);
        }
        return null;
    }

    public User searchUser(String name) {
        return users.get(name);
    }
}
