package com.yuaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;

/**
 * 描述：Spring AI 框架方式调整 AI 大模型（Ollama）
 */
//@Component // 取消注释后，项目启动时会执行
public class OllamaAiInvoke implements CommandLineRunner
{
    @Resource
    private ChatModel ollamaChatModel;

    @Override
    public void run(String... args) throws Exception
    {
        AssistantMessage assistantMessage = ollamaChatModel.call(new Prompt("你好，我是程序员小白"))
                .getResult()
                .getOutput();
        System.out.println(assistantMessage.getText());
    }
}
