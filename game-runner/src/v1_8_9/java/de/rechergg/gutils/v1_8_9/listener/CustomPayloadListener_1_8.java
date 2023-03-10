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

package de.rechergg.gutils.v1_8_9.listener;

import com.google.common.base.Charsets;
import de.rechergg.gutils.GUtilsAPI;
import de.rechergg.gutils.event.EventHandler;
import de.rechergg.gutils.event.events.mysterymod.MysteryModMessageEvent;
import de.rechergg.gutils.v1_8_9.events.CustomPayloadEvent_1_8;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;

/*
 * @created: 25.02.2023 - 20:37
 * @author: RECHERGG
 */
public class CustomPayloadListener_1_8 {

  private final GUtilsAPI gUtilsAPI;

  public CustomPayloadListener_1_8(GUtilsAPI gUtilsAPI) {
    this.gUtilsAPI = gUtilsAPI;
  }

  @EventHandler
  public void onCustomPayload(CustomPayloadEvent_1_8 event) {
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

  public String readStringFromBuffer(int maxLength, ByteBuf packetBuffer) {
    int i = this.readVarIntFromBuffer(packetBuffer);
    if (i > maxLength * 4) {
      throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + maxLength * 4 + ")");
    } else if (i < 0) {
      throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
    } else {
      ByteBuf byteBuf = packetBuffer.readBytes(i);
      byte[] bytes;
      if (byteBuf.hasArray()) {
        bytes = byteBuf.array();
      } else {
        bytes = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(byteBuf.readerIndex(), bytes);
      }

      String s = new String(bytes, Charsets.UTF_8);
      if (s.length() > maxLength) {
        throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + maxLength + ")");
      } else {
        return s;
      }
    }
  }

  private int readVarIntFromBuffer(ByteBuf packetBuffer) {
    int i = 0;
    int j = 0;

    byte b0;
    do {
      b0 = packetBuffer.readByte();
      i |= (b0 & 127) << j++ * 7;
      if (j > 5) {
        throw new RuntimeException("VarInt too big");
      }
    } while((b0 & 128) == 128);

    return i;
  }
}
