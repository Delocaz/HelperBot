package me.Delocaz.HelperBot;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHelpCommand implements CommandExecutor {
	HelperBot hb;
	public DelHelpCommand(HelperBot hb) {
		this.hb = hb;
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(hb.cf.getLang("specifyPage"));
			return true;
		} else if (sender instanceof Player) {
			if (!(((Player) sender).hasPermission(hb.cf.get("permissions.delhelp")) || ((Player) sender).isOp())) {
				sender.sendMessage("Unknown command. Type \"help\" for help.");
				return true;
			}
		}
		String page = args[0];
		File f = new File(hb.getDataFolder().getPath() + File.separatorChar + page + "." + hb.cf.get("extension"));
		f.delete();
		sender.sendMessage(hb.cf.getLang("deletedSuccessfully").replaceAll("%page", page));
		return true;
	}
}