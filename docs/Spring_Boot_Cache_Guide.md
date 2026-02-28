# Spring Boot 缓存机制详解

## 📅 文档信息

- **创建日期**: 2026-02-28
- **基于项目**: Spring PetClinic (spring-boot-starter-parent 4.0.1)
- **Java 版本**: 17

---

## 1. Spring Boot 默认缓存实现（CacheManager）

Spring Boot 自动配置（`spring-boot-starter-cache`）会按照以下优先级自动配置 `CacheManager`：

| 优先级 | 缓存实现 | 条件 |
|--------|----------|------|
| 1 | RedisCacheManager | 存在 `spring-boot-starter-data-redis` 依赖 |
| 2 | EhCacheCacheManager | 存在 `ehcache.xml` 配置文件 |
| 3 | CaffeineCacheManager | 存在 Caffeine 依赖 |
| 4 | SimpleCacheManager | 默认使用，基于 `ConcurrentHashMap` |

### 默认实现：ConcurrentHashMap

如无特殊配置，Spring Boot 使用 `ConcurrentHashMap` 实现内存缓存：

```java
// 默认配置（Spring Boot 自动配置）
// 无需任何编码，自动生效
```

### 获取当前使用的 CacheManager

```java
@Autowired
private CacheManager cacheManager;

public void showCacheInfo() {
    System.out.println("当前缓存管理器: " + cacheManager.getClass().getName());
    System.out.println("缓存列表: " + cacheManager.getCacheNames());
}
```

---

## 2. 常用缓存注解

### 2.1 @Cacheable - 查询缓存

**作用**: 方法执行前检查缓存，存在则返回缓存数据，否则执行方法并缓存结果

**使用场景**: 查询操作，特别是频繁读取且数据变化频率低的数据

```java
@Service
public class VetService {

    @Cacheable(value = "vets", key = "#root.methodName")
    public Collection<Vet> findAllVets() {
        // 首次调用执行方法，结果存入缓存
        // 后续调用直接从缓存返回
        return vetRepository.findAll();
    }

    // 带条件的缓存
    @Cacheable(value = "vets", key = "#pageable.pageNumber", 
               condition = "#pageable.pageNumber < 10")
    public Page<Vet> findAllVets(Pageable pageable) {
        return vetRepository.findAll(pageable);
    }
}
```

**注解参数说明**:

| 参数 | 说明 | 示例 |
|------|------|------|
| `value` | 缓存名称（必填） | `"vets"` |
| `key` | 缓存键（SpEL表达式） | `"#id"`, `"#root.methodName"` |
| `condition` | 缓存条件（SpEL） | `"#id > 0"` |
| `unless` | 不缓存条件 | `"#result == null"` |

**SpEL 常用表达式**:

```java
@Cacheable(value = "users", key = "#id")
@Cacheable(value = "users", key = "#user.name + '_' + #user.age")
@Cacheable(value = "users", key = "#root.methodName")
@Cacheable(value = "users", key = "#result[0].id")  // 结果的第一个元素
```

### 2.2 @CachePut - 更新缓存

**作用**: 每次调用都执行方法，并更新缓存（用于数据更新场景）

**使用场景**: 更新用户信息等需要保持数据一致性的操作

```java
@Service
public class OwnerService {

    @CachePut(value = "owners", key = "#owner.id")
    public Owner updateOwner(Owner owner) {
        // 无论缓存是否存在，都会执行方法
        // 执行后将结果更新到缓存
        return ownerRepository.save(owner);
    }

    // 多种 key 策略
    @CachePut(value = "owners", key = "#result.id")
    public Owner saveOwner(Owner owner) {
        return ownerRepository.save(owner);
    }
}
```

**注意**: `@CachePut` 与 `@Cacheable` 不能同时用于同一方法（会冲突）

### 2.3 @CacheEvict - 清除缓存

**作用**: 删除缓存，可删除单条或全部

**使用场景**: 数据删除或更新时清除旧缓存

```java
@Service
public class OwnerService {

    // 清除单个缓存
    @CacheEvict(value = "owners", key = "#id")
    public void deleteOwner(Integer id) {
        ownerRepository.deleteById(id);
    }

    // 清除所有 owners 缓存
    @CacheEvict(value = "owners", allEntries = true)
    public void clearAllOwnersCache() {
        // 业务逻辑
    }

    // 在方法执行前清除缓存
    @CacheEvict(value = "owners", beforeInvocation = true)
    public void refreshOwners() {
        // 方法执行前清除缓存
    }

    // 组合多个缓存操作
    @Caching(evict = {
        @CacheEvict(value = "owners", key = "#id"),
        @CacheEvict(value = "vets", allEntries = true)
    })
    public void updateOwnerWithVets(Integer id) {
        // 清除多个缓存
    }
}
```

### 2.4 @Caching - 组合注解

```java
@Caching(
    cacheable = @Cacheable(value = "users", key = "#name"),
    put = @CachePut(value = "users", key = "#result.id")
)
public User findOrCreate(String name) {
    // 组合使用
}
```

---

## 3. 缓存配置步骤

### Step 1: 添加依赖

```xml
<!-- Maven -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

```groovy
// Gradle
implementation 'org.springframework.boot:spring-boot-starter-cache'
```

### Step 2: 启用缓存注解

```java
@Configuration
@EnableCaching  // 开启缓存支持
public class CacheConfiguration {

    @Bean
    public JCacheManagerCustomizer petclinicCacheConfigurationCustomizer() {
        return cm -> cm.createCache("vets", cacheConfiguration());
    }

