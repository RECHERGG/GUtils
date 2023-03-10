/*
 * Copyright (c) 2023 RECHERGG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.rechergg.gutils.v1_12_2.listener;

import com.google.common.base.Charsets;
import de.rechergg.gutils.GUtilsAPI;
import de.rechergg.gutils.event.EventHandler;
import de.rechergg.gutils.event.events.mysterymod.MysteryModMessageEvent;
import de.rechergg.gutils.v1_12_2.events.CustomPayloadEvent_1_12;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import net.minecraft.network.PacketBuffer;

/*
 * @created: 25.02.2023 - 20:37
 * @author: RECHERGG
 */
public class CustomPayloadListener_1_12 {

  private final GUtilsAPI gUtilsAPI;

  public CustomPayloadListener_1_12(GUtilsAPI gUtilsAPI) {
    this.gUtilsAPI = gUtilsAPI;
  }

  @EventHandler
  public void onCustomPayload(CustomPayloadEvent_1_12 event) {
    if(event.getChannelName().equals("mysterymod:mm")) {

      if(event.getPacketBuffer().readableBytes() <= 0){
        return;
      }
      String messageKey = readStringFromBuffer(32767, event.getPacketBuffer());

      if(event.getPacketBuffer().readableBytes() <= 0) {
        return;
      }
      String messageContent = readStringFromBuffer(32767, event.getPacketBuffer());

      GUtilsAPI.EVENT_BUS.post(new MysteryModMessageEvent(messageKey, messageContent));
    }
  }

  public String readStringFromBuffer(int maxLength, PacketBuffer packetBuffer) {
    int i = this.readVarIntFromBuffer(packetBuffer);

    if (i > maxLength * 4) {
      throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + maxLength * 4 + ")");
    } else if (i < 0) {
      throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
    } else {
      ByteBuf bytebuf = packetBuffer.readBytes(i);
      byte[] abyte = null;

      if (bytebuf.hasArray()) {
        abyte = bytebuf.array();
      } else {
        abyte = new byte[bytebuf.readableBytes()];
        bytebuf.getBytes(bytebuf.readerIndex(), abyte);
      }

      String s = new String(abyte, Charsets.UTF_8);

      if (s.length() > maxLength) {
        throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + maxLength + ")");
      }
      else {
        return s;
      }
    }
  }

  public int readVarIntFromBuffer(PacketBuffer packetBuffer) {
    int i = 0;
    int j = 0;

    while (true) {
      byte b0 = packetBuffer.readByte();
      i |= (b0 & 127) << j++ * 7;

      if (j > 5) {
        throw new RuntimeException("VarInt too big");
      }

      if ((b0 & 128) != 128) {
        break;
      }
    }

    return i;
  }
}
