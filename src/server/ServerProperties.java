package server;

import constants.GameConstants;
import constants.ServerConstants;

import handling.cashshop.CashShopServer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import database.DatabaseConnection;

/**
 *
 * @author Emilyx3
 */
public class ServerProperties {

    private static final Properties props = new Properties();

    private ServerProperties() {
    }

    static {
        String toLoad = "channel.properties";
        loadProperties(toLoad);
        toLoad = "game.properties";
        loadProperties(toLoad);
		if (getProperty("GMS") != null) {
			GameConstants.GMS = Boolean.parseBoolean(getProperty("GMS"));
		}
		toLoad = GameConstants.GMS ? "worldGMS.properties" : "world.properties";
        loadProperties(toLoad);
        ServerConstants.Use_Localhost=Boolean.parseBoolean(getProperty("useloc"));
        ServerConstants.IP=getProperty("net.sf.odinms.world.host");
        ServerConstants.MAPLE_PATCH=getProperty("PATCH","1");
        //CashShopServer.PORT=Integer.parseInt(getProperty("net.sf.odinms.shop.port",String.valueOf(8600)));
        ServerConstants.MAPLE_VERSION=Short.parseShort(getProperty("VERSION",String.valueOf(105)));
        ServerConstants.SQL_USER=getProperty("db.user","root");
        ServerConstants.SQL_PASSWORD=getProperty("db.pass","");
        ServerConstants.SQL_URL=getProperty("db.url","jdbc:mysql://localhost:3307/moople?autoReconnect=true");
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM auth_server_channel_ip");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //if (rs.getString("name").equalsIgnoreCase("gms")) {
                //    GameConstants.GMS = Boolean.parseBoolean(rs.getString("value"));
                //} else {
            	if(!props.containsKey(rs.getString("name") + rs.getInt("channelid")))
                    props.put(rs.getString("name") + rs.getInt("channelid"), rs.getString("value"));
                //}
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
           // ex.printStackTrace();
        	System.out.println("MySql 服务器访问出错。");
            System.exit(0); //Big ass error.
        }
        

    }

    public static void loadProperties(String s) {
        FileReader fr;
        try {
            fr = new FileReader(s);
            props.load(fr);
            fr.close();
        } catch (IOException ex) {
           // ex.printStackTrace();
            System.out.println("配置文件无法读取。");
        	System.exit(0);
        }
    }

    public static String getProperty(String s) {
        return props.getProperty(s);
    }

    public static void setProperty(String prop, String newInf) {
        props.setProperty(prop, newInf);
    }

    public static String getProperty(String s, String def) {
        return props.getProperty(s, def);
    }
}
