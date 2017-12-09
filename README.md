# kafkawatcher
(requirement) SBT , scala, kafka, Linux( to integrate with MapD) 

_-Focus_

A tool that would read CS ticket from Kafka and insert it into MapD

_-External library needed :_

   cs_analytics jar file ( I included it in the Lib folder)

_-Building Executables_
   
    Go to the folder where build.sbt file is present and run the following commands 
```sbt compile
sbt universal:packageZipTarball

**If you want to check the code is running you run the 'sbt run' command with these two parameters arg1 = (number of hours before current), arg2 = (specific consumer group). Both are optional and default value will give you a new consumer group with last 12 hours of data.
```
_-MapD config_

in mapD create a new table.
```
DROP TABLE csticket_stream;
CREATE TABLE IF NOT EXISTS csticket_stream
     (
    ticket_id INT,
    game TEXT ENCODING DICT(8),
    is_approved BOOLEAN,
    is_reviewed BOOLEAN,
    status TEXT ENCODING DICT(8),
    ticket_type TEXT ENCODING DICT(8),
    timestamp TIMESTAMP ENCODING FIXED(32),
    player TEXT,
    player_email TEXT,
    comment TEXT,
    subject TEXT,
    language TEXT ENCODING DICT(16),
    issue_type TEXT ENCODING DICT(8),
    client_id TEXT,
    version TEXT ENCODING DICT(16),
    device TEXT ENCODING DICT(16),
    platform TEXT ENCODING DICT(8),
    os TEXT ENCODING DICT(8),
    country TEXT ENCODING DICT(8),
    star_rating TEXT ENCODING DICT(8)
    );
```
_-Running_

Open the sh file and change the path of mapd and path of kafkawatcher exec file 

Simple , Just execute kafkasys.sh file (please provide command line argument)
TODO:
-Change command line to getOpts as both are optional parameter 
-Number of hours should be provided as command line argument

`./kafkawatcher.exec 18`