    private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
        return new MutableConfiguration<>()
            .setStatisticsEnabled(true);  // 启用统计
    }
}
```

### Step 3: 在方法上使用注解

```java
@Repository
public interface VetRepository extends Repository<Vet, Integer> {

    @Transactional(readOnly = true)
    @Cacheable("vets")
    Collection<Vet> findAll();
}
```

---

## 4. 整合 Redis 缓存

### 4.1 添加 Redis 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### 4.2 配置 Redis 连接

```yaml
# application.yml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password:           # 可选
      database: 0
      timeout: 5000ms
      lettuce:
        pool:
          max-active: 8
          max-idle: 8
          min-idle: 0
          max-wait: -1ms
```

### 4.3 自定义 Redis 缓存配置

```java
@Configuration
@EnableCaching
public class RedisCacheConfiguration {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 默认配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofHours(1))           // 缓存过期时间
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair
                    .fromSerializer(new StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair
                    .fromSerializer(new GenericJackson2JsonRedisSerializer())
            )
            .disableCachingNullValues();             // 不缓存 null 值

        // 配置特定缓存
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("users", config.entryTtl(Duration.ofMinutes(30)));
        cacheConfigurations.put("products", config.entryTtl(Duration.ofHours(2)));

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .withInitialCacheConfigurations(cacheConfigurations)
            .transactionAware()
            .build();
    }
}
```

### 4.4 完整 Redis 配置类示例

```java
@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(":").append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheWriter redisCacheWriter = 
            RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .prefixCacheNameWith("petclinic:")
            .entryTtl(Duration.ofHours(1))
            .serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return new RedisCacheManager(redisCacheWriter, config);
    }
}
```

---

## 5. 实际代码示例（基于 Spring PetClinic）

### 5.1 缓存 Repository 层

```java
// VetRepository.java
public interface VetRepository extends Repository<Vet, Integer> {

    @Transactional(readOnly = true)
    @Cacheable("vets")  // 缓存名称为 "vets"
    Collection<Vet> findAll() throws DataAccessException;

    @Transactional(readOnly = true)
    @Cacheable(value = "vets", key = "#pageable.pageNumber")
    Page<Vet> findAll(Pageable pageable) throws DataAccessException;
}
```

### 5.2 缓存 Service 层

```java
@Service
@Transactional
public class VetService {

    private final VetRepository vetRepository;

    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "vets", key = "'all'")
    public Collection<Vet> findAll() {
        return vetRepository.findAll();
    }

    @CacheEvict(value = "vets", allEntries = true)
    public void reloadCache() {
        // 清除缓存，用于手动刷新
    }
}
```

### 5.3 缓存 Controller 层

```java
@RestController
@RequestMapping("/api/vets")
public class VetResource {

    private final VetService vetService;

    public VetResource(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping
    @Cacheable(value = "vets", key = "'api_all'")
    public Collection<Vet> showAllVets() {
        return vetService.findAll();
    }
}
```

---

## 6. 使用注意事项

### 6.1 缓存一致性问题

```java
// ❌ 错误示例：更新后没有清除缓存
@CachePut(value = "users", key = "#user.id")
public User updateUser(User user) {
    return userRepository.save(user);
}

// ✅ 正确示例：使用 @CacheEvict 清除缓存
@CacheEvict(value = "users", key = "#user.id")
public User updateUser(User user) {
    return userRepository.save(user);
}
```

### 6.2 缓存穿透

```java
// ✅ 使用 unless 避免缓存空值
@Cacheable(value = "users", key = "#id", unless = "#result == null")
public User findById(Integer id) {
    return userRepository.findById(id).orElse(null);
}
```

### 6.3 缓存雪崩

```java
// ✅ 设置随机过期时间
@Bean
public RedisCacheManager cacheManager() {
    Random random = new Random();
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(30 + random.nextInt(10)));  // 30-40分钟随机
    // ...
}
```

### 6.4 缓存击穿

```java
// ✅ 使用 sync 防止缓存击穿（Spring Boot 2.0+）
@Cacheable(value = "users", key = "#id", sync = true)
public User findById(Integer id) {
    return userRepository.findById(id).orElse(null);
}
```

### 6.5 序列化问题

```java
// ✅ 使用 JPA 实体时注意：
// 1. 实现 Serializable 接口
@Entity
public class Vet implements Serializable {
    private static final long serialVersionUID = 1L;
    // ...
}

// 2. 或使用 DTO 替代实体类进行缓存
```

### 6.6 事务与缓存

```java
// ✅ readOnly = true 可提升性能
@Transactional(readOnly = true)
@Cacheable("vets")
public Collection<Vet> findAll() {
    return vetRepository.findAll();
}
```

---

## 7. 缓存监控与管理

### 7.1 启用缓存监控

```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,caches
  cache:
    cache-names:
      - vets
      - owners
```

### 7.2 查看缓存信息

```bash
# 通过 actuator 端点
curl http://localhost:8080/actuator/caches
```

---

## 8. 总结对比

| 注解 | 作用 | 执行时机 | 适用场景 |
|------|------|----------|----------|
| `@Cacheable` | 查询缓存 | 方法执行前 | 读取多、写入少的数据 |
| `@CachePut` | 更新缓存 | 每次执行 | 需要保持数据新鲜的更新操作 |
| `@CacheEvict` | 清除缓存 | 方法执行后 | 数据删除或更新时清理旧缓存 |

---

## 📚 参考资料

- [Spring Boot 官方文档 - Caching](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.caching)
- [Spring Cache 官方文档](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache)
- [Spring Data Redis 文档](https://docs.spring.io/spring-data/redis/docs/current/reference/html/)