# Range-Anxiety
======================================

Prerequisites:

Executing the .wls files require the latest version of Mathematica (11 or higher). 
* If you don't already have it installed, go to www.wolfram.com/mathematica/trial/ for a free trial(valid for 15 days).
* If you have Mathematica version 10 or below, you will need to upgrade to Mathematica 11.
* Mac OS X users will need to download the 'extras' package along with the main installation.
This executes the wolframscript from command prompt.
* For any other issues, go to www.support.wolfram.com/kb/49


Check installation:
$ wolframscript -v
* This returns the Mathematica version that has been installed on your device and enters the Mathematica kernel.  
* To exit the kernel, type
$ Quit

Download:
* Navigate to the directory where you want to download the range-anxiety folder.
* $ git clone https://github.com/Greennav/range-anxiety.git

Warm-up:
* Change your current working directory to range-anxiety.
* To make the .wls scripts executable, you need to set executable permissions. Run the commands given below.
$ chmod a+x nearest50routes.wls
$ chmod a+x nearest50poygon.wls
$ chmod a+x range.wls
$ chmod a+x mulpol.wls
$ chmod a+x rangeroutes.wls

* To see the function of each .wls file, read gsocrep.pdf

Execution:
* Please note that because of the large quantity of data, the scripts may take a while to produce an output. 
Please give the scripts sufficient time to run. 
To run each .wls file, type
$ ./nearest50routes.wls
$ ./nearest50poygon.wls
$ ./range.wls
$ ./mulpol.wls
$ ./rangeroutes.wls

* In each case, a .gif file is created in the range-anxiety folder.

