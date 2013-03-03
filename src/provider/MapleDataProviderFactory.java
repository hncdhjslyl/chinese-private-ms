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
package provider;

import java.io.File;

import server.ServerProperties;

public class MapleDataProviderFactory {

    private final static String wzPath = System.getProperty("net.sf.odinms.wzpath");

    private static MapleDataProvider getWZ(Object in) {
        if (in instanceof File &&((File) in).exists()) {
            return new MapleDataProvider((File) in);
        }
        throw new IllegalArgumentException("wz资源不存在 " + in);
    }

    public static MapleDataProvider getDataProvider(Object in) {
    	try{
        return getWZ(in);
    	}
    	catch(IllegalArgumentException e)
    	{
    		System.err.println(e.getMessage());
    		System.exit(0);
    	}
    	return null;
    }

    public static File fileInWZPath(String filename) {
        return new File(wzPath, filename);
    }
}
