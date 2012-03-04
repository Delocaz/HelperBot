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
			if (!(((Player) sender).hasPermission(hb.cf.get("permissions.help")) || ((Player) sender).isOp() || ((Player) sender).hasPermission(hb.cf.get("permissions.pagehelp").replaceAll("%page", page)))) {
				sender.sendMessage(hb.cf.getLang("noPermission"));
				return true;
			}
		}
		List<String> l = readPage(page);
		for (String s : l) {
			sender.sendMessage(shortcodify(s.replaceAll("&([0-9a-f])", "\u00A7$1"), sender));
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
	public String shortcodify(String s, CommandSender sender) {
		String sc_player, sc_coords, sc_x, sc_y, sc_z, sc_level, sc_world;
		if (!(sender instanceof Player)) {
			sc_player = "Console";
			sc_coords = "null";
			sc_x = "null";
			sc_y = "null";
			sc_z = "null";
			sc_level = "null";
			sc_world = "null";
		} else {
			Player p = (Player) sender;
			sc_player = p.getDisplayName();
			sc_x = p.getLocation().getBlockX() + "";
			sc_y = p.getLocation().getBlockY() + "";
			sc_z = p.getLocation().getBlockZ() + "";
			sc_coords = sc_x + ", " + sc_y + ", " + sc_z;
			sc_level = p.getLevel() + "";
			sc_world = p.getWorld().getName();
		}
		s = s.replaceAll(hb.cf.get("shortcodes.player"), sc_player);
		s = s.replaceAll(hb.cf.get("shortcodes.coords"), sc_coords);
		s = s.replaceAll(hb.cf.get("shortcodes.x"), sc_x);
		s = s.replaceAll(hb.cf.get("shortcodes.y"), sc_y);
		s = s.replaceAll(hb.cf.get("shortcodes.z"), sc_z);
		s = s.replaceAll(hb.cf.get("shortcodes.level"), sc_level);
		s = s.replaceAll(hb.cf.get("shortcodes.world"), sc_world);
		return s;
	}
}