gcc -c -fPIC clicker.cpp -o pilotpc-x64.o -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include -lXtst
gcc pilotpc-x64.o -shared -o pilotpc-x64.so -lXtst
gcc -c -fPIC clicker.cpp -o pilotpc.o -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include -lXtst --target=i386
gcc pilotpc.o -shared -o pilotpc.so -lXtst --target=i386