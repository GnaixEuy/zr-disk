package cn.realandy.zrdisk;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/13
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@SpringBootTest
public class GeneraterDataSourceDocApplication {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 配置想要生成的表+ 配置想要忽略的表
     */
    public static ProcessConfig getProcessConfig() {
        // 忽略表名
        List<String> ignoreTableName = Arrays.asList("flyway_schema_history");
        // 忽略表前缀，如忽略a开头的数据库表
        List<String> ignorePrefix = Arrays.asList("t_his");
        // 忽略表后缀
        List<String> ignoreSuffix = Arrays.asList("_test");
        // 需要生成数据库文档的表
        List<String> designatedTableName = Arrays.asList("user", "role", "file", "vip");
        // 需要生成数据库文档的表前缀
        List<String> designatedTablePrefix = Arrays.asList("user_", "vip_");
        // 需要生成数据库文档的表后缀
        List<String> designatedTableSuffix = Arrays.asList("_exception");
        // 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
        return ProcessConfig.builder()
                // 根据名称指定表生成
                .designatedTableName(designatedTableName)
                // 根据表前缀生成
                .designatedTablePrefix(designatedTablePrefix)
                // 根据表后缀生成
                .designatedTableSuffix(designatedTableSuffix)
                // 忽略表名
                .ignoreTableName(ignoreTableName)
                // 忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                // 忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
    }

    /**
     * 数据库文档生成
     */
    @Test
    void documentGeneration() throws SQLException {
//        DataSource dataSource = applicationContext.getBean(DruidDataSource.class);
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/disk");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("123456");
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        String outPath = System.getProperty("user.dir").replaceAll("\\\\", "/");
        outPath += "/doc";
        // 生成文件配置 创建screw的引擎配置
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径
                .fileOutputDir(outPath)
                // 打开目录
                .openOutputDir(true)
                // 文件类型 HTML->HTML文件  WORD->WORD文件  MD->Markdown文件
                .fileType(EngineFileType.WORD)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker)
                // 自定义文件名称，即生成的数据库文档名称
                .fileName("zr-disk网盘系统数据库文档").build();
        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
        Configuration config = Configuration.builder()
                // 版本
                .version("1.0.0")
                // 描述
                .description("数据库设计文档生成")
                // 数据源
                .dataSource(dataSource)
                // 生成配置
                .engineConfig(engineConfig)
                // 生成配置
                .produceConfig(getProcessConfig())
                .build();
        // 执行screw，生成数据库文档
        new DocumentationExecute(config).execute();
    }
}

