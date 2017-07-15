package io.hopt.sql;

import io.hopt.KitPvP;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Owner on 15/07/2017.
 */
public class MySQL {

    static KitPvP plugin = KitPvP.getPlugin(KitPvP.class);

    public static boolean dataExists(UUID uuid, Player player) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + MySQL.plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveProfile(UUID uuid, Player player, String ip) {
        try {
            if (MySQL.dataExists(uuid, player)) {
                return;
            }

            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("UPDATE " + MySQL.plugin.table + " (UUID, IP) VALUES (?,?,?)");
            statement.setString(1, uuid.toString());
            statement.setString(2, ip.toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerPlayer(UUID uuid, Player player, String ip) {
        try {
            if (MySQL.dataExists(uuid, player)) {
                return;
            }

            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("INSERT INTO " + MySQL.plugin.table + " (UUID, IP) VALUES (?,?,?)");
            statement.setString(1, uuid.toString());
            statement.setString(2, ip.toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
