#!/usr/bin/env wolframscript
tab = Import["uk-towns.csv"];

latitudes = tab[[2 ;; All, 8]];

longitudes = tab[[2 ;; All, 9]];

randomrow = RandomInteger[{2, 48207}];

centre = {tab[[randomrow, 8]], tab[[randomrow, 9]]};

Print[centre]

rawlist = 
  MapThread[
   GeoDistance[{centre[[1]], centre[[2]]}, {#1, #2}] &, {latitudes, 
    longitudes}];


lists = {rawlist, latitudes, longitudes};

sorlist = Quiet[Sort[rawlist]];

allmiles = 
  Quiet[UnitConvert[rawlist[[#]], "Miles"] & /@ Range[1, 48206]];

list2 = {allmiles, latitudes, longitudes};

sortlat = Quiet[list2[[All, Ordering@list2[[1]]]][[2]]];

sortlon = Quiet[list2[[All, Ordering@list2[[1]]]][[3]]];

milesInt = Quiet[First[sorlist[[#]]] & /@ Range[1, 48206]];

pos = Flatten[Position[milesInt, _?(20 < # < 30 &)]];

latfin = Take[sortlat[[#]] & /@ pos, 100];

lonfin = Take[sortlon[[#]] & /@ pos, 100];


points = Quiet[
   MapThread[
    TravelDirections[{GeoPosition[{centre[[1]], centre[[2]]}], 
       GeoPosition[{#1, #2}]}] &, {latfin, lonfin}]];

points = DeleteCases[points[[1 ;; All]], $Failed];

markerloc = 
  GeoGraphics[GeoMarker[GeoPosition[{centre[[1]], centre[[2]]}]]];

testmap = 
  DynamicGeoGraphics[Show[markerloc, 
   GeoGraphics[Style[Line[#], Thick, Red]] &@points[[2 ;; All]]]];

Export["20to30rangeroutes.nb", testmap];

