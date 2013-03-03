/*
This file is part of the OdinMS Maple Story Server
Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
Matthias Butz <matze@odinms.de>
Jan Christian Meyer <vimes@odinms.de>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License version 3
as published by the Free Software Foundation. You may not use, modify
or distribute this program under any other version of the
GNU Affero General Public License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package handling.cashshop;

import java.net.InetSocketAddress;

import handling.MapleServerHandler;
import handling.channel.PlayerStorage;
import handling.mina.MapleCodecFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
//import org.apache.mina.transport.socket.nio.NioSo;
import org.apache.mina.transport.socket.SocketSessionConfig;

import constants.ServerConstants;
import server.MTSStorage;
import server.ServerProperties;

public class CashShopServer {

    private static String ip;
    private static InetSocketAddress InetSocketadd;
    public static int PORT = 8600;
    private static IoAcceptor acceptor;
    private static PlayerStorage players, playersMTS;
    private static boolean finishedShutdown = false;

    public static final void run_startup_configurations() {
    	PORT = Integer.parseInt(ServerProperties.getProperty("net.sf.odinms.shop.host",String.valueOf(8600)));
        ip = ServerProperties.getProperty("net.sf.odinms.world.host") + ":" + PORT;

        IoBuffer.setUseDirectBuffer(false);
        IoBuffer.setAllocator(new SimpleBufferAllocator());

        acceptor = new NioSocketAcceptor();
        //final NioSocketAcceptorConfig cfg = new NioSocketAcceptorConfig();
        //cfg.getSessionConfig().setTcpNoDelay(true);
        //cfg.setDisconnectOnUnbind(true);
        //cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MapleCodecFactory()));
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MapleCodecFactory()));
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        players = new PlayerStorage(-10);
        playersMTS = new PlayerStorage(-20);

        try {
            InetSocketadd = new InetSocketAddress(ServerConstants.IP,PORT);
            acceptor.setHandler(new MapleServerHandler());
            acceptor.bind(InetSocketadd);
            System.out.println("端口 " + PORT + " 开始工作.");
        } catch (final Exception e) {
            System.err.println("绑定端口 " + PORT + " 失败");
            System.exit(0);
          //  e.printStackTrace();
          //  throw new RuntimeException("绑定失败.", e);
        }
    }

    public static final String getIP() {
        return ip;
    }

    public static final PlayerStorage getPlayerStorage() {
        return players;
    }

    public static final PlayerStorage getPlayerStorageMTS() {
        return playersMTS;
    }

    public static final void shutdown() {
        if (finishedShutdown) {
            return;
        }
        System.out.println("正在关闭商城服务器...");
        players.disconnectAll();
	playersMTS.disconnectAll();
        MTSStorage.getInstance().saveBuyNow(true);
        System.out.println("商城服务器解除端口绑定...");
	//acceptor.unbindAll();
        finishedShutdown = true;
    }

    public static boolean isShutdown() {
	return finishedShutdown;
    }
}
