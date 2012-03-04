package me.Delocaz.HelperBot;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HelperBot extends JavaPlugin implements Listener {
	HelpCommand hc;
	AddHelpCommand ahc;
	DelHelpCommand dhc;
	public ConfigFile cf;
	public void onEnable() {
		cf = new ConfigFile(this);
		hc = new HelpCommand(this);
		ahc = new AddHelpCommand(this);
		dhc = new DelHelpCommand(this);
		getCommand("help").setExecutor(hc);
		getCommand("addhelp").setExecutor(ahc);
		getCommand("delhelp").setExecutor(dhc);
	}
	public void onDisable() {
		System.out.println("[HelperBot] Bai!");
	}
	public void disable() {
		setEnabled(false);
	}
}