<template>
  <footer class="app-footer">
    <div class="footer-content">
      <div class="footer-section">
        <div class="footer-logo">
          <h3>AI 超级智能体应用平台</h3>
        </div>
        <div class="footer-links">
          <a href="#">《用户协议》</a>
          <a href="#">《隐私政策》</a>
        </div>
      </div>
      
      <div class="footer-section">
        <h4>快速导航</h4>
        <div class="footer-links">
          <router-link to="/love-master">AI 恋爱大师</router-link>
          <router-link to="/super-agent">AI 超级智能体</router-link>
          <router-link to="/">返回首页</router-link>
        </div>
      </div>
      
      <div class="footer-section">
        <h4>关于我们</h4>
        <div class="footer-links">
          <a href="#">商务合作</a>
          <a href="#">意见反馈</a>
        </div>
      </div>
    </div>
    
    <div class="footer-bottom">
      <p>© {{ currentYear }} AI 超级智能体应用平台 - 让 AI 为您服务</p>
    </div>
  </footer>
</template>

<script setup>
import { computed } from 'vue'

// 计算当前年份
const currentYear = computed(() => new Date().getFullYear())
</script>

<style scoped>
.app-footer {
  background-color: rgba(17, 23, 41, 0.8);
  backdrop-filter: blur(10px);
  padding: 40px 0 20px;
  color: rgba(255, 255, 255, 0.6);
  border-top: 1px solid rgba(0, 240, 255, 0.2);
  width: 100%;
  margin-top: auto;
  position: relative;
  z-index: 2;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  padding: 0 20px;
}

.footer-section {
  flex: 1;
  min-width: 200px;
  margin-bottom: 30px;
  padding: 0 15px;
}

.footer-logo h3 {
  font-family: 'Orbitron', sans-serif;
  font-size: 1.25rem;
  margin-bottom: 15px;
  color: #fff;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

.footer-section h4 {
  font-family: 'Orbitron', sans-serif;
  font-size: 1rem;
  margin-bottom: 15px;
  color: #fff;
}

.footer-links {
  display: flex;
  flex-direction: column;
}

.footer-links a {
  margin-bottom: 10px;
  color: rgba(255, 255, 255, 0.6);
  text-decoration: none;
  transition: all 0.2s;
}

.footer-links a:hover {
  color: #00f0ff;
  text-shadow: 0 0 5px #00f0ff;
}

.footer-bottom {
  text-align: center;
  padding-top: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  margin-top: 20px;
}

.qrcode-placeholder {
  width: 90px;
  height: 90px;
  background-color: #f5f5f5;
  margin: 0 auto 10px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
}

.qrcode-placeholder:after {
  content: '🤖';
}

.footer-bottom {
  text-align: center;
  padding-top: 20px;
  margin-top: 20px;
  border-top: 1px solid #eee;
  color: #999;
  font-size: 0.9rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .footer-content {
    flex-direction: column;
  }
  
  .footer-section {
    width: 100%;
    margin-bottom: 20px;
    padding: 0;
  }
}

@media (max-width: 480px) {
  .app-footer {
    padding: 30px 0 15px;
  }
  
  .footer-section h4 {
    font-size: 0.95rem;
  }
  
  .footer-links a {
    font-size: 0.9rem;
  }
  
  .qrcode-placeholder {
    width: 80px;
    height: 80px;
  }
}
</style> 