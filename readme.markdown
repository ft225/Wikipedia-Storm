
Wikipedia-Storm is a web application able to compute in real time the last edits on Wikipedia.
It's given to the user the possibility of:
* view a representation of the most active users editing Wikipedia pages;
* view a representation of the most edited Wikipedia pages;

The application is realized with the programming languages : Java, Html, Javascript (in particular using [d3.js](http://d3js.org/) libraries).

## Running instructions
### Required
First of all you have to open the terminal and digit:
```
npm install http-server
npm install socket.io
```
These commands will install on your root directory some required libraries to run the Wikipedia-Storm application.

Then clone the project on your file system. 

## Running the server

Download and install node.js [here](http://nodejs.org/).
To run the server open the terminal,go into WebContent directory of the project and digit:

```
node server.js
```

## Running the client

Check your Java version is Java7 by the command:
```
java -version
```
If not, download it from [here](http://www.java.com/it/download/).


Then open a new shell, go into the Wikipedia-Storm directory and start the jar with the following command:

```
java -jar WikiSunset.jar
```
It will open a window containing a link to the Wikipedia-Storm homepage.

## Bugs

The application works fine on Mozilla Firefox and Safari, it doesn't work on Google Chrome browser.

## Authors

* Matteo Pisani
* Fabiana Alessandro




