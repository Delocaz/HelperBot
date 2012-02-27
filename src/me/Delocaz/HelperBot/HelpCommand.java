package me.Delocaz.HelperBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {
	HelperBot hb;
	enum Type {TEXT, URL}
	public HelpCommand(HelperBot hb) {
		this.hb = hb;
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String page = "";
		if (args.length == 0) {
			page = hb.getConfig().getString("default");
		} else {
			for(String arg:args){
				if(!page.equals(""))page += " ";
				page += arg;
			}
		}
		if (sender instanceof Player) {
			if (!(((Player) sender).hasPermission("helperbot.help") || ((Player) sender).isOp())) {
				sender.sendMessage("Unknown command. Type \"help\" for help.");
				return true;
			}
		}
		List<String> l = readPage(page);
		for (String s : l) {
			sender.sendMessage(s.replaceAll("&([0-9a-f])", "\u00A7$1"));
		}
		return true;
	}
	public List<String> readPage(String page) {
		File f = new File(hb.getDataFolder().getPath() + File.separatorChar + page + "." + hb.cf.get("extension"));
		BufferedReader reader;
		List<String> l = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(f));
			String text = null;
			while ((text = reader.readLine()) != null) {
				Object[] o = analyze(text);
				if (o[0] == Type.URL) {
					URL u = new URL((String) o[1]);
					InputStream is = u.openStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					while ((line = br.readLine()) != null) {
						l.add(line);
					}
				} else {
					l.add(text);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			l.clear();
			l.add(ChatColor.DARK_RED + page + " not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return l;
	}
	public Object[] analyze(String text) {
		String re1="(\\[url\\])";
		String re2=".*?";
		String re3="((?:http|https)(?::\\/{2}[\\w]+)(?:[\\/|\\.]?)(?:[^\\s\"]*))";
		Pattern p = Pattern.compile(re1+re2+re3,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(text);
		Object[] o = new Object[2]; 
		if (m.find())
		{
			o[0] = Type.URL;
			o[1] = m.group(2);
			return o;
		}
		o[0] = Type.TEXT;
		o[1] = text;
		return o;
	}
}