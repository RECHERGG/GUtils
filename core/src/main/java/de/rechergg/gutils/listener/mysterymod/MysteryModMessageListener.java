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

package de.rechergg.gutils.listener.mysterymod;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rechergg.gutils.GUtilsAPI;
import de.rechergg.gutils.event.EventHandler;
import de.rechergg.gutils.event.events.mysterymod.MysteryModMessageEvent;
import de.rechergg.gutils.event.events.packet.clearlag.ClearLagMessageEvent;
import de.rechergg.gutils.event.events.packet.redstone.RedstoneChangeEvent;
import de.rechergg.gutils.util.enums.RedstoneState;
import java.util.concurrent.TimeUnit;

/*
 * @created: 25.02.2023 - 20:47
 * @author: RECHERGG
 */
public class MysteryModMessageListener {

  private final GUtilsAPI gUtilsAPI;
  private final JsonParser parser = new JsonParser();

  public MysteryModMessageListener(GUtilsAPI gUtilsAPI) {
    this.gUtilsAPI = gUtilsAPI;
  }

  @EventHandler
  public void onMessage(MysteryModMessageEvent event) {
    this.gUtilsAPI.getLogger().info("MysteryMod Message: " + event.getMessageKey() + " - " + event.getMessageContent());

    JsonElement message;
    try {
      message = parser.parse(event.getMessageContent());
    } catch(Exception err) {
      System.err.println("Error while parsing MysteryMod message: "+err.getMessage());
      return;
    }


    if (event.getMessageKey().equals("redstone")) {
      String redstone = message.getAsJsonObject().get("status").getAsString();
      RedstoneState redstoneState;
      if (redstone.equals("0")) {
        redstoneState = RedstoneState.REDSTONE_STATE_ON;
      } else {
        redstoneState = RedstoneState.REDSTONE_STATE_OFF;
      }
      GUtilsAPI.EVENT_BUS.post(new RedstoneChangeEvent(redstoneState));
    }

    if (event.getMessageKey().equals("countdown_create")) {
      JsonObject countdown = message.getAsJsonObject();
      String name = countdown.get("name").getAsString();
      TimeUnit timeUnit = TimeUnit.valueOf(countdown.get("unit").getAsString());
      long until = countdown.get("until").getAsLong();
      if(name.equals("ClearLag")) {
        GUtilsAPI.EVENT_BUS.post(new ClearLagMessageEvent(System.currentTimeMillis() + timeUnit.toMillis(until)));
      }
    }
  }
}
