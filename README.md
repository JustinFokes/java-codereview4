Band Tracker Application
By: Justin Fokes

Technologies Used

jUnit Fluentleniu Veloctiy Java HTML CSS

Setting Up Project

Clone this repository:

$ git clone https: https://github.com/JustinFokes/java-codereview4.git
$ cd java-codereview4
Open and run your SQL server

$ postgres
$ psql
Create band_tracker by following the steps below:

$ CREATE DATABASE band_tracker;
$ \c band_tracker;
$ CREATE TABLE bands (id serial PRIMARY KEY, name varchar, genre varchar, homeTown varchar);
$ CREATE TABLE venues (id serial PRIMARY KEY, name varchar, phone varchar, location varchar);
$ CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker;
Navigate back to the directory where this repository has been cloned, then run gradle:

$ gradle run

Open localhost://4567/ in your browser and enjoy : )


Legal

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.