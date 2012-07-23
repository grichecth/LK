package com.grichecth.blocks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.block.design.Texture;

import org.getspout.spoutapi.material.CustomBlock;

public class Main extends JavaPlugin {
	public static CustomBlock CusBlock;
	Logger log = Logger.getLogger("Minecraft");
	public static Texture CusBlockTexture;
	private Statement statement;
	public static ConfigMaker conf = new ConfigMaker();
	public Map<String, Boolean> BlocksEnabled = new HashMap<String, Boolean>();

	public void onEnable() {
		conf.load();
		LoadBlocks();
	}

	public void onDisable() {
		this.log.info("[GrichBlocks] has been disabled.");
	}

	public void LoadBlocks() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:"
					+ getConfig().getString("database.address") + ":"
					+ getConfig().getString("database.port") + "/"
					+ getConfig().getString("database.dbname"), getConfig()
					.getString("database.user"),
					getConfig().getString("database.pass"));
			this.statement = connection.createStatement();
			ResultSet rs = this.statement
					.executeQuery("SELECT name, texture, blockid FROM "
							+ getConfig().getString("database.table") + "");
			while (rs.next()) {
				if (!BlocksEnabled.containsKey(rs.getString(1))) {
					BlocksEnabled.put(rs.getString(1), true);
					CusBlockTexture = new Texture(this, rs.getString(2), 16, 16, 16);
					CusBlock = new Blockmaker(this, rs.getString(1), rs.getInt(3), CusBlockTexture);
					this.log.info("[GrichBlocks] " + rs.getString(1) + " Loaded.");
				}
			}
			// Close Connections
			this.log.info("[GrichBlocks] Successfully read from MySQL.");
			rs.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("gbreload")) {
			LoadBlocks();
			return true;
		}
		return false;
	}
}
