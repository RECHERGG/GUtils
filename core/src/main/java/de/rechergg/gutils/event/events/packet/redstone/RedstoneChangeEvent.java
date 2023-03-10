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

package de.rechergg.gutils.event.events.packet.redstone;

import de.rechergg.gutils.event.Event;
import de.rechergg.gutils.util.enums.RedstoneState;

/*
 * @created: 25.02.2023 - 21:33
 * @author: RECHERGG
 */
public class RedstoneChangeEvent implements Event {

  protected RedstoneState redstoneState;

  public RedstoneChangeEvent(RedstoneState redstoneState) {
    this.redstoneState = redstoneState;
  }

  public RedstoneState getRedstoneState() {
    return redstoneState;
  }

  public void setRedstoneState(RedstoneState redstoneState) {
    this.redstoneState = redstoneState;
  }
}
