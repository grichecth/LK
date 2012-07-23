package com.grichecth.blocks;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigMaker{
	public static String mainDir = "plugins" + File.separator + "GrichBlocks";
	private File settingsFile = new File(mainDir + File.separator + "config.yml");
	private FileConfiguration config = new YamlConfiguration();
	private ConfigurationSection database;

	public void load()	{
		if (!this.settingsFile.exists()) {
			new File(mainDir).mkdirs();
			try {
				this.settingsFile.createNewFile();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			createDefaultSettingsFile();
		}
		else {
			try {
				this.config.load(this.settingsFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		this.database = this.config.getConfigurationSection("database");
	}

	public void save()	{
		try {
			this.config.save(this.settingsFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ConfigurationSection getDatabaseSettings(){
		return this.database;
	}

	public void createDefaultSettingsFile(){
		this.config.createSection("database");
		this.database = this.config.getConfigurationSection("database");
		this.database.set("address", "mysql://mc.emmz.com");
		this.database.set("port", "3306");
		this.database.set("dbname", "blocks");
		this.database.set("table", "blocktable");
		this.database.set("user", "");
		this.database.set("pass", "");
		save();
	}
}