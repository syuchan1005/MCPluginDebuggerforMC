package com.github.syuchan1005.mcplugindebugger;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;

/**
 * Created by syuchan on 2017/07/03.
 */
public class SocketRunnable implements Runnable {
	private String pluginName;
	private File pluginsFolder;

	private ServerSocket serverSocket;

	public SocketRunnable(int port, String pluginName, File pluginsFolder) {
		this.pluginName = pluginName;
		this.pluginsFolder = pluginsFolder;
		try  {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("localhost", port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bukkit.getLogger().info("Waiting...");
	}

	@Override
	public void run() {
		while (true) {
			try (Socket socket = serverSocket.accept(); DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
				String name = inputStream.readUTF();
				if (!Objects.equals(pluginName, name)) continue;
				Util.unloadPlugin(Bukkit.getPluginManager().getPlugin(pluginName));
				Bukkit.getConsoleSender().sendMessage("[Debugger] Unload Plugin");
				File file = new File(pluginsFolder, pluginName + ".jar");
				byte[] buffer = new byte[512];
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				int fLength;
				while ((fLength = inputStream.read(buffer)) > 0) {
					fileOutputStream.write(buffer, 0, fLength);
				}
				fileOutputStream.flush();
				fileOutputStream.close();
				Bukkit.getConsoleSender().sendMessage("[Debugger] File Reserved");
				Util.loadPlugin(file);
				Bukkit.getConsoleSender().sendMessage("[Debugger] LoadComplete: " + pluginName);
			} catch (IOException | InvalidDescriptionException | InvalidPluginException e) {
				e.printStackTrace();
			}
		}
	}
}
