package server;

import client.SkillFactory;
import client.inventory.MapleInventoryIdentifier;
import constants.BattleConstants;
import constants.ServerConstants;
import handling.ExternalCodeTableGetter;
import handling.MapleServerHandler;
import handling.RecvPacketOpcode;
import handling.SendPacketOpcode;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.login.LoginServer;
import handling.cashshop.CashShopServer;
import handling.login.LoginInformationProvider;
import handling.world.World;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import database.DatabaseConnection;
import handling.world.family.MapleFamily;
import handling.world.guild.MapleGuild;
import java.sql.PreparedStatement;
import server.Timer.*;
import server.events.MapleOxQuizFactory;
import server.life.MapleLifeFactory;
import server.life.MapleMonsterInformationProvider;
import server.life.MobSkillFactory;
import server.life.PlayerNPC;
import server.quest.MapleQuest;
import java.util.concurrent.atomic.AtomicInteger;
import server.maps.MapleMapFactory;

public class Start {

    public static long startTime = System.currentTimeMillis();
    public static final Start instance = new Start();
    public static AtomicInteger CompletedLoadingThreads = new AtomicInteger(0);

    public void run() throws InterruptedException {

        if (Boolean.parseBoolean(ServerProperties.getProperty("net.sf.odinms.world.admin")) || ServerConstants.Use_Localhost) {
            System.out.println("[!!! 仅管理员登陆模式  !!!]");
        }

        try {
            final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET loggedin = 0");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("[异常] SQL是否启动.");
        }
        printSection("世界服务器");
      // System.out.println("[启动服务器：" + ServerProperties.getProperty("net.sf.odinms.login.serverName") + " 版本" + ServerConstants.MAPLE_VERSION +" ]");
        
        World.init();
        System.out.println("世界服务器加载完成...");
        System.out.println("服务器版本 "+ ServerConstants.MAPLE_VERSION);
        
        printSection("时钟线程");
        WorldTimer.getInstance().start();
        EtcTimer.getInstance().start();
        MapTimer.getInstance().start();
        CloneTimer.getInstance().start();
        EventTimer.getInstance().start();
        BuffTimer.getInstance().start();
        PingTimer.getInstance().start();
        
        System.out.println("时钟线程加载完成...");
        //System.out.println("各定时器启动完毕");
        printSection("加载家族");
        MapleGuildRanking.getInstance().load();
        MapleGuild.loadAll(); //(this); 
        
        System.out.println("家族信息加载完成...");
        
        printSection("加载学院");
        //System.out.println("社团信息启动完毕");
        MapleFamily.loadAll(); //(this); 
        System.out.println("学院信息加载完成...");
        
       // System.out.println("家族信息启动完毕");
        printSection("加载任务");
        MapleLifeFactory.loadQuestCounts();
        MapleQuest.initQuests();
        System.out.println("任务信息加载完成....");
        //System.out.println("人物信息启动完毕");
        
        printSection("加载道具 和 爆率");
        MapleItemInformationProvider.getInstance().runEtc();
        
        
       // System.out.println("物品信息启动完毕");
        MapleMonsterInformationProvider.getInstance().load(); 
        //BattleConstants.init(); 
       // System.out.println("怪物信息启动完毕");
        MapleItemInformationProvider.getInstance().runItems(); 
        System.out.println("道具和爆率信息加载完成..");
      //  System.out.println("刷新物品信息");
        
        printSection("加载技能");
        SkillFactory.load();
        System.out.println("技能信息加载完成..");
        
        printSection("基础信息加载器");
        LoginInformationProvider.getInstance();
        RandomRewards.load();
        MapleOxQuizFactory.getInstance();
        MapleCarnivalFactory.getInstance();
		CharacterCardFactory.getInstance().initialize(); 
        MobSkillFactory.getInstance();
        SpeedRunner.loadSpeedRuns();
        MTSStorage.load();
        System.out.println("基础信息加载器加载完成..");
        printSection("加载地图信息");
        //System.out.println("角色信息启动完毕");
        MapleInventoryIdentifier.getInstance();
        MapleMapFactory.loadCustomLife();
        System.out.println("地图信息加载完成..");
        
        printSection("加载商城道具");
        CashItemFactory.getInstance().initialize();
        CashItemFactory.getInstance().initCashBlock();
        System.out.println("商城道具加载完成 ..");
        
        
        
        MapleServerHandler.initiate();
        
        
        System.out.println("[启动登陆服务器]");
        LoginServer.run_startup_configurations();
        System.out.println("[服务器成功开启]");

        System.out.println("[启动频道服务器]");
        ChannelServer.startChannel_Main();
        System.out.println("[频道服务器成功开启]");

        System.out.println("[启动现金商店]");
        CashShopServer.run_startup_configurations();
        System.out.println("[现金商店成功开启]");
        printSection("刷怪线程");
        //CheatTimer.getInstance().register(AutobanManager.getInstance(), 60000);
        Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown()));
        World.registerRespawn();
        //ChannelServer.getInstance(1).getMapFactory().getMap(910000000).spawnRandDrop(); //start it off
        ShutdownServer.registerMBean();
        printSection("启动完毕");
        //ServerConstants.registerMBean();
        PlayerNPC.loadAll();// touch - so we see database problems early...
        MapleMonsterInformationProvider.getInstance().addExtra();
        LoginServer.setOn(); //now or later
        System.out.println("[服务器在 " + ((System.currentTimeMillis() - startTime) / 1000) + " 秒完成启动工作]");
        RankingWorker.run();
       // System.out.println(ExternalCodeTableGetter.getOpcodeTable(RecvPacketOpcode.values()));
       // System.out.println(ExternalCodeTableGetter.getOpcodeTable(SendPacketOpcode.values()));
    }
    public static void printSection(String s)
    {
      s = "-[ " + s + " ]";
      while (s.getBytes().length < 79) {
        s = "=" + s;
      }
      System.out.println(s);
    }
    
    public static class Shutdown implements Runnable {

        @Override
        public void run() {
            ShutdownServer.getInstance().run();
            ShutdownServer.getInstance().run();
        }
    }

    public static void main(final String args[]) throws InterruptedException {
    	try
    	{
    	System.getProperties().load(new FileInputStream("game.properties"));
    	}
    	catch(Exception ex)
    	{
    		System.err.println("缺少基本配置文件.");
    		System.exit(0);
    	}
    	
        instance.run();
    }
}
