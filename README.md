# range-anxiety
Computation and visualization of the vehicle's range

# Execute the following commands to get a JSON output that lists the nodes forming the edges of a polygon.
 
Clone and navigate to the directory range-anxiety.

$ mvn package

$ cd target

Download a '.osm.pbf' file from 'http://download.geofabrik.de', rename the file to 'test.osm.pbf' and place it in the target directory.

$ java -jar range-1.0-SNAPSHOT.jar

To get the polygon format,
navigate to 'localhost:8111/greennav/range/polygon' to get a valid JSON output that can be used directly in any map editor without any further rearrangement.

To get the marker format,
navigate to 'localhost:8111/greennav/range/marker' to get a valid JSON output that can be used directly in any map editor without any further rearrangement.

For more details, read 'gsoc2.pdf'. 