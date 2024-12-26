package edu.ucam.logic;

import edu.ucam.logic.persistencia.FileManager;
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

    private void addUser(User user) {
        Log.getInstance().info("Añadiendo usuario: " + user);
        users.put(user.getName(), user);
        saveUsers();
    }

    private void saveUsers() {
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
