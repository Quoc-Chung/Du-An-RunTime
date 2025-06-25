// App.jsx
import React, { useState, useRef } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import ChatRoom from "./components/ChatRoom";
import Login from "./components/Login";
import "./App.css";

function App() {
  const [username, setUsername] = useState("");
  const [isConnected, setIsConnected] = useState(false);
  const stompClientRef = useRef(null);

  const connectSocket = (name) => {
    const socket = new SockJS("http://localhost:8080/chat_runtime");

    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        setUsername(name);
        setIsConnected(true);
        stompClient.publish({
          destination: "/app/join",
          body: name,
        });
      },
      onStompError: (frame) => {
        console.error("STOMP error:", frame.headers["message"]);
      },
    });

    stompClient.activate();
    stompClientRef.current = stompClient;
  };

  const leaveRoom = () => {
    if (stompClientRef.current) {
      stompClientRef.current.publish({
        destination: "/app/leave",
        body: username,
      });
      stompClientRef.current.deactivate();
    }
    setIsConnected(false);
  };

  return isConnected ? (
    <ChatRoom
      username={username}
      stompClient={stompClientRef.current}
      onLeave={leaveRoom}
    />
  ) : (
    <Login onLogin={connectSocket} />
  );
}

export default App;
