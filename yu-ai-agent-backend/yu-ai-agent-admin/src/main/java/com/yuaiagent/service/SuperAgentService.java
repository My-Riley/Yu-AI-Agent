package com.yuaiagent.service;

import com.yuaiagent.agent.YuManus;
import com.yuaiagent.chatmemory.FileBasedChatMemory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * 描述：超级智能体 service 层
 */
@Service
@Slf4j
public class SuperAgentService
{

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    private final ChatMemory chatMemory;

    public SuperAgentService()
    {
        String fileDir = System.getProperty("user.dir") + "/tmp/super-chat-memory";
        this.chatMemory = new FileBasedChatMemory(fileDir);
    }

    /**
     * 描述：流式调用 Manus 超级智能体
     *
     * @param message 用户消息
     * @param chatId  对话ID
     * @return
     */
    public SseEmitter doChatByStream(String message, String chatId)
    {
        YuManus yuManus = new YuManus(allTools, dashscopeChatModel);
        yuManus.setChatId(chatId);
        yuManus.setChatMemory(chatMemory);
        return yuManus.runStream(message);
    }

    /**
     * 描述：获取指定对话的所有消息
     * @param chatId
     * @return
     */
    public List<Message> getChatMessages(String chatId)
    {
        return chatMemory.get(chatId);
    }

    /**
     * 描述：列出所有对话信息
     * @return
     */
    public List<FileBasedChatMemory.ConversationSummary> listConversations() {
        if (chatMemory instanceof FileBasedChatMemory) {
            return ((FileBasedChatMemory) chatMemory).listConversations();
        }
        return List.of();
    }

    /**
     * 描述：列出所有对话 ID
     * @return
     */
    public List<String> listChatIds()
    {
        if (chatMemory instanceof FileBasedChatMemory)
        {
            return ((FileBasedChatMemory) chatMemory).listConversationIds();
        }
        return List.of();
    }

    /**
     * 描述：清空指定对话
     * @param chatId
     */
    public void clearChat(String chatId)
    {
        chatMemory.clear(chatId);
    }
}
