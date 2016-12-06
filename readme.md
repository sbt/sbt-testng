# sbt-testng-interface - Testing via TestNG in sbt

This is an implementation of the [sbt test interface](https://github.com/sbt/test-interface) for testing with **[TestNG](http://testng.org)**.

If you're developing in Scala, you can use [Specs2](http://specs2.org) and be happy. However, if you're sentenced to Java, TestNG is a very good alternative to JUnit.

## Usage

Thanks to @asflierl, there is now a convenience sbt plugin which greatly simplifies configuring the testng test interface.

Version 3.0.3 works with sbt 0.13.13.

Add the following to your project's `plugins.sbt` file:

```scala
addSbtPlugin("de.johoop" % "sbt-testng-plugin" % "3.0.3")
```

Add the following to your project's `build.sbt` file:

```scala
import de.johoop.testngplugin.TestNGPlugin._

testNGSettings
```

You can configure TestNG via the settings keys below.

When done, run your tests in sbt as usual via **`sbt test`**.

## Settings

### `testNGVersion`

* *Description:* Version of TestNG to use for the tests.
* *Accepts:* `String`
* *Default:* `"6.9.13.6"`

### `testNGOutputDirectory`

* *Description:* Where TestNG stores its test result files.
* *Accepts:* `String`
* *Default:* `"target/testng"`

### `testNGParameters`

* *Description:* Additional TestNG parameters.
* *Accepts:* `Seq[String]`
* *Default:* `Seq()`

### `testNGSuites`

* *Description:* TestNG test suite file paths (yaml or xml).
* *Accepts:* `Seq[String]`
* *Default:* `Seq("src/test/resources/testng.yaml")`

## Note

TestNG uses its own test runner wich works in a very different way compared to the one from sbt. This means that the interface implementation is kind of a hack.

This also means that executing single tests via**`sbt test-only`** won't work. Please use the options of TestNG instead.

## License

Copyright (c) 2011-2017 Joachim Hofer & contributors
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The names of the authors may not be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHORS ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
