# Version 1

import socket
import time

UDP_IP_ADDRESS = "192.168.8.102"
UDP_PORT = 8888
MESSAGE_TO_UNLOCK = "password"

serverSock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

serverSock.bind((UDP_IP_ADDRESS, UDP_PORT))

def __setResponseMsg(object, error, status, errbool , message):
    if(bool(error) and status == ""):
        UDP_RESP_MESSAGE["error"] = str(errbool)
        UDP_RESP_MESSAGE["message"] = message
    else:
        UDP_RESP_MESSAGE["error"] = str(errbool)
        UDP_RESP_MESSAGE["door_command"] = status

def __sendToADDR(ADDR, status):
    srvResponse = resp("false", status)
    serverSock.sendto(bytes(srvResponse, "utf-8"), ADDR)
    return srvResponse

def resp(error, status = ''):
    UDP_RESP_MESSAGE={}

    __setResponseMsg(UDP_RESP_MESSAGE, error, status, TRUE, "Incorrect message")

    return str(UDP_RESP_MESSAGE)
    

while True:
    data, addr = serverSock.recvfrom(1024)
    data = data.decode("utf-8")
    	
    print ("Message: ", data)
    print ("Addr: ", addr)
	
    if data == MESSAGE_TO_UNLOCK:
        srvResponse = __sendToADDR(addr, "open")
        print(srvResponse)
        
        time.sleep(5)

        srvResponse = __sendToADDR(addr, "close")
        print(srvResponse)
    else:
        srvResponse = resp("true")
        serverSock.sendto(bytes(srvResponse, "utf-8"), addr)
        print(srvResponse)
    
