package me.Delocaz.HelperBot;

import org.bukkit.plugin.java.JavaPlugin;

public class HelperBot extends JavaPlugin {
	public ConfigFile cf;
	public void onEnable() {
		cf = new ConfigFile(this);
		getCommand("help").setExecutor(new HelpCommand(this));
		getCommand("addhelp").setExecutor(new AddHelpCommand(this));
		getCommand("delhelp").setExecutor(new DelHelpCommand(this));
	}
	public void onDisable() {
		System.out.println("[HelperBot] Bai!");
	}
	public void disable() {
		setEnabled(false);
	}
}