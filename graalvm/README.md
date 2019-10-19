#Install GraalVM on Linux
Based on: https://gist.github.com/ricardozanini/fa65e485251913e1467837b1c5a8ed28
- Download and unpackage
```
wget https://github.com/oracle/graal/releases/download/vm-19.1.1/graalvm-ce-linux-amd64-19.1.1.tar.gz  -O /tmp/graalvm.tar.gz
tar -xvzf /tmp/graalvm.tar.gz 
mv graalvm-ce-19.1.1 /usr/lib/jvm/
ln -s /usr/lib/jvm/graalvm-ce-19.1.1 /usr/lib/jvm/graalvm
update-alternatives --install /usr/bin/java java /usr/lib/jvm/graalvm/bin/java 1
update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/graalvm/bin/javac 1
update-alternatives --install /usr/bin/native-image native-image /usr/lib/jvm/graalvm/bin/native-image 1
update-alternatives --set java /usr/lib/jvm/graalvm/bin/java
update-alternatives --set javac /usr/lib/jvm/graalvm/bin/javac
update-alternatives --set native-image /usr/lib/jvm/graalvm/bin/native-image
rm -f /tmp/graalvm.tar.gz
```
- Install native image tool
```
/usr/lib/jvm/graalvm-ce-19.1.1/bin/gu install native-image
```

- Set GRAALVM_HOME
```
echo 'export GRAALVM_HOME=/usr/lib/jvm/graalvm' > /etc/profile.d/setGRAALVM_HOME.sh
```
- Set JAVA_HOME
```jshelllanguage
echo 'export JAVA_HOME=/usr/lib/jvm/graalvm' > /etc/profile.d/setJAVA_HOME.sh
echo 'export PATH=$PATH:/usr/lib/jvm/graalvm/bin' > /etc/profile.d/addgraalvmtopath.sh

export PATH=$PATH:/usr/lib/jvm/graalvm/bin/gu
```

#High-performance modern Java
GraalVM is a Java VM and JDK based on HotSpot/OpenJDK, implemented in Java. It supports additional programming languages and execution modes, like ahead-of-time compilation of Java applications for fast startup and low memory footprint.

Easiest way to show how GraalVM works is by example and for this purpose we will use TopTen.java example from
https://github.com/chrisseaton/graalvm-ten-things

- GraalVM includes a javac compiler
```jshelllanguage
$ javac TopTen.java
```
- To run without the GraalVM JIT compiler can use the flag -XX:-UseJVMCICompiler. 
```jshelllanguage
$ time java -XX:-UseJVMCICompiler TopTen small.txt
and = 69
much = 66
far = 58
the = 39
less = 38
a = 35
this = 33
more = 33
that = 28
one = 27

real    0m0.204s
user    0m0.348s
sys    0m0.024s
```
- The java command included in GraalVM will automatically use GraalVM JIT compiler
```jshelllanguage
$ time java TopTen small.txt
and = 69
much = 66
far = 58
the = 39
less = 38
a = 35
this = 33
more = 33
that = 28
one = 27

real    0m0.150s
user    0m0.279s
sys    0m0.033s
```
- Compile ahead-of-time, to a native executable image, instead of compiling just-in-time at runtime
```jshelllanguage
$ native-image --no-server TopTen

#run the executable, lightning fast!
$ time ./topten small.txt
and = 69
much = 66
far = 58
the = 39
less = 38
a = 35
this = 33
more = 33
that = 28
one = 27

real    0m0.020s
user    0m0.004s
sys    0m0.005s
```