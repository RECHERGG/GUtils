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

package de.rechergg.gutils.hudwidgets;

import de.rechergg.gutils.GUtilsAPI;
import de.rechergg.gutils.event.EventHandler;
import de.rechergg.gutils.event.events.packet.clearlag.ClearLagEvent;
import de.rechergg.gutils.event.events.packet.clearlag.ClearLagMessageEvent;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import java.util.concurrent.TimeUnit;

/*
 * @created: 25.02.2023 - 21:54
 * @author: RECHERGG
 */
public class ClearLagTextHudWidget extends TextHudWidget<TextHudWidgetConfig> {

  private final GUtilsAPI gUtilsAPI;
  private TextLine time;
  private int remainingTime;
  private int x = 0;

  public ClearLagTextHudWidget(String id, GUtilsAPI gUtilsAPI) {
    super(id);

    this.gUtilsAPI = gUtilsAPI;
  }

  @Override
  public void load(TextHudWidgetConfig config) {
    super.load(config);

    this.time = super.createLine("ClearLag", "Loading...");
  }

  @Override
  public void onTick() {
    this.x++;

    if (this.remainingTime <= 20) {
      GUtilsAPI.EVENT_BUS.post(new ClearLagEvent(this.remainingTime));
    }

    if (this.remainingTime > 20) {
      if (this.x == 20) {
        this.remainingTime--;
        changeTime(
            String.format("%02d:%02d", this.remainingTime / 60, this.remainingTime % 60));
        this.x = 0;
      }
      return;
    }

    if (this.x == 20) {
      if (remainingTime > 0) {
        this.remainingTime--;
        changeTime((this.remainingTime % 60) + "s");
      } else {
        changeTime("0s");
      }
      this.x = 0;
    }
  }

  @EventHandler
  public void onClearLag(ClearLagMessageEvent event) {
    final long now = System.currentTimeMillis();
    this.remainingTime = (int) TimeUnit.MILLISECONDS.toSeconds(event.getTime() - now);

    if (this.remainingTime <= 0) {
      changeTime((this.remainingTime % 60) + "s");
      return;
    }

    changeTime(
        String.format("%02d:%02d", (this.remainingTime % 3600) / 60,
            (this.remainingTime % 60)));
  }

  public void changeTime(String time) {
    this.time.updateAndFlush(time);
  }
}
