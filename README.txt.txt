Zadatak rjesen u tehnologijama koje su zadate.

Kako je navedeno u zadatku da se promgramski parametri zadaju preko komandne linije, u tu svrhu je koristen jCommander plugin.
Iako sam u pom.xml file-u kao dapendency naveo jcommander imao sam problema prilikom pokretanja programa. Jedno od rjesenja je
da sam jcommander .jar file sacuvao na putanji /src/main/resources/jcommander-1.7.jar i prilikom pokretanja programa treba se navesti putanja do ovog .jar file-a.
npr.: java -cp target/TCPPing-1.0.jar:src/main/resources/jcommander-1.7.jar Main -c -bind 127.0.0.1 -port 9900 (catcher)
	  java -cp target/TCPPing-1.0.jar:src/main/resources/jcommander-1.7.jar Main -p -port 9900 -mps 1 -size 50 localhost (pitcher)

Prilikom startanja programa potrebno je odabrati ulogu programa, PITCHER (-p) ili CATHCER (-c). Ukoliko se unesu neki durgi karakteri program ispisuje poruku.
 
Kao pitcher (-p) jedan od obaveznih parametara programa je i hostname racunara na kojem je pokrenut catcher.
Posto sam razvoj programa sam radio na linux masini, da bi program funkcionisao potrebno je u /etc/hosts file-u dodati ip adresu i hostname hosta koji ce imati ulogu catchera. Kako sam program testirao na lokalnom hosu, kao hostname sam proslijedio parametar localhost