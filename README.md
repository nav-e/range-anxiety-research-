# range-anxiety
Computation and visualization of the vehicle's range

Execute the following commands to get a JSON output that lists the nodes forming the edges of a polygon.
 
Clone and navigate to the directory range-anxiety.

$ mvn package

$ cd target

Move the Jordan.osm.pbf file to the target folder.

$ java -jar range-1.0-SNAPSHOT.jar

Working Example:

To get the polygon format,
navigate to 'http://localhost:8111/greennav/polygon?startlat=31.7239898&startlng=35.6429683&range=10.0' to get a valid JSON output that can be used directly in any map editor without any further rearrangement.

To get the marker format,
navigate to 'http://localhost:8111/greennav/marker?startlat=31.7239898&startlng=35.6429683&range=10.0' to get a valid JSON output that can be used directly in any map editor without any further rearrangement.

For more details, read 'results2.pdf'. 
