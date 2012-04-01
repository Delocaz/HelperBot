package me.Delocaz.HelperBot;

import java.io.IOException;

import org.bukkit.ChatColor;
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
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    System.out.println("Failed to submit stats :-(");
		}
	}
	public void onDisable() {
		System.out.println("[HelperBot] Bai!");
	}
	public void disable() {
		setEnabled(false);
	}
	public String colorize(String s) {
		s = s.replaceAll("&([0-9a-f])", "\u00A7$1");
		s = s.replaceAll("&k", "" + ChatColor.MAGIC.getChar());
		return s;
	}
}