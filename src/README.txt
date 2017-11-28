This is a tcp chat application project for Computer Network class. 
Group member: Pu Fang(61871845), Jingmin Yu(16318281)
Contribution: Pu Fang: UI and messgae instructions
              Jingmin Yu: File instructions

Press start server to build a new and the only one server.
Press add client to create several clients.

Instruction format:

Connect with server:
connect [IP:PORT] [client name]  e.g. connect 127.0.0.1:1112 client1

Broadcast message:
broadcast msg [message]  e.g. broadcast msg sad sa d

Unicast message:
unicast msg [client name] [message]  e.g. unicast msg client1 sad as da

Blockcast message:
blockcast msg [client name] [message] e.g. blockcast msg client1 sad a 

Broadcast file:
broadcast file [relative path] e.g. broadcast file Client1/1.txt

Unicast file:
unicast file [client name] [relative path]  e.g. unicast file client1 Client1/1.txt

Disconnect with server:
disconnect or press "X" on windows.

TIPS: Different case of letters in instructions will bring difference. e.g. Connect is 
      differnet from connect.
      Everytime when "add client" pressed, the program will create new folder with 
      default name "Client i"(i=1,2,3......). When client receive a file, the file will
      be put in their folder.