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

package de.rechergg.gutils.util;


import de.rechergg.gutils.util.enums.CityBuild;

/*
 * @created: 21.02.2023 - 19:17
 * @author: RECHERGG
 */
public class GrieferGamesServer {

  private boolean onGrieferGames;
  private boolean onCityBuild;
  private CityBuild cityBuild;

  public boolean isOnGrieferGames() {
    return onGrieferGames;
  }

  public boolean isOnCityBuild() {
    return onCityBuild;
  }

  public void setOnGrieferGames(boolean onGrieferGames) {
    this.onGrieferGames = onGrieferGames;
  }

  public void setOnCityBuild(boolean onCityBuild) {
    this.onCityBuild = onCityBuild;
  }

  public CityBuild getCityBuild() {
    return cityBuild;
  }

  public void setCityBuild(CityBuild cityBuild) {
    this.cityBuild = cityBuild;
  }
}
