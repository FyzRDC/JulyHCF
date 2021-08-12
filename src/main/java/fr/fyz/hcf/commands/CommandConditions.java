package fr.fyz.hcf.commands;

import java.util.Arrays;

import org.bukkit.entity.Player;

public enum CommandConditions {
	
	INFO(0, 1, ""),
	JOIN(0, 2, "<faction>"),
	LEAVE(0, 1, ""),
	CREATE(0, 2, "<name>"),
	INVITE(2, 2, "<player>"),
	DISBAND(3, 1, ""),
	NAME(3, 2, "<new name>"),
	DESC(3, 2, "<desc>"),
	SETHOME(2, 1, ""),
	OPEN(2, 1, ""),
	CLOSE(2, 1, ""),
	UNSETHOME(2, 1, ""),
	KICK(2, 2, "<player>"),
	PROMOTE(3, 2, "<player>"),
	DEMOTE(3, 2, "<player>"),
	CLAIM(2, 1, ""),
	TOP(0, 1, "");
	
	private int neededPower;
	private int args;
	private String template;
	
	private CommandConditions(int neededPower, int args, String template) {
		this.neededPower = neededPower;
		this.template = template;
		this.args = args;
	}
	
	public String getTemplate() {
		return template;
	}
	
	public int getNeededPower() {
		return neededPower;
	}
	
	public int getMinArgs() {
		return args;
	}
	
	public static CommandConditions getConditionsByName(String cmd) {
		return Arrays.stream(values()).filter(i -> i.toString().toLowerCase().equals(cmd.toLowerCase())).findAny().orElse(null);
	}
	
	public static boolean canExecute(String cmd, String[] args, Player p) {
		CommandConditions cc = getConditionsByName(cmd);
		if(cc == null) {
			return false;
		}
		if(cc.getMinArgs() <= args.length) {
			//if(p.getPower() >= cc.getNeededPower()) { 
				return true;
			//}
		}
		return false;
	}

}
