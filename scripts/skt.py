#!/usr/bin/env python3

import socket
import json
import sys

PORT = 8090

applications = [
    {"name": "Firefox", "command": "firefox-bin", "logo": "󰈹"},
    {"name": "OBS", "command": "obs", "logo": ""},
    {"name": "Volume", "command": "pavucontrol", "logo": "󰕾"},
    {"name": "VLC", "command": "vlc", "logo": "󰕼"},
]

def filter_apps(term):
    if not term:
        return applications
    term_lower = term.lower()
    return [app for app in applications if term_lower in app["name"].lower()]

def start_server():
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server.bind(('localhost', PORT))
    server.listen(5)
    print(f"Server listening on port {PORT}")
    
    while True:
        client, addr = server.accept()
        data = client.recv(1024).decode().strip()
        filtered = filter_apps(data)
        response = json.dumps(filtered)
        print(response)
        client.send(response.encode())
        client.close()

def send_request(term):
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect(('localhost', PORT))
    client.send((term + "\n").encode())  # Add newline delimiter
    response = client.recv(4096).decode()
    client.close()
    print(response)

if __name__ == "__main__":
    if len(sys.argv) == 1:
        start_server()
    else:
        send_request(sys.argv[1])
