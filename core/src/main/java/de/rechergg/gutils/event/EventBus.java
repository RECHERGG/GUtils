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

package de.rechergg.gutils.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.lang.reflect.Method;

/*
 * @created: 21.02.2023 - 13:27
 * @author: RECHERGG
 */
public class EventBus {

  private final Multimap<String, Object> eventListeners;

  public EventBus() {
    this.eventListeners = ArrayListMultimap.create();
  }

  public void register(Object o) {
    for (Method m : o.getClass().getMethods()) {
      if (!m.isAnnotationPresent(EventHandler.class)) {
        continue;
      }
      if (m.getParameterCount() != 1) {
        continue;
      }
      Class<?> parameterType = m.getParameterTypes()[0];

      this.eventListeners.put(parameterType.getCanonicalName(), o);
    }
  }

  public void unregister(Object o) {
    for (Method m : o.getClass().getMethods()) {
      if (!m.isAnnotationPresent(EventHandler.class)) {
        continue;
      }
      if (m.getParameterCount() != 1) {
        continue;
      }
      Class<?> parameterType = m.getParameterTypes()[0];
      this.eventListeners.remove(parameterType.getCanonicalName(), o);
    }
  }

  public <T> T post(T event) {
    for (Object listener : this.eventListeners.get(event.getClass().getCanonicalName())) {
      for (Method m : listener.getClass().getMethods()) {
        if (!m.isAnnotationPresent(EventHandler.class)) {
          continue;
        }
        if (m.getParameterCount() != 1) {
          continue;
        }
        Class<?> parameterType = m.getParameterTypes()[0];
        if (!parameterType.getCanonicalName().equals(event.getClass().getCanonicalName())) continue;

        try {
          m.invoke(listener, event);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return event;
  }

}
