package com.yuaiagent.controller;

import com.yuaiagent.chatmemory.FileBasedChatMemory;
import com.yuaiagent.service.LoveAppService;
import com.yuaiagent.service.SuperAgentService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import java.io.IOException;
import java.util.List;

/**
 * 描述：AI 恋爱大师应用控制层
 */
@RestController
@RequestMapping("/ai")
public class AiController
{
    @Resource
    private LoveAppService loveAppService;

    @Resource
    private SuperAgentService superAgentService;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    /**
     * 描述：列出所有对话 ID
     * @param type 智能体类型 (love|super)
     * @return
     */
    @GetMapping("/sessions")
    public List<FileBasedChatMemory.ConversationSummary> listChatIds(String type)
     {
        if ("super".equals(type)) 
        {
            return superAgentService.listConversations();
        }
        return loveAppService.listConversations();
    }

    /**
     * 描述：清空指定对话
     * @param chatId  对话ID
     * @param type    智能体类型 (love|super)
     * @return
     */
    @DeleteMapping("/sessions/{chatId}")
    public boolean clearChat(@PathVariable String chatId, String type)
     {
        if ("super".equals(type)) 
        {
            superAgentService.clearChat(chatId);
        } else {
            loveAppService.clearChat(chatId);
        }
        return true;
    }

    /**
     * 描述：获取指定对话的所有消息
     * @param chatId  对话ID
     * @param type    智能体类型 (love|super)
     * @return
     */
    @GetMapping("/sessions/{chatId}/messages")
    public List<Message> getChatMessages(@PathVariable String chatId, String type)
     {
        if ("super".equals(type)) 
        {
            return superAgentService.getChatMessages(chatId);
        }
        return loveAppService.getChatMessages(chatId);
    }

    /**
     * 描述：同步调用 AI 恋爱大师应用
     *
     * @param message 用户消息
     * @param chatId  对话 ID
     * @return
     */
    @GetMapping("/love_app/chat/sync")
    public String doChatWithLoveAppSync(String message, String chatId)
    {
        return loveAppService.doChat(message, chatId);
    }

    /**
     * 描述：SSE 流式调用 AI 恋爱大师应用
     *
     * @param message  用户消息
     * @param chatId   对话ID
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithLoveAppSSE(String message, String chatId)
    {
        return loveAppService.doChatByStream(message, chatId);
    }

    /**
     * 描述：SSE 流式调用 AI 恋爱大师应用
     *
     * @param message  用户消息
     * @param chatId   对话ID
     * @return
     */
    @GetMapping(value = "/love_app/chat/server_sent_event")
    public Flux<ServerSentEvent<String>> doChatWithLoveAppServerSentEvent(String message, String chatId)
    {
        return loveAppService.doChatByStream(message, chatId)
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }

    /**
     * 描述：SSE 流式调用 AI 恋爱大师应用
     *
     * @param message  用户消息
     * @param chatId   对话ID
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse_emitter")
    public SseEmitter doChatWithLoveAppServerSseEmitter(String message, String chatId)
    {
        // 创建一个超时时间较长的 SseEmitter
        SseEmitter sseEmitter = new SseEmitter(180000L); // 3 分钟超时
        // 获取 Flux 响应式数据流并且直接通过订阅推送给 SseEmitter
        loveAppService.doChatByStream(message, chatId)
                .subscribe(chunk -> {
                    try {
                        sseEmitter.send(chunk);
                    } catch (IOException e) {
                        sseEmitter.completeWithError(e);
                    }
                }, sseEmitter::completeWithError, sseEmitter::complete);
        // 返回
        return sseEmitter;
    }

    /**
     * 描述：流式调用 Manus 超级智能体
     *
     * @param message  用户消息
     * @param chatId   对话ID
     * @return
     */
    @GetMapping("/manus/chat")
    public SseEmitter doChatWithManus(String message, String chatId)
    {
        return superAgentService.doChatByStream(message, chatId);
    }

}
