# 项目开发状态 (Gemini CLI 学习笔记)

## 📅 最后更新: 2026-02-28

## 🎯 当前进度
- [x] **第一阶段完成**: 掌握了基础的文件操作、Git 流程和 Maven 构建（基于 Hello World 示例）。
- [x] **方向调整**: 用户决定跳过简单的依赖管理练习，直接使用**真实开源项目**进行高阶学习。
- [x] **代理指南**: 已创建 AI 代理指南文件 (AGENTS.md)，包含构建命令、代码风格和行为准则。
- [x] **opencode智能助手**: 已配置 `/继续学习` 命令，支持一键分析学习进度并提供下一步建议。
- [x] **项目选型完成**: 已成功克隆 Spring PetClinic 项目（spring-projects/spring-petclinic）。
- [x] **环境准备完成**: Spring PetClinic 项目已成功构建（Maven clean compile）。
- [x] **子代理（Task）熟练运用**: 掌握 task 工具启动子代理进行并行任务处理。

## 🚀 待办事项 (Next Steps)
1. [x] **项目选型**: 已选择并克隆 Spring PetClinic 项目到本地。
2. [x] **环境准备**: 项目已克隆，Java 21 和 Maven 3.9.12 环境已验证，构建成功。
3. [ ] **深度调研**: 使用 `codebase_investigator` 或组合工具分析复杂架构。
4. [ ] **实战任务**: 在真实代码库中定位 Bug 或添加特性。
5. [x] **子代理学习 - 基础入门**: 启动 explore 子代理分析 PetClinic 模块。
6. [x] **子代理学习 - 结果整合**: 整合多个子代理结果并验证准确性。
7. [x] **子代理学习 - 通用代理**: 使用 general 子代理研究技术问题。
8. [x] **子代理学习 - 高级技巧**: 掌握 task_id 续话和复杂 prompt 设计。
9. [x] **子代理学习 - 综合实战**: 多代理协作生成架构分析报告。
10. [ ] **子代理学习 - 自建子代理**: 创建自定义子代理并调用。

## 💡 小贴士
- 推荐选择代码质量高且有一定复杂度的项目，例如:
    - `spring-projects/spring-petclinic` (经典的 Spring Boot 示例)
    - `apache/commons-lang` (工具类库，适合单元测试和逻辑分析)
    - `google/guava` (高质量 Java 核心库)

## 💡 小贴士
- 下次在任何地方打开此项目，只需对 Gemini 说：**"读取 PROJECT_STATUS.md"**，即可快速同步进度。
