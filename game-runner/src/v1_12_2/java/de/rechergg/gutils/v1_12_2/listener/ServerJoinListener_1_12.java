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

import de.rechergg.gutils.GUtilsAPI;
import de.rechergg.gutils.v1_12_2.events.CustomPayloadEvent_1_12;
import de.rechergg.gutils.v1_12_2.events.IncomingPacketEvent_1_12;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import java.lang.reflect.Field;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketCustomPayload;

/*
 * @created: 25.02.2023 - 17:35
 * @author: RECHERGG
 */
public class ServerJoinListener_1_12 {

  private final GUtilsAPI gUtilsAPI;

  public ServerJoinListener_1_12(GUtilsAPI gUtilsAPI) {
    this.gUtilsAPI = gUtilsAPI;
  }

  @Subscribe
  public void onJoin(ServerJoinEvent event) {
    try {
      Field field = Minecraft.getMinecraft().player.connection.getClass().getDeclaredField(this.gUtilsAPI.isDebug() ? "channel" : "k");
      field.setAccessible(true);
      Channel channel = (Channel) field.get(Minecraft.getMinecraft().getCurrentServerData());
      ChannelPipeline pipeline = channel.pipeline();
      pipeline.addBefore("packet_handler", "listener", new ChannelDuplexHandler() {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          if(msg instanceof Packet) {
            Packet<?> packet = (Packet<?>) msg;
            IncomingPacketEvent_1_12 incomingPacketEvent = new IncomingPacketEvent_1_12((Packet<?>) msg);
            GUtilsAPI.EVENT_BUS.post(incomingPacketEvent);
            if(!incomingPacketEvent.isCancelled()) {
              super.channelRead(ctx, msg);
            }

            if(!(incomingPacketEvent.getPacket() instanceof SPacketCustomPayload)) {
              return;
            }

            SPacketCustomPayload s3FPacketCustomPayload = (SPacketCustomPayload) incomingPacketEvent.getPacket();

            GUtilsAPI.EVENT_BUS.post(new CustomPayloadEvent_1_12(s3FPacketCustomPayload.getChannelName(), s3FPacketCustomPayload.getBufferData()));
          }else {
            super.channelRead(ctx, msg);
          }
        }


      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
