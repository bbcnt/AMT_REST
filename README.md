# AMT REST LAB #
## 2014 - Bignens Julien & B. Carvalho Bruno :uk:

#Warning#
This is the official guide to make our implementation of the AMT REST lab work. Follow this guide as strictly as possible, so it can work as it should.

## Introduction ##
The objective of this project is to emulate the behavior of a few sensors that create observations based on different values they collect (Internet Of Things style). We then want to use these observations to create "facts" that can be exploited by the users of this api. These facts can contain various informations. The next image is an example:

![Script](./images/Facts_extract.PNG "First Aspect")

For the sensor number 20, we have 3 facts, the first one is a fact counting the number of observations that were taken by this sensor on the date of 1970-01-16 (soooo long ago). The second one is a counter, showing how many observations were taken so far by this sensor. The thirs and last one is the same as the first one, except, this time we want this info for 2014-12-11 (not so long ago). And we can see that the 2 lat columns have the same "information", which is normal, the only observations that were taken, were taken this same day. So basically, everyday, a new fact will be created, counting the number of observations for this particular day.

**Note:** in the DATE_COUNTER observations, there will be other infos (min,max and average value), but this was just to have an easy example.

## Step 1
Enough babbling, let's start this. The first thing to do is to use the script dbScriptREST.sh to generate all the basic structure (glassfish domains, database, etc).