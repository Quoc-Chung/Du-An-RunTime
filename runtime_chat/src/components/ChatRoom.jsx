import React, { useState, useEffect, useRef } from "react";
import '../css/ChatRoom.css';
export default function ChatRoom({ username, stompClient, onLeave }) {
  const [messageInput, setMessageInput] = useState("");
  const [messages, setMessages] = useState([]);
  const messageEndRef = useRef(null);

  useEffect(() => {
    if (stompClient) {
      const subscription = stompClient.subscribe("/topic/messages", (message) => {
        const msg = JSON.parse(message.body);
        setMessages((prev) => [...prev, msg]);
      });

      return () => {
        subscription.unsubscribe();
      };
    }
  }, [stompClient]);

  useEffect(() => {
    messageEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const sendMessage = () => {
    if (messageInput.trim()) {
      const msg = {
        nguoiGui: username,
        content: messageInput,
        type: "CHAT",
      };

      stompClient.publish({
        destination: "/app/sendMessage",
        body: JSON.stringify(msg),
      });

      setMessageInput("");
    }
  };

  return (
    <div className="chat-box">
      <div className="header">
        <h3>💬 Phòng chat</h3>
        <button onClick={onLeave}>Rời phòng</button>
      </div>

      <div className="messages">
        {messages.map((msg, idx) => (
          <div
            key={idx}
            className={`message ${
              msg.type !== "CHAT"
                ? "system"
                : msg.nguoiGui === username
                ? "self"
                : "other"
            }`}
          >
            {msg.type === "CHAT" ? (
              <>
                <strong>{msg.nguoiGui}:</strong> {msg.content}
              </>
            ) : (
              <em>{msg.content}</em>
            )}
          </div>
        ))}
        <div ref={messageEndRef}></div>
      </div>

      <div className="input-box">
        <input
          type="text"
          placeholder="Nhập tin nhắn..."
          value={messageInput}
          onChange={(e) => setMessageInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && sendMessage()}
        />
        <button onClick={sendMessage}>Gửi</button>
      </div>
    </div>
  );
}
