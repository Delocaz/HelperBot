package me.Delocaz.HelperBot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddHelpCommand implements CommandExecutor {
	HelperBot hb;
	public AddHelpCommand(HelperBot hb) {
		this.hb = hb;
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(hb.cf.getLang("specifyPage"));
			return true;
		} else if (args.length == 1) {
			sender.sendMessage(hb.cf.getLang("missingContent").replaceAll("%page", args[0]));
			return true;
		} else if (sender instanceof Player) {
			if (!(((Player) sender).hasPermission(hb.cf.get("permissions.addhelp")) || ((Player) sender).isOp())) {
				sender.sendMessage("Unknown command. Type \"help\" for help.");
				return true;
			}
		}
		String page = args[0];
		File f = new File(hb.getDataFolder().getPath() + File.separatorChar + page + "." + hb.cf.get("extension"));
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f));
			String content = "";
			for (int i = 1; i < args.length; i++) {
				if (content != "") {content = content + " ";}
				content = content + args[i];
			}
			String[] split = content.split(hb.cf.get("addhelp.newline"));
			for (String s : split) {
				bw.write(s + System.getProperty("line.separator"));
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sender.sendMessage(hb.cf.getLang("createdSuccessfully").replaceAll("%page", page));
		return true;
	}
}