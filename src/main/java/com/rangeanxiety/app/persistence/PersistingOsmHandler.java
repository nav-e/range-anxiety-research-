package com.rangeanxiety.app.persistence;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.Way;

import java.io.IOException;

public class PersistingOsmHandler implements OsmHandler {
    public int numNodes = 0;
    public Multimap<Long, Double> ver = ArrayListMultimap.create();
    private Persistence persistence;

    public PersistingOsmHandler(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void handle(OsmBounds bounds) throws IOException {
    }

    @Override
    public void handle(OsmNode node) throws IOException {
        numNodes++;
        persistence.writeNode((Node) node);
        ver.put(node.getId(), node.getLatitude());
        ver.put(node.getId(), node.getLongitude());
    }

    @Override
    public void handle(OsmWay way) throws IOException {
        persistence.writeWay((Way) way);
    }

    @Override
    public void handle(OsmRelation relation) throws IOException {
        persistence.writeRelation((Relation) relation);
    }

    @Override
    public void complete() throws IOException {
    }
}
