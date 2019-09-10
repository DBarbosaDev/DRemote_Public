# Version 1

import socket
import time

UDP_IP_ADDRESS = "192.168.8.102"
UDP_PORT = 8888
MESSAGE_TO_UNLOCK = "password"

serverSock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

serverSock.bind((UDP_IP_ADDRESS, UDP_PORT))

def resp(error, status = ''):
    UDP_RESP_MESSAGE={}
    
    if(bool(error) and status == ''):
        UDP_RESP_MESSAGE["error"] = 'true'
        UDP_RESP_MESSAGE["message"] = 'Incorrect Message.'
    else:
        UDP_RESP_MESSAGE["error"] = 'false'
        UDP_RESP_MESSAGE["door_command"] = status

    return str(UDP_RESP_MESSAGE)
    

while True:
    data, addr = serverSock.recvfrom(1024)
    data = data.decode("utf-8")
    	
    print ("Message: ", data)
    print ("Addr: ", addr)
	
    if data == MESSAGE_TO_UNLOCK:
    
        srvResponse = resp("false", "open")
        serverSock.sendto(bytes(srvResponse, "utf-8"), addr)
        print(srvResponse)
        
        time.sleep(5)

        srvResponse = resp("false", "close")
        serverSock.sendto(bytes(srvResponse, "utf-8"), addr)
        print(srvResponse)
        
    else:
        srvResponse = resp("true")
        serverSock.sendto(bytes(srvResponse, "utf-8"), addr)
        print(srvResponse)
    
