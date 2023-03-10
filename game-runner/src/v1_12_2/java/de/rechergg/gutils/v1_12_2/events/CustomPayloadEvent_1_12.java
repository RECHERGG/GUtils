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

package de.rechergg.gutils.v1_12_2.events;

import de.rechergg.gutils.event.Event;
import net.minecraft.network.PacketBuffer;

/*
 * @created: 25.02.2023 - 17:28
 * @author: RECHERGG
 */
public class CustomPayloadEvent_1_12 implements Event {

  private String channelName;
  private PacketBuffer packetBuffer;

  public CustomPayloadEvent_1_12(String channelName, PacketBuffer packetBuffer) {
    this.channelName = channelName;
    this.packetBuffer = packetBuffer;
  }

  public String getChannelName() {
    return channelName;
  }

  public CustomPayloadEvent_1_12 setChannelName(String channelName) {
    this.channelName = channelName;
    return this;
  }

  public PacketBuffer getPacketBuffer() {
    return packetBuffer;
  }

  public CustomPayloadEvent_1_12 setPacketBuffer(PacketBuffer packetBuffer) {
    this.packetBuffer = packetBuffer;
    return this;
  }

}
