# jpgm
Java library for Probabilistic Graphical Models

## How to use
Use the Node class to create a graphical model. A list of all nodes should be passed to the BayesianNetwork class. For now all the Expectation Maximization functions are a part of this class.
`iterate` function runs through one iteration of e-step and m-step. The estimated values for the missing data should be in the same path as the data file in a text file called _temp.txt_ .
Passing an argument to `iterate` will run the iterations that many times.

For the sprinkler model, after 2 iterations, the results obtained were as follows,

```
Rain : Winter
1	0	
--------------
0.8581947743467934	0.14180522565320666	
0.2317789291882556	0.7682210708117444	

Winter :
1	0	
--------------
0.421	0.579	

SlipperyRoad : Rain
1	0	
--------------
0.9662966700302724	0.033703329969727545	
0.33518334985133796	0.6648166501486621	

WetGrass : Sprinkler Rain
1	0	
--------------
0.8699719363891487	0.13002806361085126	
0.2439086945370608	0.7560913054629392	
0.14873979410720625	0.8512602058927937	
0.09947643979057591	0.900523560209424	

Sprinkler : Winter
1	0	
--------------
0.32707838479809975	0.6729216152019002	
0.8048359240069085	0.19516407599309155	
```

The Graph node names are presented, followed by their parents after ':', then followed by the distribution.