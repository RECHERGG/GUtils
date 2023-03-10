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

import de.rechergg.gutils.GUtilsAPI;
import de.rechergg.gutils.v1_8_9.events.CustomPayloadEvent_1_8;
import de.rechergg.gutils.v1_8_9.events.IncomingPacketEvent_1_8;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import java.lang.reflect.Field;

/*
 * @created: 25.02.2023 - 17:35
 * @author: RECHERGG
 */
public class ServerJoinListener_1_8 {

  private final GUtilsAPI gUtilsAPI;

  public ServerJoinListener_1_8(GUtilsAPI gUtilsAPI) {
    this.gUtilsAPI = gUtilsAPI;
  }

  @Subscribe
  public void onJoin(ServerJoinEvent event) {
    try {
      for (Field f : Minecraft.getMinecraft().getNetHandler().getNetworkManager().getClass().getDeclaredFields()) {
        this.gUtilsAPI.getLogger().info("Name von einem Field - " + f.getName() + " - " + f.getType());
      }
      Field field = Minecraft.getMinecraft().getNetHandler().getNetworkManager().getClass().getDeclaredField(this.gUtilsAPI.isDebug() ? "channel" : "k");
      field.setAccessible(true);
      Channel channel = (Channel) field.get(Minecraft.getMinecraft().getNetHandler().getNetworkManager());
      ChannelPipeline pipeline = channel.pipeline();
      pipeline.addBefore("packet_handler", "listener", new ChannelDuplexHandler() {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          if(msg instanceof Packet) {
            Packet<?> packet = (Packet<?>) msg;
            IncomingPacketEvent_1_8 incomingPacketEvent = new IncomingPacketEvent_1_8((Packet<?>) msg);
            GUtilsAPI.EVENT_BUS.post(incomingPacketEvent);
            if(!incomingPacketEvent.isCancelled()) {
              super.channelRead(ctx, msg);
            }

            if(!(incomingPacketEvent.getPacket() instanceof S3FPacketCustomPayload)) {
              return;
            }

            S3FPacketCustomPayload s3FPacketCustomPayload = (S3FPacketCustomPayload) incomingPacketEvent.getPacket();

            GUtilsAPI.EVENT_BUS.post(new CustomPayloadEvent_1_8(s3FPacketCustomPayload.getChannelName(), s3FPacketCustomPayload.getBufferData()));
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
