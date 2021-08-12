package fr.fyz.hcf.faction.json;

import java.util.List;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;


public class ListJSON<T extends Object> {

	private Gson gson;
	
	public ListJSON() {
		this.gson = createGsonInstance();
	}
	
	private Gson createGsonInstance() {
		return new GsonBuilder()
				.serializeNulls()
				.disableHtmlEscaping()
				.create();
	}
	
	
	public String serialize(List<T> list) {
		return this.gson.toJson(list);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> deserialize(String json) {
		return this.gson.fromJson(json, List.class);
	}
}
