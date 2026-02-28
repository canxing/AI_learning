# Gemini CLI 学习笔记

## 📅 2026-02-02 第一课：入门与核心概念

### 1. 核心理念
- **结对编程**：Gemini CLI 不仅仅是聊天机器人，它能直接读取本地文件、执行 Shell 命令。
- **上下文感知**：它通过读取项目文件来理解现状，而不是依赖对话历史。

### 2. 基本工作流
- **创建/写文件**：直接下令（如"创建一个 Java Hello World"），AI 会自动调用 `write_file`。
- **运行命令**：AI 可以编译、运行程序（`run_shell_command`），如 `javac`、`git` 等。
- **重构**：可以指挥 AI 移动目录、修改代码逻辑并清理旧文件。

### 3. Git 集成
- **初始化**：`git init` 和生成 `.gitignore`。
- **智能提交**：AI 可以分析变更并自动生成 Commit Message（`git commit -m "..."`）。
- **远程协作**：支持 `git push` 到 GitHub，甚至处理关联远程仓库。

### 4. 上下文管理（进阶）
这是保证 AI 长期协作效率的关键：

| 文件名 | 作用 | 类似于 |
| :--- | :--- | :--- |
| **`PROJECT_STATUS.md`** | 记录项目进度、待办事项 | **会议记录/工单** |
| **`GEMINI.md`** | 记录技术规范、编码风格 | **员工手册/开发规范** |
| **`save_memory`** (工具) | 记住用户个人的全局偏好 | **用户配置文件** |

### 5. 常用指令话术
- "分析一下当前目录" -> 快速上手新接手的项目。
- "读取项目状态" -> 恢复上次的工作进度。
- "Git push 报错了帮我修" -> 解决版本控制问题。

### 6. 高级技巧：文档自动化联动
- **痛点**：多份文档（如进度表、路线图）手动更新繁琐且易忘。
- **解决方案**：在 `GEMINI.md` 中定义"维护协议"。
- **原理**：通过 prompt 明确告诉 AI，"每当完成编码任务，必须主动检查并更新 PROJECT_STATUS.md"。
- **效果**：实现"改代码 -> 自动修文档"的链式反应，保持单一数据源一致性。

---

## 📅 2026-02-28 第二课：子代理（Task）熟练运用

### 1. 什么是子代理？
- 子代理是通过 `task` 工具启动的**临时工作代理**，用于并行处理多任务
- 不同于主对话中的代理，子代理有独立的任务上下文
- 可以同时启动多个子代理，实现并行处理

### 2. 子代理类型
| 类型 | 说明 | 权限 |
|------|------|------|
| `explore` | 代码库探索代理，快速查找文件和搜索代码 | 只读 |
| `general` | 通用代理，执行多步骤复杂任务 | 读写（除todo外） |

### 3. task 工具参数
- `command`: 触发任务的命令
- `description`: 任务描述（3-5词）
- `prompt`: 详细的任务指令
- `subagent_type`: 子代理类型（explore/general）
- `task_id`: （可选）继续同一个会话

### 4. 学习计划
**目标**：熟练运用子代理进行并行任务处理

| 次数 | 内容 | 状态 |
|------|------|------|
| 第1次 | explore 基础入门：并行分析 Owner/Pet 模块 | ✅ 已完成 |
| 第2次 | 结果整合与交叉验证 | ✅ 已完成 |
| 第3次 | general 代理：研究技术问题并生成文档 | ✅ 已完成 |
| 第4次 | 高级技巧：task_id 续话 + 复杂 prompt 设计 | ✅ 已完成 |
| 第5次 | 综合实战：多代理协作生成架构分析报告 | ✅ 已完成 |

### 5. 实践记录

#### 第1次：explore 基础入门
- 任务：并行分析 Owner 模块和 Pet 模块
- 方式：同时启动两个 explore 子代理
- 结果：获得完整的模块分析报告（API、实体关系、文件结构）

#### 第2次：结果整合与交叉验证
- 任务：验证子代理发现的准确性
- 方式：使用 `grep` 工具搜索关键代码
- 结果：所有发现均验证通过

#### 第3次：general 代理
- 任务：研究 Spring Boot 缓存机制
- 方式：启动 general 子代理，生成技术文档
- 结果：生成 `docs/Spring_Boot_Cache_Guide.md`

#### 第4次：高级技巧
- 任务：学习 task_id 续话
- 方式：用已有 task_id 继续会话
- 结果：成功继续 Owner 分析，获取更详细的级联和加载策略说明

#### 第5次：综合实战
- 任务：多代理协作分析完整架构
- 方式：3个子代理并行分析 Owner/Visit/Vet 模块
- 结果：生成 `docs/PetClinic_Architecture.md` 架构分析报告

