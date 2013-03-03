/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.messages.commands;

import client.MapleCharacter;
import client.MapleClient;
import constants.ServerConstants.PlayerGMRank;
import handling.world.World;
import tools.StringUtil;
import tools.packet.CWvsContext;

/**
 *
 * @author Emilyx3
 */
public class SuperDonatorCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.SUPERDONATOR;
    }

         public static class Say extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
             if (splitted.length > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                if (!c.getPlayer().isGM()) {
                    sb.append("嘉宾 ");
                }
                sb.append(c.getPlayer().getName());
                sb.append("] ");
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                World.Broadcast.broadcastMessage(CWvsContext.serverNotice(5, sb.toString()));
            } else {
                c.getPlayer().dropMessage(6, "使用方法: say 要广播的话");
                return 0;
            }
            return 1;
        }
    }
         //封禁 是否合适??
                  public static class Ban extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
			if (splitted.length < 3) {
                c.getPlayer().dropMessage(5, "[使用方法] %ban <IGN> <Reason>");
                return 0;
            }
			String originalReason = StringUtil.joinStringFrom(splitted, 2);
			String reason = c.getPlayer().getName() + " banned " + splitted[1] + ": " + originalReason;
			MapleCharacter target = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
			if (target != null) {
				String ip = target.getClient().getSession().getRemoteAddress().toString().split(":")[0];
				reason += " (IP: " + ip + ")";
				target.ban(reason, false);
				World.Broadcast.broadcastSmega(CWvsContext.serverNotice(0, "[嘉宾 封禁] " + c.getPlayer().getName() + " 因为 " + originalReason+ " 把 玩家 "+ target + " 封禁 " ));
			} else {
				if (MapleCharacter.ban(splitted[1], reason, false)) {
					c.getPlayer().dropMessage(5, "离线封禁 " + splitted[1]);
				} else {
					c.getPlayer().dropMessage(5, "对 " + splitted[1] +" 封禁失败");
				}
                        }
                              return 0;
        }
         }
}