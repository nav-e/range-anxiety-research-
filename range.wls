#!/usr/bin/env wolframscript
(* ::Package:: *)



tab=Import["/Users/leahchrestien/routes/uk-towns.csv"];



latitudes=tab[[2;;All,8]];


longitudes=tab[[2;;All,9]];



randomrow=RandomInteger[{2,48207}];



centre={tab[[randomrow,8]],tab[[randomrow,9]]};



Print[centre]



rawlist=MapThread[GeoDistance[{centre[[1]],centre[[2]]},{#1,#2}]&,{latitudes,longitudes}];

lists={rawlist,latitudes,longitudes};



sorlist=Quiet[Sort[rawlist]];


allmiles=Quiet[UnitConvert[rawlist[[#]],"Miles"]&/@Range[1,48206]];



list2={allmiles,latitudes,longitudes};



sortlat=Quiet[list2[[All,Ordering@list2[[1]]]][[2]]];



sortlon=Quiet[list2[[All,Ordering@list2[[1]]]][[3]]];



milesInt=Quiet[First[sorlist[[#]]]&/@Range[1,48206]];



pos=Flatten[Position[milesInt,_?(20<#<30&)]];



latfin=Take[sortlat[[#]]&/@pos,100];



lonfin=Take[sortlon[[#]]&/@pos,100];



centres=MapThread[GeoPosition[{#1,#2}]&,{latfin,lonfin}];



{dist,route}=Quiet[FindShortestTour[centres]];


listt={latfin[[route[[#]]]],lonfin[[route[[#]]]]}&/@Range[1,100];



polygon=Polygon[GeoPosition[listt]];



markerloc=GeoGraphics[GeoMarker[GeoPosition[{centre[[1]],centre[[2]]}]]];


testpolygon=DynamicGeoGraphics[Show[markerloc,GeoGraphics[{EdgeForm[Red],FaceForm[Red],polygon}]]];



Export["range.nb",testpolygon];

