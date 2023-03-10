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

import de.rechergg.gutils.event.EventHandler;
import de.rechergg.gutils.event.events.packet.redstone.RedstoneChangeEvent;
import de.rechergg.gutils.util.enums.RedstoneState;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;

/*
 * @created: 25.02.2023 - 21:21
 * @author: RECHERGG
 */
@SpriteTexture("redstone32x32.png")
public class RedstoneStateTextHudWidget extends TextHudWidget<TextHudWidgetConfig> {

  private TextLine state;
  @SpriteSlot()
  private Icon icon;

  public RedstoneStateTextHudWidget(String id) {
    super(id);
  }


  @Override
  public void load(TextHudWidgetConfig config) {
    super.load(config);

    this.state = super.createLine("Redstone", "Loading...");
    this.setIcon(this.icon);
  }

  @EventHandler
  public void onRedstoneChange(RedstoneChangeEvent event) {
    changeState(event.getRedstoneState());
  }

  public void changeState(RedstoneState redstoneState) {
    this.state.updateAndFlush(redstoneState.getName());
  }
}
