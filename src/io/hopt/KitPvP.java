package io.hopt;

import io.hopt.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Owner on 15/07/2017.
 */
public class KitPvP extends JavaPlugin implements Listener {

    public static KitPvP instance;
    public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
    public static PluginManager pm = Bukkit.getServer().getPluginManager();
    public String host;
    public String username;
    public String password;
    public String database;
    public String table;
    public int port;
    private Connection connection;

    @Override
    public void onEnable() {
        instance = this;
        this.MySQLSetup();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        pm.registerEvents(new Profile(), this);

    }

    public void onDisable() {

    }

    public static KitPvP getInstance() {
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void MySQLSetup() {
        this.host = this.getConfig().getString("host");
        this.port = this.getConfig().getInt("port");
        this.username = this.getConfig().getString("username");
        this.password = this.getConfig().getString("password");
        this.database = this.getConfig().getString("database");
        this.table = this.getConfig().getString("table");
        try {
            KitPvP login = this;
            synchronized (login) {
                if (this.getConnection() != null && !this.getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                this.setConnection(
                        DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                                this.username, this.password));
                console.sendMessage("[KitPvP] Successfully connected to SQL.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
    }
}
