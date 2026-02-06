# AI学习项目 - 代理指南

本文档为AI代理（如opencode、Cursor、GitHub Copilot等）提供在本代码库中工作的必要信息，包括构建命令、代码风格指南和行为准则。本项目专注于AI CLI工具的学习和实践，旨在通过实际项目掌握AI辅助编程技能。

## 项目概述

- **项目性质**: AI CLI工具学习项目，通过实践掌握AI辅助编程技能
- **项目结构**: 多项目学习目录，每个子项目专注于特定技术栈或学习目标
- **当前项目**: `01_hello_world/` - 第一个Java Maven示例项目
- **技术栈**: 
  - **Java版本**: 21
  - **构建工具**: Maven 3.9+
  - **编码**: UTF-8
- **项目目录结构**:
  - `01_hello_world/` - Java Maven入门项目
    - `src/main/java/com/example/` - 主源代码
    - `src/test/java/com/example/` - 测试代码
  - *(后续将添加更多学习项目，如Spring Boot、React、Docker等)*
- **学习文档**:
  - `GEMINI.md` - 技术规范和AI行为准则
  - `PROJECT_STATUS.md` - 项目进度和待办事项
  - `ROADMAP.md` - 学习路线图
  - `LEARNING_NOTES.md` - 学习笔记
  - `AGENTS.md` - AI代理工作指南（本文档）

## 构建与测试命令

**注意**: 不同项目可能使用不同的构建工具。进入新项目时，首先检查项目根目录的构建配置文件（如 `pom.xml`, `build.gradle`, `package.json` 等）。

### 当前项目 (01_hello_world) - Maven常用命令

```bash
# 进入项目目录
cd 01_hello_world

# 清理并编译
mvn clean compile

# 运行所有测试
mvn test

# 打包项目（生成JAR）
mvn package

# 运行主类
mvn exec:java

# 直接运行（已编译）
java -cp target/classes com.example.Hello [参数]
```

### 运行单个测试

```bash
# 运行特定测试类
mvn test -Dtest=HelloTest

# 运行特定测试方法
mvn test -Dtest=HelloTest#testGetGreetingWithDefaultName

# 使用通配符
mvn test -Dtest="*Test"
```

### 其他实用命令

```bash
# 检查依赖
mvn dependency:tree

# 格式化代码（如有spotless配置）
mvn spotless:apply

# 静态分析（如有checkstyle配置）
mvn checkstyle:check
```

## 代码风格指南

**注意**: 以下代码风格指南基于当前Java项目，其他语言项目请参考相应语言的社区规范。

### 基本规范

1. **语言**: Java 21
2. **编码**: UTF-8
3. **注释**: 所有代码注释和解释必须使用**中文**
4. **目录结构**: 严格遵循Maven标准结构

### 命名约定

- **类名**: 大驼峰式，如 `HelloWorld`
- **方法名**: 小驼峰式，如 `getGreeting`
- **变量名**: 小驼峰式，有意义的名词
- **常量名**: 全大写，下划线分隔，如 `MAX_SIZE`
- **包名**: 全小写，反向域名，如 `com.example`

### 导入规范

```java
// 正确：分组导入，空行分隔
package com.example;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

// 避免通配符导入（除了静态导入）
import java.util.*; // 不推荐
```

### 格式化规范

1. **缩进**: 4个空格（非制表符）
2. **行长**: 不超过100字符
3. **大括号**: K&R风格
   ```java
   public class Hello {
       public void method() {
           // 代码
       }
   }
   ```
4. **空行**: 
   - 类/方法之间空一行
   - 逻辑段落之间空一行

### 类型与异常处理

1. **类型推断**: 优先使用`var`（局部变量）
2. **异常处理**: 
   - 捕获具体异常，而非`Exception`
   - 记录日志或重新抛出
   - 避免空的catch块
3. **Optional**: 可能为空的值使用`Optional`

### 测试规范

1. **测试框架**: JUnit 5
2. **命名模式**: 
   - 测试类: `*Test`
   - 测试方法: `test*` 或使用描述性名称
3. **断言**: 使用JUnit 5的`Assertions`类
4. **覆盖率**: 新增功能必须包含测试用例

## AI代理行为准则

### 代码修改原则

1. **上下文优先**: 修改代码前，先了解上下文和现有模式
2. **最小变更**: 优先以"最小diff"方式修改，避免无关重构
3. **API稳定**: 除非明确要求，否则不修改public API、类名、方法签名
4. **依赖谨慎**: 遵循Java/Spring主流实践，不引入不必要的新依赖
5. **事实基础**: 不臆造不存在的类、Bean、配置或依赖

### 测试验证

1. **重构必测**: 每次重构后，必须运行测试验证
2. **故障处理**: 如果测试失败，优先修复而非跳过

### 沟通风格

1. **简洁直接**: 保持回答简洁，优先使用代码和Shell命令
2. **明确标注**: 修改代码时，优先输出git diff或明确标注修改位置
3. **诚实透明**: 如果信息不足，先说明不确定点，而非猜测

## 文档维护协议

### 自动联动机制

每当完成代码任务或学习里程碑后，**必须主动**检查并更新以下文件，无需用户额外指令：

1. **`PROJECT_STATUS.md`**: 更新"当前进度"和"待办事项"
2. **`ROADMAP.md`**: 如果涉及大阶段完成，勾选对应里程碑

### 更新原则

1. **单一来源**: 遇到冲突时，以代码实际状态为准
2. **及时同步**: 任务完成后立即更新文档
3. **保持简洁**: 文档更新应清晰、简洁，反映实际进展

### 更新示例

```markdown
## 📅 最后更新: 2026-02-06

## 🎯 当前进度
- [x] **新增功能**: 添加了用户认证模块
- [x] **测试覆盖**: 编写了相关单元测试

## 🚀 待办事项 (Next Steps)
1. [ ] **集成测试**: 添加端到端测试
2. [ ] **性能优化**: 优化数据库查询
```

## Git工作流

### 基本操作

```bash
# 查看状态
git status

# 添加变更
git add .

# 提交（AI应生成有意义的提交信息）
git commit -m "feat: 添加用户认证模块"

# 推送
git push origin main
```

### 提交信息规范

- **feat**: 新功能
- **fix**: bug修复
- **docs**: 文档更新
- **style**: 代码格式（不影响功能）
- **refactor**: 重构
- **test**: 测试相关
- **chore**: 构建过程或辅助工具变动

## 故障排查

### 常见问题

1. **构建失败**:
   - 检查Java版本: `java -version`
   - 清理并重新构建: `mvn clean compile`
   - 检查网络和Maven仓库

2. **测试失败**:
   - 运行单个测试定位问题
   - 检查测试数据和环境
   - 查看堆栈跟踪

3. **依赖问题**:
   - 更新依赖: `mvn versions:update-properties`
   - 检查冲突: `mvn dependency:tree`

### 获取帮助

- 查看项目文档: `README.md`, `GEMINI.md`
- 检查项目状态: `PROJECT_STATUS.md`
- 查看路线图: `ROADMAP.md`

---

**最后更新**: 2026-02-06  
**维护者**: AI代理系统  
**版本**: 1.0