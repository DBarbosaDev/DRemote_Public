# DRemote
### What is DRemote?
- DRemote it's a smart door lock that is controlled by a Native Mobile Application. The door locker is built in a raspberry pi.
 This device lock and unlock the door depending of the password sended by client(Android App). 

### Why?
- My objective with that project it's to improve my skills on android & java and create something 
that will be useful to me or someone else.

### What is not completed?
- The first version of mobile app is already completed, however 
I need to finish the electronic of the door lock and the file that will control the actions.
## Build
### Python Server
In order to start the core part of the project, you must build and run the Raspberry Pi Server.
1st Edit IP Address (to local IPv4):
```
UDP_IP_ADDRESS = "<your_ip>"
```
2nd Create rule pointing to the file, allowing communication between server/client.
3rd Run with admin privileges:
```
python3 dremote_server.py
```

### APK
To install the application under your android device, just download and install the file inside the DRemote Apk folder.

## Technologies?
### Languages
- Raspberry Pi server: Python
- Android: Java
### Development Tools
- Android Studio to develop the mobile application, Visual Studio Code to dev the python server 
and to quick changes on python file notepad++.

---

**For more information, please feel free to contact me by diogobarbosa1@outlook.pt**
