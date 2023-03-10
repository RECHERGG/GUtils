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

package de.rechergg.gutils;

import de.rechergg.gutils.event.EventBus;
import de.rechergg.gutils.hudwidgets.ClearLagTextHudWidget;
import de.rechergg.gutils.hudwidgets.RedstoneStateTextHudWidget;
import de.rechergg.gutils.listener.mysterymod.MysteryModMessageListener;
import de.rechergg.gutils.listener.server.citybuild.JoinCityBuildListener;
import de.rechergg.gutils.util.GrieferGamesServer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.logging.Logging;
import java.lang.management.ManagementFactory;

/*
 * @created: 21.02.2023 - 13:34
 * @author: RECHERGG
 */
public class GUtilsAPI {

  private final Logging logger;
  private final LabyAPI labyAPI;
  private final GUtilsConfiguration gUtilsConfiguration;
  public static EventBus EVENT_BUS;
  private final GrieferGamesServer grieferGamesServer;
  private final ClearLagTextHudWidget clearLagTextHudWidget;

  public GUtilsAPI(Logging logger, LabyAPI labyAPI, GUtilsConfiguration configuration) {
    this.logger = logger;
    this.labyAPI = labyAPI;
    this.gUtilsConfiguration = configuration;

    HudWidgetRegistry registry = getLabyAPI().hudWidgetRegistry();
    RedstoneStateTextHudWidget redstoneState = new RedstoneStateTextHudWidget("redstone_state");
    this.clearLagTextHudWidget = new ClearLagTextHudWidget("clearlag", this);

    EVENT_BUS = new EventBus();

    this.grieferGamesServer = new GrieferGamesServer();

    registry.register(redstoneState);
    registry.register(clearLagTextHudWidget);

    GUtilsAPI.EVENT_BUS.register(new MysteryModMessageListener(this));
    GUtilsAPI.EVENT_BUS.register(new JoinCityBuildListener(this));
    GUtilsAPI.EVENT_BUS.register(redstoneState);
    GUtilsAPI.EVENT_BUS.register(clearLagTextHudWidget);
  }

  public GrieferGamesServer getGrieferGamesServer() {
    return grieferGamesServer;
  }

  public boolean isDebug() {
    String command = System.getProperty("sun.java.command");
    return !command.endsWith(".jar");
  }

  public Logging getLogger() {
    return logger;
  }

  public LabyAPI getLabyAPI() {
    return labyAPI;
  }

  public ClearLagTextHudWidget getClearLagTextHudWidget() {
    return clearLagTextHudWidget;
  }

  public GUtilsConfiguration getGUtilsConfiguration() {
    return gUtilsConfiguration;
  }
}
