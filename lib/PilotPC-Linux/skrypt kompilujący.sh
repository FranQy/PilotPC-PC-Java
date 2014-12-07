gcc -c -fPIC clicker.cpp -o pilotpc-x64.o -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include -I/usr/include/ -lXtst -m64
gcc pilotpc-x64.o -shared -o libpilotpc-x64.so -lXtst -m64
gcc -c -fPIC clicker.cpp -o pilotpc.o -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include -I/usr/include/ -lXtst -m32
gcc pilotpc.o -shared -o libpilotpc.so -m32