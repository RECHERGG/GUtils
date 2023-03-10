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

package de.rechergg.gutils.listener.chat;

import de.rechergg.gutils.GUtilsAPI;
import de.rechergg.gutils.event.events.citybuild.JoinCityBuildEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

/*
 * @created: 23.02.2023 - 16:53
 * @author: RECHERGG
 */
public class ServerChatMessageListener {

  private final GUtilsAPI gUtilsAPI;

  public ServerChatMessageListener(GUtilsAPI gUtilsAPI) {
    this.gUtilsAPI = gUtilsAPI;
  }

  @Subscribe
  public void onMessage(ChatReceiveEvent event) {

    if (event.chatMessage().getFormattedText().equals("§r§8[§r§6GrieferGames§r§8] §r§aDeine Daten wurden vollständig heruntergeladen.§r")) {
      GUtilsAPI.EVENT_BUS.post(new JoinCityBuildEvent());
    }
  }
}
