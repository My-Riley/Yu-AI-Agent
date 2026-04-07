<template>
  <div class="home-container">
    <div class="header">
      <div class="glitch-wrapper">
        <h1 class="glitch-title">AI 超级智能体</h1>
      </div>
      <p class="subtitle">/ 探索 AI 的无限可能 /</p>
      <div class="cyber-line"></div>
    </div>
    
    <div class="apps-container">
      <div class="app-card" @click="navigateTo('/love-master')">
        <div class="card-glow"></div>
        <div class="app-icon love-icon">❤️</div>
        <div class="app-info">
          <div class="app-title">AI恋爱大师</div>
          <div class="app-desc">智能情感顾问，帮你解答恋爱烦恼</div>
        </div>
        <div class="app-button">
          <span class="btn-text">立即体验</span>
          <span class="btn-icon">→</span>
        </div>
      </div>
      
      <div class="app-card" @click="navigateTo('/super-agent')">
        <div class="card-glow"></div>
        <div class="app-icon robot-icon">🤖</div>
        <div class="app-info">
          <div class="app-title">AI超级智能体</div>
          <div class="app-desc">全能型AI助手，解决各类专业问题</div>
        </div>
        <div class="app-button">
          <span class="btn-text">立即体验</span>
          <span class="btn-icon">→</span>
        </div>
      </div>
    </div>
    
    <div class="cyber-circles">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const navigateTo = (path) => {
  router.push(path)
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=PingFang+SC:wght@400;500;700&display=swap');

/* 全局样式变量 */
:root {
  --primary-blue: #1890ff;
  --primary-pink: #ff4d79;
  --bg-white: #ffffff;
  --text-dark: #333333;
  --text-grey: #666666;
}

.home-container {
  display: flex; 
  flex-direction: column;
  min-height: 100vh;
  background-color: #f8fafc;
  background-image: 
    radial-gradient(circle at 100% 0%, rgba(24, 144, 255, 0.05) 0%, transparent 40%),
    radial-gradient(circle at 0% 100%, rgba(255, 77, 121, 0.05) 0%, transparent 40%);
  position: relative;
  overflow: hidden;
  padding: 80px 20px;
}

.header {
  text-align: center;
  margin-bottom: 80px;
  position: relative;
  z-index: 10;
}

.glitch-title {
  font-size: 48px;
  font-weight: 800;
  color: #1a1a1a;
  margin-bottom: 16px;
  letter-spacing: -1px;
}

.subtitle {
  font-size: 20px;
  color: #64748b;
  font-weight: 400;
}

.cyber-line {
  width: 60px;
  height: 4px;
  background: linear-gradient(90deg, #1890ff, #ff4d79);
  margin: 24px auto 0;
  border-radius: 2px;
}

.apps-container {
  display: flex;
  justify-content: center;
  gap: 32px;
  max-width: 1200px;
  margin: 0 auto;
  position: relative;
  z-index: 10;
}

.app-card {
  background: #ffffff;
  border-radius: 24px;
  padding: 48px 32px;
  width: 380px;
  cursor: pointer;
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.02);
}

.app-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.08);
}

.love-icon {
  background: #fff5f7;
  color: #ff4d79;
}

.robot-icon {
  background: #f0f7ff;
  color: #1890ff;
}

.app-icon {
  width: 80px;
  height: 80px;
  border-radius: 20px;
  font-size: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 24px;
}

.app-info {
  margin-bottom: 32px;
}

.app-title {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 12px;
}

.app-desc {
  font-size: 16px;
  color: #64748b;
  line-height: 1.6;
}

.app-button {
  width: 100%;
  height: 54px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  font-weight: 600;
  transition: all 0.3s;
}

.app-card:nth-child(1) .app-button {
  background: #ff4d79;
  color: white;
}

.app-card:nth-child(2) .app-button {
  background: #1890ff;
  color: white;
}

.app-button:hover {
  filter: brightness(1.1);
  gap: 16px;
}

.btn-icon {
  font-size: 20px;
}

.circle {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  z-index: 1;
  opacity: 0.1;
}

.circle-1 {
  width: 400px;
  height: 400px;
  background: #1890ff;
  top: -100px;
  right: -100px;
}

.circle-2 {
  width: 300px;
  height: 300px;
  background: #ff4d79;
  bottom: -50px;
  left: -50px;
}

@media (max-width: 768px) {
  .apps-container {
    flex-direction: column;
    align-items: center;
  }
  
  .app-card {
    width: 100%;
    max-width: 380px;
  }
  
  .glitch-title {
    font-size: 36px;
  }
}
</style> 