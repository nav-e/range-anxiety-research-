package com.rangeanxiety.app.helper;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;



public class HaversineTest
{
@Autowired
Haversine haversine;
@Before
public void setUp() {
haversine = new Haversine();
}


@Test
public void testHaversineResult()
{
double distance = 1310.9648493934997 ;
double result;
result=haversine.Havdistance(32.5407621,35.8447713,26.3047069000,56.4520605);
assertEquals(distance, result,0.1);
}

}