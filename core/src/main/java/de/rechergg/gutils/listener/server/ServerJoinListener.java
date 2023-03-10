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

package de.rechergg.gutils.listener.server;

import com.google.inject.Inject;
import de.rechergg.gutils.GUtilsAPI;
import de.rechergg.gutils.util.GrieferGamesServer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkLoginEvent;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import java.lang.reflect.Field;

/*
 * @created: 21.02.2023 - 13:41
 * @author: RECHERGG
 */
public class ServerJoinListener {

  private final GUtilsAPI gUtilsAPI;

  @Inject
  public ServerJoinListener(GUtilsAPI gUtilsAPI) {
    this.gUtilsAPI = gUtilsAPI;
  }

  @Subscribe
  public void onServerJoint(ServerLoginEvent event) {
    String server = event.serverData().actualAddress().toString().toLowerCase();
    if (server.endsWith("griefergames.net") || server.endsWith("griefergames.de")) {
      this.gUtilsAPI.getGrieferGamesServer().setOnGrieferGames(true);
    }
  }
}
