package com.github.syuchan1005.mcplugindebugger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import javafx.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;

/**
 * Created by syuchan on 2017/07/03.
 */
public class Util {
	private static Field pluginsField;
	private static Field lookupNamesField;
	private static Field commandMapField;
	private static Field knownCommandsField;

	private static Field pluginField;
	private static Field pluginInitField;

	static {
		Class<? extends PluginManager> plMClass = Bukkit.getPluginManager().getClass();
		try {
			pluginsField = plMClass.getDeclaredField("plugins");
			pluginsField.setAccessible(true);
			lookupNamesField = plMClass.getDeclaredField("lookupNames");
			lookupNamesField.setAccessible(true);
			commandMapField = plMClass.getDeclaredField("commandMap");
			commandMapField.setAccessible(true);
			knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
			knownCommandsField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public static void loadPlugin(File pluginFile) throws InvalidDescriptionException, InvalidPluginException {
		PluginManager pluginManager = Bukkit.getPluginManager();
		Plugin plugin = pluginManager.loadPlugin(pluginFile);
		plugin.onLoad();
		pluginManager.enablePlugin(plugin);
	}

	public static void unloadPlugin(Plugin plugin) {
		if (plugin == null) return;
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.disablePlugin(plugin);
		try {
			try {
				List<Plugin> plugins = (List<Plugin>) pluginsField.get(pluginManager);
				if (plugins != null && plugins.contains(plugin)) plugins.remove(plugin);
			} catch (ClassCastException ignored) {
			}
			try {
				Map<String, Plugin> names = (Map<String, Plugin>) lookupNamesField.get(pluginManager);
				if (names != null && names.containsKey(plugin.getName())) names.remove(plugin.getName());
			} catch (ClassCastException ignored) {
			}
			try {
				SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(pluginManager);
				Map<String, Command> commands = (Map<String, Command>) knownCommandsField.get(commandMap);
				if (commandMap != null) {
					for (Iterator<Map.Entry<String, Command>> it = commands.entrySet().iterator(); it.hasNext(); ) {
						Map.Entry<String, Command> entry = it.next();
						if (entry.getValue() instanceof PluginCommand) {
							PluginCommand c = (PluginCommand) entry.getValue();
							if (c.getPlugin() == plugin) {
								c.unregister(commandMap);
								it.remove();
							}
						}
					}
				}
			} catch (ClassCastException ignored) {
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		ClassLoader cl = plugin.getClass().getClassLoader();
		if (cl instanceof URLClassLoader) {
			try {
				if (pluginField == null) {
					pluginField = cl.getClass().getDeclaredField("plugin");
					pluginField.setAccessible(true);
				}
				pluginField.set(cl, null);
				if (pluginInitField == null) {
					pluginInitField = cl.getClass().getDeclaredField("pluginInit");
					pluginInitField.setAccessible(true);
				}
				pluginInitField.set(cl, null);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
				ex.printStackTrace();
			}
			try {
				((URLClassLoader) cl).close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.gc();
	}
}
