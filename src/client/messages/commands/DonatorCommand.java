package client.messages.commands;

import client.MapleClient;
import constants.ServerConstants.PlayerGMRank;
import handling.channel.ChannelServer;
import handling.world.World;
import tools.StringUtil;
import tools.packet.CWvsContext;

/**
 *
 * @author Emilyx3
 */
public class DonatorCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.DONATOR;
    }
    //这里中文翻译为贵宾
       public static class Say extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                if (!c.getPlayer().isGM()) {
                    sb.append("贵宾玩家 ");
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
    }
