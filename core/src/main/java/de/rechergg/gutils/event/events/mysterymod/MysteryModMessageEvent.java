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

package de.rechergg.gutils.event.events.mysterymod;

import de.rechergg.gutils.event.Event;

/*
 * @created: 25.02.2023 - 20:47
 * @author: RECHERGG
 */
public class MysteryModMessageEvent implements Event {

  protected String messageKey;
  protected String messageContent;

  public MysteryModMessageEvent(String messageKey, String messageContent) {
    this.messageKey = messageKey;
    this.messageContent = messageContent;
  }

  public String getMessageKey() {
    return messageKey;
  }

  public void setMessageKey(String messageKey) {
    this.messageKey = messageKey;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }
}
