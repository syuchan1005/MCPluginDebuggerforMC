package com.github.syuchan1005.mcplugindebugger;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCPluginDebugger extends JavaPlugin {
	private static Thread socketThread = null;

	@Override
	public void onEnable() {
		FileConfiguration config = this.getConfig();
		config.options().copyDefaults(true);
		this.saveConfig();
		String pluginName = config.getString("debug.pluginName");
		int port = config.getInt("debug.socketPort");
		socketThread = new Thread(new SocketRunnable(port, pluginName, this.getDataFolder().getParentFile()));
		socketThread.start();
		this.getLogger().info("Enabled MCPluginDebugger(target: " + pluginName + ", port: " + port + ")");
	}

	@Override
	public void onDisable() {
		socketThread.interrupt();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		switch (cmd.getName().toLowerCase()) {
			case "unload":
				Util.unloadPlugin(Bukkit.getPluginManager().getPlugin(args[0]));
				sender.sendMessage("Unloaded");
				break;
			case "load":
				try {
					Util.loadPlugin(new File(this.getDataFolder().getParent(), args[0] + ".jar"));
					sender.sendMessage("Loaded");
				} catch (InvalidDescriptionException | InvalidPluginException e) {
					e.printStackTrace();
				}
				break;
			default:
				return false;
		}
		return true;
	}
}
