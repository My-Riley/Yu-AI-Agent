import axios from 'axios'

// 根据环境变量设置 API 基础 URL
const isProduction = import.meta.env.PROD
const API_BASE_URL = isProduction 
 ? '/api' // 生产环境使用相对路径，适用于前后端部署在同一域名下
 : 'http://localhost:8123/api' // 开发环境指向本地后端服务

// 创建axios实例
const request = axios.create({
  baseURL: API_BASE_URL,
  timeout: 60000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    return response
  },
  error => {
    return Promise.reject(error)
  }
)

// 封装SSE连接
export const connectSSE = (url, params, onMessage, onError) => {
  // 构建带参数的URL
  const queryString = Object.keys(params)
    .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
    .join('&')
  
  const fullUrl = `${API_BASE_URL}${url}?${queryString}`
  
  // 创建EventSource
  const eventSource = new EventSource(fullUrl)
  
  eventSource.onmessage = event => {
    let data = event.data
    
    // 检查是否是特殊标记
    if (data === '[DONE]') {
      if (onMessage) onMessage('[DONE]')
    } else {
      // 处理普通消息
      if (onMessage) onMessage(data)
    }
  }
  
  eventSource.onerror = error => {
    if (onError) onError(error)
    eventSource.close()
  }
  
  // 返回eventSource实例，以便后续可以关闭连接
  return eventSource
}

// AI恋爱大师聊天
export const chatWithLoveApp = (message, chatId) => {
  return connectSSE('/ai/love_app/chat/sse', { message, chatId })
}

// AI超级智能体聊天
export const chatWithManus = (message, chatId) => {
  return connectSSE('/ai/manus/chat', { message, chatId })
}

// 获取会话列表
export const getSessions = (type) => {
  return request.get('/ai/sessions', { params: { type } })
}

// 获取会话消息
export const getSessionMessages = (chatId, type) => {
  return request.get(`/ai/sessions/${chatId}/messages`, { params: { type } })
}

// 清空会话
export const deleteSession = (chatId, type) => {
  return request.delete(`/ai/sessions/${chatId}`, { params: { type } })
}

export default {
  chatWithLoveApp,
  chatWithManus,
  getSessions,
  getSessionMessages,
  deleteSession
} 