import React, { useState } from 'react'
import '../css/Login.css';
export default function Login({ onLogin }) {
  const [name, setName] = useState("");

  return (
    <div className="login-box">
      <h2>🎯 Đăng nhập vào phòng chat</h2>
      <input
        type="text"
        placeholder="Nhập tên của bạn..."
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <button onClick={() => onLogin(name)} disabled={!name}>
        Tham gia phòng
      </button>
    </div>
  );
}