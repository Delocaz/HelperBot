package me.Delocaz.HelperBot;

// import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerJoinEvent;
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
		//this.getServer().getPluginManager().registerEvents(this, this);
	}
	public void onDisable() {
		System.out.println("[HelperBot] Bai!");
	}
	public void disable() {
		setEnabled(false);
	}
/*	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (getConfig().getBoolean("motd.enabled")) {
			hc.onCommand((CommandSender) e.getPlayer(), (Command) null, (String) null, new String[]{cf.get("motd.page")});
		}
	}*/
}