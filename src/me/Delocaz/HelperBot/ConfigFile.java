package me.Delocaz.HelperBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigFile {
	HelperBot hb;
	YamlConfiguration lng;
	enum versions {
		HB2_0, HB2_1;
		static versions current = HB2_1;
		public boolean isCurrent() {
			return this == current;
		}
		public static versions getVersion(String s) {
			if (s == "HelperBot v2.0") {
				return HB2_0;
			} else {
				try {
					return versions.valueOf(s);
				} catch (IllegalArgumentException e) {
					try {
						throw new Exception("I SAID DO NOT CHANGE!");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			return null;
		}
	}
	public ConfigFile(HelperBot hb) {
		this.hb = hb;
		init();
	}
	public void init() {
		lng = new YamlConfiguration();
		try {
			lng.load(new File(hb.getDataFolder().getPath() + File.separator + "lang" + File.separator + "language.yml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		lng.addDefault("deletedSuccessfully", "&a%page deleted successfully.");
		lng.addDefault("createdSuccessfully", "&a%page created successfully.");
		lng.addDefault("specifyPage", "&4Please specify a page.");
		lng.addDefault("missingContent", "&4Tell me what to put in %page!");
		hb.getConfig().addDefault("extension", "txt");
		hb.getConfig().addDefault("addhelp.newline", "/n/");
		hb.getConfig().addDefault("version_DONOTCHANGE", "HB2_1");
		hb.getConfig().addDefault("default", "default");
		hb.getConfig().options().copyDefaults(true);
		convert(versions.getVersion(hb.getConfig().getString("version_DONOTCHANGE")));
		hb.saveConfig();
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
	public void convert(versions from) {
		if (from.isCurrent()) return;
		switch(from) {
		case HB2_0:
			hb.getConfig().set("default", "default");
			hb.getConfig().set("version_DONOTCHANGE", versions.current.toString());
		default:
			return;
		}
	}
}
