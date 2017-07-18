package com.rangeanxiety.app.entities;

public class Edge {

	/**
	 * The edge costs, usually describing the distance, time, or energy use.
	 */
	private double distance;

	/**
	 * The target vertex of this edge.
	 */
	private long to;

	public Edge(double distance, long to) {
		this.distance = distance;
		this.to = to;
	}

	public double getDistance() {
		return distance;
	}

	public long getTo() {
		return to;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
