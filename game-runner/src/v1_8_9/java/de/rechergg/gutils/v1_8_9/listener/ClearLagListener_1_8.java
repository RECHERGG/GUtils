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
import de.rechergg.gutils.event.events.packet.clearlag.ClearLagEvent;
import de.rechergg.gutils.util.enums.ClearLagNotification;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

/*
 * @created: 27.02.2023 - 14:08
 * @author: RECHERGG
 */
public class ClearLagListener_1_8 {

  private final GUtilsAPI gUtilsAPI;
  private final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

  public ClearLagListener_1_8(GUtilsAPI gUtilsAPI) {
    this.gUtilsAPI = gUtilsAPI;
  }

  @EventHandler
  public void onClearLag(ClearLagEvent event) {
    if (!this.gUtilsAPI.getGrieferGamesServer().isOnCityBuild()) {
      return;
    }

    if (this.gUtilsAPI.getGUtilsConfiguration().getNotificationType().get().equals(ClearLagNotification.DISPLAY)) {
      Minecraft.getMinecraft().ingameGUI.displayTitle("§cClearlag!", null, -1, -1, -1);
      Minecraft.getMinecraft().ingameGUI.displayTitle(null, event.getTime() + "s", -1, -1, -1);
      Minecraft.getMinecraft().ingameGUI.displayTitle(null, null, 0, 2, 3);
    }

    if (this.gUtilsAPI.getGUtilsConfiguration().getNotificationType().get().equals(ClearLagNotification.DISPLAY_AND_SOUND)) {
      Minecraft.getMinecraft().ingameGUI.displayTitle("§cClearlag!", null, -1, -1, -1);
      Minecraft.getMinecraft().ingameGUI.displayTitle(null, event.getTime() + "s", -1, -1, -1);
      Minecraft.getMinecraft().ingameGUI.displayTitle(null, null, 0, 2, 3);
      if (event.getTime() == 20) {
        Minecraft.getMinecraft().thePlayer.playSound("note.pling", 10, 10);
      }
    }
    }
  }
