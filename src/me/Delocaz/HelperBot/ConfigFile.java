package me.Delocaz.HelperBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigFile {
	HelperBot hb;
	YamlConfiguration lng;
	public ConfigFile(HelperBot hb) {
		this.hb = hb;
		init();
	}
	public void init() {
		hb.getConfig().addDefault("extension", "txt");
		hb.getConfig().addDefault("addhelp.newline", "/n/");
		hb.getConfig().addDefault("default", "default");
		hb.getConfig().addDefault("permissions.help", "helperbot.help");
		hb.getConfig().addDefault("permissions.addhelp", "helperbot.addhelp");
		hb.getConfig().addDefault("permissions.delhelp", "helperbot.delhelp");
		hb.getConfig().addDefault("shortcodes.player", "%player");
		hb.getConfig().addDefault("shortcodes.x", "%x");
		hb.getConfig().addDefault("shortcodes.y", "%y");
		hb.getConfig().addDefault("shortcodes.z", "%z");
		hb.getConfig().addDefault("shortcodes.level", "%level");
		hb.getConfig().addDefault("shortcodes.world", "%world");
		hb.getConfig().addDefault("shortcodes.coords", "%coords");
		hb.getConfig().addDefault("locale", "en_US");
		hb.getConfig().options().copyDefaults(true);
		hb.saveConfig();
		lng = new YamlConfiguration();
		File f = new File(hb.getDataFolder().getPath() + File.separator + "lang" + File.separator + get("locale"));
		try {
			lng.load(f);
		} catch (FileNotFoundException e) {
			try {
				f.getParentFile().mkdir();
				f.createNewFile();
				lng.load(f);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InvalidConfigurationException e1) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		lng.addDefault("deletedSuccessfully", "&a%page deleted successfully.");
		lng.addDefault("createdSuccessfully", "&a%page created successfully.");
		lng.addDefault("specifyPage", "&4Please specify a page.");
		lng.addDefault("missingContent", "&4Tell me what to put in %page!");
		lng.addDefault("noPermission", "Unknown command. Type \"help\" for help.");
		lng.options().copyDefaults(true);
		try {
			lng.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String get(String path) {
		return hb.getConfig().getString(path);
	}
	public String getLang(String path) {
		return lng.getString(path).replaceAll("&([0-9a-f])", "\u00A7$1");
	}
	public void reload() {
		hb.reloadConfig();
		init();
	}
}
