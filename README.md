# SITE As Template

Ecommence web system using Vert.x Java, MongoDB and React.js. 
Currently WIP, is heavily under development and unstable.


![](/screenshot/screenshot.png)

## DEMO

clone or download/unzip then run
```
gulp
```

and then 
```
mvn clean install -DskipTests
```

to run website, use: (with remote debugger)
```
java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 target/site-1.0-SNAPSHOT-fat.jar -conf src/main/resources/application-conf.json
```

## Development

### Build and watch.

```
gulp watch
```

### initialize mongoDB

```
mongod -dbpath data
```

### Start app.


### Test app.


## License

The MIT License (MIT)

Copyright (c) 2015 @Bokuweb

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
