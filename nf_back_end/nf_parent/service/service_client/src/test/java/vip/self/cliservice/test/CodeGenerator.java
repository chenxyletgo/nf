package vip.self.cliservice.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class CodeGenerator {

    @Test
    public void run() {
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/nf?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "2001")
                .globalConfig(builder -> {
                    builder.author("cld") // 设置作者
                            .outputDir(projectPath + "\\src\\main\\java") // 指定输出目录
                            .disableOpenDir() // 生成文件后不打开文件
                            .dateType(DateType.ONLY_DATE); // 日期生成策略
                })
                .packageConfig(builder -> {
                    builder.parent("vip.self") // 设置父包名
                            .moduleName("cliservice"); // 设置父包模块名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_comment") // 设置需要生成的表名
                            .addTablePrefix("t_") // 设置过滤表前缀
                            .entityBuilder()
                            .idType(IdType.ASSIGN_ID) // 主键生成策略
                            .enableChainModel() // 链式
                            .enableLombok() // Lombok
                            .enableTableFieldAnnotation() // 注释
                            .logicDeleteColumnName("is_deleted")
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .controllerBuilder()
                            .enableRestStyle();
                })
                .execute();
    }
}
