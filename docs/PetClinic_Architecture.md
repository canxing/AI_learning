# PetClinic 架构分析报告

## 1. 模块概览表格

| 模块 | 核心实体 | Controller | Repository | 关系模式 |
|------|----------|------------|------------|----------|
| **Owner** | Owner (extends Person → BaseEntity) | OwnerController | OwnerRepository | 一对多 (→ Pet) |
| **Pet** | Pet (extends NamedEntity → BaseEntity) | PetController | PetRepository | 多对一 (← Owner)<br>一对多 (→ Visit) |
| **Visit** | Visit | VisitController | VisitRepository | 多对一 (← Pet) |
| **Vet** | Vet (extends Person → BaseEntity) | VetController | VetRepository | 多对多 (↔ Specialty) |
| **Specialty** | Specialty (extends BaseEntity) | - | SpecialtyRepository | 多对多 (↔ Vet) |

---

## 2. 实体关系图（文字描述）

```
BaseEntity (id)
    ↑
    ├── Person (firstName, lastName)
    │       ↑
    │       ├── Owner
    │       │       └── Pet (name, birthDate)
    │       │               └── Visit (date, description)
    │       │
    │       └── Vet
    │               ↔ Specialty (name)
    │
    └── NamedEntity (name)
            ↑
            PetType
```

**关键关系：**
- **Owner → Pet**: 一对多，EAGER 加载，级联 ALL
- **Pet → Visit**: 一对多，EAGER 加载
- **Vet ↔ Specialty**: 多对多（中间表 `vet_specialties`）

---

## 3. API 端点汇总

### Owner 相关
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/owners/new` | 新建 Owner 表单 |
| POST | `/owners/new` | 保存 Owner |
| GET | `/owners/find` | 查找 Owner 表单 |
| GET | `/owners` | 列表页（支持分页） |
| GET | `/owners/{id}` | 详情页 |
| GET | `/owners/{id}/edit` | 编辑表单 |
| POST | `/owners/{id}/edit` | 更新 Owner |

### Pet 相关
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/owners/{ownerId}/pets/new` | 新建 Pet 表单 |
| POST | `/owners/{ownerId}/pets/new` | 保存 Pet |
| GET | `/owners/{ownerId}/pets/{petId}/edit` | 编辑表单 |
| POST | `/owners/{ownerId}/pets/{petId}/edit` | 更新 Pet |

### Visit 相关
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/owners/{ownerId}/pets/{petId}/visits/new` | 新建 Visit 表单 |
| POST | `/owners/{ownerId}/pets/{petId}/visits/new` | 保存 Visit |

### Vet 相关
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/vets.html` | 兽医列表页（HTML） |
| GET | `/vets` | 兽医列表（JSON） |

---

## 架构特点

1. **分层架构**: Controller → Service → Repository（JPA）
2. **实体继承**: BaseEntity → Person → Owner/Vet
3. **前端模板**: Thymeleaf（HTML）+ REST API（JSON）
4. **关系加载策略**: 关联实体默认 EAGER 加载