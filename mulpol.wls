#!/usr/bin/env wolframscript



tab=Import["uk-towns.csv"];



latitudes=tab[[2;;All,8]];



longitudes=tab[[2;;All,9]];



randomrow=RandomInteger[{2,48207}];



centre={tab[[randomrow,8]],tab[[randomrow,9]]};



Print[centre]



rawlist=MapThread[GeoDistance[{centre[[1]],centre[[2]]},{#1,#2}]&,{latitudes,longitudes}];
(**)



lists={rawlist,latitudes,longitudes};



sorlist=Quiet[Sort[rawlist]];



allmiles=Quiet[UnitConvert[rawlist[[#]],"Miles"]&/@Range[1,48206]];



list2={allmiles,latitudes,longitudes};



sortlat=Quiet[list2[[All,Ordering@list2[[1]]]][[2]]];



sortlon=Quiet[list2[[All,Ordering@list2[[1]]]][[3]]];



milesInt=Quiet[First[sorlist[[#]]]&/@Range[1,48206]];



markerloc=GeoGraphics[GeoMarker[GeoPosition[{centre[[1]],centre[[2]]}]]];



pos1=Flatten[Position[milesInt,_?(20<#<30&)]];



latfin1=Take[sortlat[[#]]&/@pos1,100];



lonfin1=Take[sortlon[[#]]&/@pos1,100];




centres=MapThread[GeoPosition[{#1,#2}]&,{latfin1,lonfin1}];



{dist,route}=Quiet[FindShortestTour[centres]];



listt1={latfin1[[route[[#]]]],lonfin1[[route[[#]]]]}&/@Range[1,100];



poly1=Polygon[GeoPosition[listt1]];



pos2=Flatten[Position[milesInt,_?(40<#<50&)]];


latfin2=Take[sortlat[[#]]&/@pos2,100];



lonfin2=Take[sortlon[[#]]&/@pos2,100];



centres2=MapThread[GeoPosition[{#1,#2}]&,{latfin2,lonfin2}];



{dist2,route2}=Quiet[FindShortestTour[centres2]];



listt2={latfin2[[route2[[#]]]],lonfin2[[route2[[#]]]]}&/@Range[1,100];



poly2=Polygon[GeoPosition[listt2]];



pos3=Flatten[Position[milesInt,_?(60<#<70&)]];



latfin3=Take[sortlat[[#]]&/@pos3,100];



lonfin3=Take[sortlon[[#]]&/@pos3,100];



centres3=MapThread[GeoPosition[{#1,#2}]&,{latfin3,lonfin3}];



{dist3,route3}=Quiet[FindShortestTour[centres3]];



listt3={latfin3[[route3[[#]]]],lonfin3[[route3[[#]]]]}&/@Range[1,100];



poly3=Polygon[GeoPosition[listt3]];



testmap=DynamicGeoGraphics[Show[markerloc,GeoGraphics[{EdgeForm[Red],FaceForm[Red],poly1}],GeoGraphics[{EdgeForm[Blue],FaceForm[Blue],poly2}],GeoGraphics[{EdgeForm[Green],FaceForm[Green],poly3}]]];


Export["mulpoly.nb",testmap];
