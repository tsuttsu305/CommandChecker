package jp.tsuttsu305.CommandChecker;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandChecker extends JavaPlugin {
	public static CommandChecker plugin;
	Logger logger = Logger.getLogger("Minecraft");

	public List<String> noAlert, kickCmd;
	public String kickMsg;

	@Override
	public void onEnable (){
		getServer().getPluginManager().registerEvents(new PlayerCommand(this), this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		noAlert = getConfig().getStringList("noAlert");
		kickCmd = getConfig().getStringList("kickCmd");
		kickMsg = getConfig().getString("kickMsg");
	}

	//noAlertチェック用
	public boolean chkNoAlert(String cmd){
		//判定用 trueになったらAlert不要
		boolean chk = false;
		
		for (String s: noAlert){
			if (s.equalsIgnoreCase(cmd)){
				chk = true;
				break;
			}
		}
		return chk;
	}
	
	//KickCmdチェック
	public boolean chkKick(String cmd){
		//チェック用
		boolean chk = false;
		
		for (String s:kickCmd){
			if (s.equalsIgnoreCase(cmd)){
				chk = true;
				break;
			}
		}
		return chk;
	}
	
	//KickMsg
	public String getKickMsg(){
		return kickMsg;
	}
	
}
