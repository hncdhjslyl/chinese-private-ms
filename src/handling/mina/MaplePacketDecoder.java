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
package handling.mina;

import handling.RecvPacketOpcode;
import client.MapleClient;
import server.ServerProperties;
import tools.FileoutputUtil;
import tools.HexTool;
import tools.MapleAESOFB;
import tools.MapleCustomEncryption;
import tools.StringUtil;
import tools.data.input.ByteArrayByteStream;
import tools.data.input.GenericLittleEndianAccessor;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import constants.ServerConstants;

public class MaplePacketDecoder extends CumulativeProtocolDecoder {

    public static final String DECODER_STATE_KEY = MaplePacketDecoder.class.getName() + ".STATE";

    public static class DecoderState {

	public int packetlength = -1;
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
	final DecoderState decoderState = (DecoderState) session.getAttribute(DECODER_STATE_KEY);

/*	if (decoderState == null) {
	    decoderState = new DecoderState();
	    session.setAttribute(DECODER_STATE_KEY, decoderState);
	}*/
	final MapleClient client = (MapleClient) session.getAttribute(MapleClient.CLIENT_KEY);

	if (decoderState.packetlength == -1) {
	    if (in.remaining() >= 4) {
		final int packetHeader = in.getInt();
		if (!client.getReceiveCrypto().checkPacket(packetHeader)) {
		    session.close(false);
		    return false;
		}
		decoderState.packetlength = MapleAESOFB.getPacketLength(packetHeader);
	    } else {
	    	System.out.println("数据长度不足以解码");
		return false;
	    }
	}
	if (in.remaining() >= decoderState.packetlength) {
	    final byte decryptedPacket[] = new byte[decoderState.packetlength];
	    in.get(decryptedPacket, 0, decoderState.packetlength);
	    decoderState.packetlength = -1;

	    client.getReceiveCrypto().crypt(decryptedPacket);
	    // china ver no 
	   // MapleCustomEncryption.decryptData(decryptedPacket);
	    out.write(decryptedPacket);
	    //Add
	    if (ServerConstants.ShowPacket) {
	        int packetLen = decryptedPacket.length;
	        int pHeader = readFirstShort(decryptedPacket);
	        String pHeaderStr = Integer.toHexString(pHeader).toUpperCase();
	        pHeaderStr = StringUtil.getLeftPaddedStr(pHeaderStr, '0', 4);
	        String op = lookupSend(pHeader);
	        String Send = "客户端发送 " + op + " [" + pHeaderStr + "] (" + packetLen + ")\r\n";
	        if (packetLen <= 6000) {
	          String SendTo = Send + HexTool.toString(decryptedPacket) + "\r\n" + HexTool.toStringFromAscii(decryptedPacket);
	            System.out.println(SendTo + "\r\n");
	          }
	        
	      }
	    //
	    return true;
	}
	return false;
    }
    private String lookupSend(int val) {
        for (RecvPacketOpcode op : RecvPacketOpcode.values()) {
          if (op.getValue() == val) {
            return op.name();
          }
        }
        return "UNKNOWN";
      }

      private int readFirstShort(byte[] arr) {
        return new GenericLittleEndianAccessor(new ByteArrayByteStream(arr)).readShort();
      }



}
