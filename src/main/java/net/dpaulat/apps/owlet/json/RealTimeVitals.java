/*
 * Copyright 2021 Dan Paulat
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

package net.dpaulat.apps.owlet.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RealTimeVitals {

    private Integer ox;
    private Integer hr;
    private Integer mv;
    private Integer sc;
    private Integer st;
    private Integer bso;
    private Integer bat;
    private Integer btt;
    private Integer chg;
    private Integer aps;
    private Integer alrt;
    private Integer ota;
    private Integer srf;
    private Integer rsi;
    private Integer sb;

    public RealTimeVitals() {
    }

    public Integer getOx() {
        return ox;
    }

    public void setOx(Integer ox) {
        this.ox = ox;
    }

    public Integer getHr() {
        return hr;
    }

    public void setHr(Integer hr) {
        this.hr = hr;
    }

    public Integer getMv() {
        return mv;
    }

    public void setMv(Integer mv) {
        this.mv = mv;
    }

    public Integer getSc() {
        return sc;
    }

    public void setSc(Integer sc) {
        this.sc = sc;
    }

    public Integer getSt() {
        return st;
    }

    public void setSt(Integer st) {
        this.st = st;
    }

    public Integer getBso() {
        return bso;
    }

    public void setBso(Integer bso) {
        this.bso = bso;
    }

    public Integer getBat() {
        return bat;
    }

    public void setBat(Integer bat) {
        this.bat = bat;
    }

    public Integer getBtt() {
        return btt;
    }

    public void setBtt(Integer btt) {
        this.btt = btt;
    }

    public Integer getChg() {
        return chg;
    }

    public void setChg(Integer chg) {
        this.chg = chg;
    }

    public Integer getAps() {
        return aps;
    }

    public void setAps(Integer aps) {
        this.aps = aps;
    }

    public Integer getAlrt() {
        return alrt;
    }

    public void setAlrt(Integer alrt) {
        this.alrt = alrt;
    }

    public Integer getOta() {
        return ota;
    }

    public void setOta(Integer ota) {
        this.ota = ota;
    }

    public Integer getSrf() {
        return srf;
    }

    public void setSrf(Integer srf) {
        this.srf = srf;
    }

    public Integer getRsi() {
        return rsi;
    }

    public void setRsi(Integer rsi) {
        this.rsi = rsi;
    }

    public Integer getSb() {
        return sb;
    }

    public void setSb(Integer sb) {
        this.sb = sb;
    }

    @Override
    public String toString() {
        return "RealTimeVitals{" +
                "ox=" + ox +
                ", hr=" + hr +
                ", mv=" + mv +
                ", sc=" + sc +
                ", st=" + st +
                ", bso=" + bso +
                ", bat=" + bat +
                ", btt=" + btt +
                ", chg=" + chg +
                ", aps=" + aps +
                ", alrt=" + alrt +
                ", ota=" + ota +
                ", srf=" + srf +
                ", rsi=" + rsi +
                ", sb=" + sb +
                '}';
    }
}
