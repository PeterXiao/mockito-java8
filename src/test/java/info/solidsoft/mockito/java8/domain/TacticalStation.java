/*
 * Copyright (C) 2015 Marcin Zajączkowski.
 *
 * Licensed under the Apache License, Version 2.0.
 */
package info.solidsoft.mockito.java8.domain;

/**
 * Test domain classes.
 */
public class TacticalStation {

    public enum TubeStatus {
        EMPTY, LOADING, LOADED, UNLOADING, BROKEN
    }

    public int getNumberOfEnemyShipsInRange() {
        return 0;
    }

    public void fireTorpedo() {
        fireTorpedo(getNumberOfFirstLoadedTube());
    }

    public void fireTorpedo(int tubeNumber) {
    }

    private int getNumberOfFirstLoadedTube() {
        return 0;
    }

    public int getNumberOfTubes() {
        return 0;
    }

    public void reloadTubeWithGivenTorpedoType(int tubeNumber, TopedoType topedoType) {
    }

    public int getNumberOfRemainingTorpedos() {
        return 0;
    }

    public TubeStatus getTubeStatus(int tubeNumber) {
        return null;
    }

    public int smellyFindNumberOfShipsInRangeByCriteria(int minimumRange, String partOfName, int numberOfPhasers) {
        return 0;
    }

    public int findNumberOfShipsInRangeByCriteria(ShipSearchCriteria searchCriteria) {
        return 0;
    }

    public void firePhaser() {
    }

    public void doSelfCheck() {
    }
}
