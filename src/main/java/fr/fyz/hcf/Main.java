package fr.fyz.hcf;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.fyz.hcf.commands.CommandFACTION;
import fr.fyz.hcf.listeners.JoinListener;

public class Main extends JavaPlugin{
	
	private static Main INSTANCE;
	
	public static Main getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		loadCommands();
		loadEvents();
	}
	
	private void loadEvents() {
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
	}

	private void loadCommands() {
		getCommand("faction").setExecutor(new CommandFACTION());
		getCommand("f").setExecutor(new CommandFACTION());
	}
	
}
