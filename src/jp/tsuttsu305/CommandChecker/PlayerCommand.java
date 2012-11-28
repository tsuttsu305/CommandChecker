package jp.tsuttsu305.CommandChecker;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommand implements Listener {
	private CommandChecker cmdChk = null;

	public PlayerCommand(CommandChecker cmdChk) {
		this.cmdChk = cmdChk;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommand (PlayerCommandPreprocessEvent event){
		//Playerが送信しているのは確定 Player取得
		Player sender = event.getPlayer();
		
		//コマンド取得 "/"排除　スペースで分割
		String cmd = event.getMessage();
		String[] cmdSp = cmd.replace("/", "").split(" ");
		
		//bypass持ってる人とOPは回避
		if (sender.hasPermission("cmdchk.bypass") || sender.isOp()){
			return;
		}
		//noAlert判定
		if (cmdChk.chkNoAlert(cmdSp[0])){
			return;
		}
		
		//Alert発報
		Player[] onPlayer = cmdChk.getServer().getOnlinePlayers();
		for (int i = 0; i < onPlayer.length;i++){
			if (onPlayer[i].hasPermission("cmdchk.alert")){
				onPlayer[i].sendMessage(ChatColor.BLUE + "[CmdChk] " + ChatColor.GREEN + sender.getName() + ChatColor.WHITE + " used Command : " + ChatColor.GREEN +  cmd);
			}
		}
		//Serverにログを残す
		cmdChk.logger.info("[CmdChk] " + sender.getName() + " used Command : " +  cmd);
		
		//Kick判定
		if (cmdChk.chkKick(cmdSp[0])){
			sender.kickPlayer(cmdChk.getKickMsg());
		}
		
	}
}
