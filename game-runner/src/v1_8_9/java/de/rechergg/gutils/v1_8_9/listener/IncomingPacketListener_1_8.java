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
import de.rechergg.gutils.event.EventHandler;
import de.rechergg.gutils.util.enums.CityBuild;
import de.rechergg.gutils.util.enums.ModColor;
import de.rechergg.gutils.v1_8_9.events.IncomingPacketEvent_1_8;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.scoreboard.Score;
import java.util.Timer;
import java.util.TimerTask;

/*
 * @created: 27.02.2023 - 15:54
 * @author: RECHERGG
 */
public class IncomingPacketListener_1_8 {

  private final GUtilsAPI gUtilsAPI;

  public IncomingPacketListener_1_8(GUtilsAPI gUtilsAPI) {
    this.gUtilsAPI = gUtilsAPI;
  }

  @EventHandler
  public void onIncomingPacket(IncomingPacketEvent_1_8 event) {
    if(!(event.getPacket() instanceof S3EPacketTeams)) {
      return;
    }

    S3EPacketTeams scoreboardPacket = (S3EPacketTeams) event.getPacket();
    String name = scoreboardPacket.getName();
    String value = scoreboardPacket.getPrefix();

    //this.gUtilsAPI.getLogger().info("Packet: " + name + " - " + value);

    if(name.equals("server_value")) {
      value = ModColor.removeColor(value).toLowerCase();
      if(value.trim().equals("") || value.contains("lade")) return;
      if (this.gUtilsAPI.getGrieferGamesServer().getCityBuild() == null) {
        this.gUtilsAPI.getGrieferGamesServer().setCityBuild(CityBuild.valueOf(value.toUpperCase()));

        if (value.toUpperCase().equals(CityBuild.PORTAL.name()) || value.toUpperCase().equals(CityBuild.LOBBY.name())) {
          this.gUtilsAPI.getGrieferGamesServer().setOnCityBuild(false);
          return;
        }

        this.gUtilsAPI.getGrieferGamesServer().setOnCityBuild(true);
        return;
      }

      if(this.gUtilsAPI.getGrieferGamesServer().getCityBuild().name().equals(value.toUpperCase())) {
        return;
      }

      this.gUtilsAPI.getGrieferGamesServer().setCityBuild(CityBuild.valueOf(value.toUpperCase()));

      if (value.toUpperCase().equals(CityBuild.PORTAL.name()) || value.toUpperCase().equals(CityBuild.LOBBY.name())) {
        this.gUtilsAPI.getGrieferGamesServer().setOnCityBuild(false);
        return;
      }

      this.gUtilsAPI.getGrieferGamesServer().setOnCityBuild(true);
    }
  }
}