#### 第6次：自建子代理（待进行）
- 任务：创建自定义子代理并调用
- 方式：在 `.opencode/agents/` 目录创建自定义代理配置文件
- 目标：掌握自建子代理的完整流程

---

## 📅 2026-02-28 第三课：子代理（Task）进阶问答

### 常见问题与解答

#### Q1: 如何调用 explore 子代理？
**语法**：
```python
task(
    command="分析 PetClinic Owner 模块",
    description="探索 Owner 模块",
    prompt="在 spring-petclinic 项目中探索 Owner 模块...",
    subagent_type="explore"
)
```

**关键参数**：
- `command`: 任务标题
- `description`: 简短描述（3-5词）
- `prompt`: 详细任务指令
- `subagent_type`: 子代理类型（explore/general）

---

#### Q2: 什么情况下会调用子代理？
**触发场景**：
- **并行任务**："同时分析 A 模块和 B 模块"
- **代码探索**："找出所有的 Controller 文件"
- **批量搜索**："搜索所有用到 XXX 的地方"
- **研究任务**："研究 Spring Boot 缓存机制"
- **多步骤任务**："帮我完成 XXX，需要先找文件、再分析、最后生成报告"

**原则**：
- 可并行独立执行时
- 需要深入探索代码库时
- 步骤较多需要委托时

---

#### Q3: explore 的适用场景在哪里定义？
**官方文档**：https://opencode.ai/docs/agents

**定义**：
```
explore: A fast, read-only agent for exploring codebases. 
Cannot modify files. Use this when you need to quickly 
find files by patterns, search code for keywords, or 
answer questions about the codebase.
```

**两种内置子代理**：
| 子代理 | 描述 |
|--------|------|
| explore | 只读，快速探索代码库 |
| general | 读写，执行多步骤复杂任务 |

---

#### Q4: 自建子代理和 explore 功能重合时如何选择？
**默认选择**：当用户未明确指定时，使用内置 explore

**选择逻辑**：
- 用户只说"分析代码" → 内置 explore
- 用户说"用代码审查代理分析" → 检查是否存在该自建代理，存在则用之
- 用户明确指定代理名称 → 使用指定代理

**核心区别**：
| 子代理 | 优势 |
|--------|------|
| 内置 explore | 无需配置，随时可用 |
| 自建子代理 | 更贴合特定业务场景，可定制 prompt/工具 |

---

#### Q5: 如何获取子代理 id？
**方式**：每次调用 `task` 后，返回值中包含 `task_id`

**示例**：
```
task_id: ses_35c54be67ffeHOeRrPw9qrKJQ8 (for resuming to continue this task if needed)
```

**忘记 id 怎么办**：
- 直接问我"之前的子代理 id 是什么？"
- 我会从对话历史中查找并列出所有子代理 id

---

#### Q6: 如何与子代理继续对话？
**方式**：必须**告诉我**，我来帮你调用

**实际路径**：
```
你 → 主代理(我) → task 工具 → 子代理
```

**你可以这样表达**：
- "继续之前的 Owner 分析"
- "用 task_id ses_35c54be67ffeHOeRrPw9qrKJQ8 继续"
- "让之前的子代理补充说明 XXX"

**注意**：你不能直接和子代理对话，必须通过我中转。

---

#### Q7: 如何同时调用两个子代理分析，最后又用一个子代理整合结果？
**触发并行分析的关键词**：
- "同时分析 Owner 模块和 Pet 模块"
- "并行分析 A、B、C 三个模块"
- "分别分析 Controller 层和 Service 层"

**关于整合**：
- 只说"分析 A 和 B" → 只启动 2 个 explore
- 说"分析 A 和 B，**然后整合结果**" → 先启动 2 个 explore，再用 1 个 general 整合
- 说"分析 A、B、C，**生成综合报告**" → 启动 3 个 explore，然后用 general 整合

**完整示例**：
> "同时分析 PetClinic 的 Owner、Visit、Vet 三个模块，然后整合成一份完整的架构分析报告"

**执行流程**：
1. 启动 explore 分析 Owner
2. 启动 explore 分析 Visit
3. 启动 explore 分析 Vet
4. 等待结果
5. 启动 general 整合三个结果
6. 生成最终报告

---

### 关键学习要点

1. **子代理类型选择**：
   - 只读探索 → explore
   - 需要写文件/生成报告 → general

2. **task_id 续话**：
   - 用于继续之前的会话
   - 保留上下文，适合长对话

3. **多代理协作**：
   - 明确说"整合"才会启动第三个代理
   - 并行分析 → 串行整合 → 输出报告

4. **产出成果**：
   - `docs/Spring_Boot_Cache_Guide.md`
   - `docs/PetClinic_Architecture.md`