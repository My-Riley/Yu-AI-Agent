<template>
  <div class="love-master-container">
    <div class="header">
      <div class="back-button" @click="goBack">返回</div>
      <h1 class="title">AI恋爱大师</h1>
      <div class="chat-id">会话ID: {{ chatId }}</div>
    </div>
    
    <div class="content-wrapper">
      <div class="chat-area">
        <ChatRoom 
          ref="chatRoomRef"
          :messages="messages" 
          :connection-status="connectionStatus"
          ai-type="love"
          theme-color="#ff4d79"
          theme-color-rgb="255, 77, 121"
          @send-message="sendMessage"
        />
      </div>
    </div>
    
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
import { useRouter } from 'vue-router'
import ChatRoom from '../components/ChatRoom.vue'
import { chatWithLoveApp, getSessionMessages } from '../api'

const props = defineProps({
  chatIdProp: String
})

const emit = defineEmits(['update-history', 'update-chat-id'])

const router = useRouter()
const messages = ref([])
const chatId = ref('')
const connectionStatus = ref('disconnected')
const chatRoomRef = ref(null)
let eventSource = null

// 生成随机会话ID
const generateChatId = () => {
  const newId = 'love_' + Math.random().toString(36).substring(2, 10)
  emit('update-chat-id', newId)
  return newId
}

// 加载历史消息
const loadMessages = async (id) => {
  if (!id) return
  
  // 清理现有连接
  if (eventSource) {
    eventSource.close()
    eventSource = null
  }
  connectionStatus.value = 'disconnected'
  
  // 清空输入框
  if (chatRoomRef.value) {
    chatRoomRef.value.clearInput()
  }
  
  try {
    const res = await getSessionMessages(id, 'love')
    if (res.data && res.data.length > 0) {
      messages.value = res.data.map(m => ({
        content: m.content || m.text || '', // 兼容不同版本的字段名
        isUser: m.messageType === 'USER' || m.role === 'USER',
        time: new Date().getTime() // 暂时用当前时间
      }))
    } else {
      // 如果没有历史消息，显示欢迎界面（messages 保持空）
      messages.value = []
    }
  } catch (error) {
    console.error('Failed to load messages:', error)
    // 出错也尝试清空并显示欢迎界面
    messages.value = []
  }
}

// 监听 ID 变化，如果是外部改变的 ID，重新加载
watch(() => props.chatIdProp, (newVal) => {
  if (newVal && newVal !== chatId.value) {
    chatId.value = newVal
    loadMessages(newVal)
  }
}, { immediate: true })

// 添加消息到列表
const addMessage = (content, isUser) => {
  messages.value.push({
    content,
    isUser,
    time: new Date().getTime()
  })
}

// 发送消息
const sendMessage = async (message) => {
  if (!chatId.value) {
    chatId.value = generateChatId()
  }
  
  addMessage(message, true)
  
  // 连接SSE
  if (eventSource) {
    eventSource.close()
  }
  
  // 创建一个空的AI回复消息
  const aiMessageIndex = messages.value.length
  addMessage('', false)
  
  connectionStatus.value = 'connecting'
  eventSource = chatWithLoveApp(message, chatId.value)
  
  // 监听SSE消息
  eventSource.onmessage = (event) => {
    const data = event.data
    if (data && data !== '[DONE]') {
      // 更新最新的AI消息内容，而不是创建新消息
      if (aiMessageIndex < messages.value.length) {
        messages.value[aiMessageIndex].content += data
      }
    }
    
    if (data === '[DONE]') {
      connectionStatus.value = 'disconnected'
      eventSource.close()
      // 发送完第一条消息后通知父组件更新历史记录
      emit('update-history')
    }
  }
  
  // 监听SSE错误
  eventSource.onerror = (error) => {
    console.error('SSE Error:', error)
    connectionStatus.value = 'error'
    eventSource.close()
  }
}

// 返回主页
const goBack = () => {
  router.push('/')
}

// 页面加载时处理逻辑
onMounted(() => {
  if (props.chatIdProp) {
    chatId.value = props.chatIdProp
    loadMessages(chatId.value)
  } else {
    // 首次进入没有对话记录，生成一个新 ID
    chatId.value = generateChatId()
    // 欢迎界面显示（messages 为空即可）
    messages.value = []
  }
})

// 组件销毁前关闭SSE连接
onBeforeUnmount(() => {
  if (eventSource) {
    eventSource.close()
  }
})
</script>

<style scoped>
.love-master-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #fffcfd;
  color: #1e293b;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 24px;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  color: #1e293b;
  border-bottom: 1px solid #fce7eb;
  position: sticky;
  top: 0;
  z-index: 10;
}

.back-button {
  font-size: 16px;
  cursor: pointer;
  display: flex;
  align-items: center;
  transition: all 0.3s;
  color: #64748b;
}

.back-button:hover {
  color: #ff4d79;
  transform: translateX(-4px);
}

.back-button:before {
  content: '←';
  margin-right: 8px;
}

.title {
  font-size: 20px;
  font-weight: 700;
  margin: 0;
  color: #ff4d79;
  letter-spacing: -0.5px;
}

.chat-id {
  font-size: 13px;
  color: #94a3b8;
  font-family: monospace;
}

.content-wrapper {
  display: flex;
  flex-direction: column;
  flex: 1;
  overflow: hidden;
}

.chat-area {
  flex: 1;
  overflow: hidden;
  position: relative;
}

/* 响应式样式 */
@media (max-width: 768px) {
  .header {
    padding: 12px 16px;
  }
  
  .title {
    font-size: 18px;
  }
  
  .chat-id {
    display: none;
  }
}
</style>