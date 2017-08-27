# range-anxiety

## Description
The range-anxiety project combines combinatorial optimisation along with orthodromic distance calculations in order to determine a set of coordinates at a chosen distance from the starting location. Assuming that our model of the Earth is a perfect sphere, this project uses the great - circle distances to calculate the shortest distance between any two points on the surface of the Earth. Such an assumption is motivated by the insignificant flattening of the Earth(about 1/300) and manifests itself through the [Haversine function](http://www.movable-type.co.uk/scripts/latlong.html) which regulates the selection of coordinates from the [Open Street Map](http://www.openstreetmap.org) database. Once this selection process is complete, a combinatorial optimisation algorithm  is applied on all the coordinates that fall within the required boundary and attempts to arrange them in a sorted circular order. The nearest-neighbour approach of the Travelling Salesman algorithm used in this context, reduces the risk of running into a non-convex polygon boundary and minimises the elimination of coordinates once they are are mapped onto a geographical region in the frontend. 

The range-anxiety repository has two main branches. The [master](https://github.com/Greennav/range-anxiety) branch returns a set of coordinates in a GeoJSON format and can by used by all map editors that follow the latitude/longitude format for drawing markers or polygons. The [OLformat](https://github.com/Greennav/range-anxiety/tree/OLformat) branch has been tailored to fit the requirements of the [GreenNav](https://github.com/Greennav) organisation and returns the coordinates in a longitude/latitude format. 

## Objective
Given initial Latitude and Longitude, or OSM Node Id, and range value, outputs a list of nodes forming the edges of a [polygon](https://gist.github.com/bfmags/6cd82eaf4270a9657ff7b1301e51d574) or a group of [markers](https://gist.github.com/bfmags/6a7ef7cfd080460e3c18578476bbcae4), with a rest endpoint for both requests.
If sucessful, the API will return a JSON Object following the [GeoJSON](http://geojson.org/) format specification.

See below for examples of how to use the API.

### Prerequisites
#### Install the latest version of JDK and Maven

### Setup

Clone and navigate to the directory range-anxiety.

```
$ mvn package
$ cd target
```

Move the Jordan.osm.pbf file to the target folder.

### Run

```$ java -jar range-1.0-SNAPSHOT.jar```

## Docker Setup

[Docker](https://www.docker.com/) allows packaging an application with all of its dependencies into a container.

```zsh
git clone https://github.com/Greennav/range-anxiety.git
cd range-anxiety
```

#### Build
We will start by building a Docker image for the application (```Dockerfile``` contains the command-line instructions).

```
docker build -t greennav-range-api .
```

##### Run
Finally we run the image as a container, making the Range API available at http://localhost:8111/.

```
docker run -d --rm -p 8111:8111 --name greennav-range-api greennav-range-api:latest
```

## Working Example

Examples below use Jordan.osm.pbf for the map data.
Returns valid JSON output that can be used directly in any map editor without any further rearrangement.

To get the polygon format, using either lat/lng or OSM Node Id parameters.

* ```http://localhost:8111/greennav/polygon?startlat=31.7239898&startlng=35.6429683&range=10.0```
* ```http://localhost:8111/greennav/polygon?startNode=3602680930&range=10.0```


To get the marker format, using either lat/lng or OSM Node Id parameters.

* ```http://localhost:8111/greennav/marker?startlat=31.7239898&startlng=35.6429683&range=10.0```
* ```http://localhost:8111/greennav/marker?startNode=3602680930&range=10.0```

## Path Parameters

| Parameter        | Description          
| -------------    |:-------------| 
| polygon          | Returns the set of coordinates in the [GeoJSON](http://geojson.org/) format used for drawing polygons. | 
| marker           | Returns the set of coordinates in the [GeoJSON](http://geojson.org/) format used for drawing markers.  |  
| startlat         | The latitude of the starting location.      | 
| startlng         | The longitude of the starting location.     | 
| startNode        | [OSM's](http://www.openstreetmap.org) unique coordinate identification.     | 
| range            | Returns the set of coordinates in the [GeoJSON](http://geojson.org/)        | 

For more details, read 'GSoC2017_Work_Product.md' available in the docs folder.
