package vip.self.cliservice.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vip.self.cliservice.entity.pro.ArticlePro;
import vip.self.cliservice.mapper.ArticleMapper;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test01 {

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Test
    public void test01() {
        Page<ArticlePro> page = new Page<>(1,3);
        QueryWrapper<ArticlePro> wrapper = new QueryWrapper<>();
        wrapper.eq("main_temp.like", 1);
        articleMapper.selectArticlePro(page, wrapper);
        List<ArticlePro> list = page.getRecords();
        for (ArticlePro ap : list) {
            System.out.println(ap);
        }
    }
}
