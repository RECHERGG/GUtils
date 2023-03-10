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

package de.rechergg.gutils.v1_8_9;

import de.rechergg.gutils.GUtilsAPI;
import de.rechergg.gutils.GUtilsConfiguration;
import de.rechergg.gutils.v1_8_9.listener.ClearLagListener_1_8;
import de.rechergg.gutils.v1_8_9.listener.CustomPayloadListener_1_8;
import de.rechergg.gutils.v1_8_9.listener.IncomingPacketListener_1_8;
import de.rechergg.gutils.v1_8_9.listener.ServerJoinListener_1_8;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.logging.Logging;

/*
 * @created: 25.02.2023 - 17:44
 * @author: RECHERGG
 */
@AddonMain
public class GUtils_1_8 extends LabyAddon<GUtilsConfiguration> {

  private GUtilsAPI gUtilsAPI;

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.gUtilsAPI = new GUtilsAPI(this.getLogger(), this.labyAPI(), this.configuration());

    this.registerListener(new ServerJoinListener_1_8(this.gUtilsAPI));
    this.registerListener(this.gUtilsAPI.getClearLagTextHudWidget());

    GUtilsAPI.EVENT_BUS.register(new CustomPayloadListener_1_8(this.gUtilsAPI));
    GUtilsAPI.EVENT_BUS.register(new ClearLagListener_1_8(this.gUtilsAPI));
    GUtilsAPI.EVENT_BUS.register(new IncomingPacketListener_1_8(this.gUtilsAPI));
  }

  @Override
  protected Class<? extends GUtilsConfiguration> configurationClass() {
    return GUtilsConfiguration.class;
  }

  public Logging getLogger() {
    return this.logger();
  }

  public GUtilsAPI getGUtilsAPI() {
    return gUtilsAPI;
  }
}
