#!/usr/bin/env wolframscript
(* ::Package:: *)

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

sortlat = Take[lists[[All, Ordering@lists[[1]]]][[2]], 50];



sortlon = Take[lists[[All, Ordering@lists[[1]]]][[3]], 50];

points = Quiet[
   MapThread[
    TravelDirections[{GeoPosition[{centre[[1]], centre[[2]]}], 
       GeoPosition[{#1, #2}]}] &, {sortlat, sortlon}]];

points = DeleteCases[points[[1;;All]], $Failed];

markerloc = 
 GeoGraphics[GeoMarker[GeoPosition[{centre[[1]], centre[[2]]}]]];

testmap = Show[markerloc,GeoGraphics[Style[Line[#],Thick,Red]]&@points[[2;;All]]];

Export["nearest50routes.gif",testmap];
