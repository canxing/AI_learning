# 🗺️ Gemini CLI 深度学习路线图 (Mastery Roadmap)

这份路线图旨在帮助开发者从简单的命令行交互，进阶到能够独立完成全栈开发与云原生部署的专家级水平。

## 🟢 第一阶段：基础交互与单文件操作 (已完成)
**目标**：建立信任，习惯“指令式”编程。
- [x] **基本文件操作**：`write_file`, `read_file`。
- [x] **Shell 交互**：编译 Java，运行 Shell 命令。
- [x] **版本控制入门**：Git init, commit, push。
- [x] **上下文管理**：创建 `PROJECT_STATUS.md` 和 `GEMINI.md`。

## 🟡 第二阶段：工程化与标准开发 (当前阶段)
**目标**：从写“脚本”进化到写“工程”。
- [ ] **构建工具迁移**：将普通 Java 项目转化为 Maven/Gradle 项目。
- [ ] **依赖管理**：让 AI 寻找并添加第三方库（如 Lombok, Jackson）。
- [ ] **测试驱动开发 (TDD)**：
    - 编写 JUnit 5 测试用例。
    - 体验“红-绿-重构”循环。
- [ ] **Debug 流程**：模拟运行时错误，学习如何将堆栈信息喂给 AI 进行修复。

## 🟠 第三阶段：现有项目辅助开发 (Legacy Code)
**目标**：在不熟悉的庞大代码库中快速定位并安全修改。
- [ ] **代码库“探针”**：
    - 使用 `list_directory` (递归) 快速建立项目脑图。
    - 使用 `glob` 和 `search_file_content` 定位特定业务逻辑（如：“找到所有处理 'UserLogin' 的逻辑”）。
- [ ] **架构逆向分析**：
    - 让 AI 阅读代码并生成 Mermaid 类图或时序图。
    - 解释复杂方法的业务含义。
- [ ] **安全重构**：
    - 提取重复代码为工具类。
    - 批量替换过时的 API（使用 `replace` 工具）。
- [ ] **补全文档**：为没有注释的旧代码生成 JavaDoc。

## 🔴 第四阶段：全栈开发与云原生部署 (Full Stack & K8s)
**目标**：一个人就是一支队伍，从后端到上线。
- [ ] **后端开发 (Spring Boot)**：
    - 定义 API 接口，生成 Controller/Service/Repository 层。
    - 集成数据库（H2/MySQL）。
- [ ] **前端开发 (React/Vue)**：
    - 使用 `run_shell_command` 初始化前端脚手架。
    - 让 AI 生成组件代码并调用后端 API。
- [ ] **容器化 (Docker)**：
    - 编写 `Dockerfile`（多阶段构建，优化镜像大小）。
    - 编写 `docker-compose.yml` 进行本地联调。
- [ ] **Kubernetes 部署**：
    - 编写 Deployment 和 Service 的 YAML 文件。
    - 解释 K8s 资源配置（CPU/Memory Limits, Probes）。
    - 模拟 `kubectl apply` 部署流程。

## 🟣 第五阶段：专家级定制 (Automation)
**目标**：让工具适应你的个人习惯。
- [ ] **自定义 Skills**：封装常用的代码生成模板。
- [ ] **自动化运维脚本**：编写 Shell 脚本处理 CI/CD 流水线。
- [ ] **知识库构建**：利用 `save_memory` 记录个人专属的云平台配置和常用 Snippets。
