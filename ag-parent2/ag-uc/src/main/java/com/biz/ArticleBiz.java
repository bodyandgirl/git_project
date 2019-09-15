package com.biz;

import com.entity.Article;
import com.mapper.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ArticleBiz extends BaseBiz<ArticleMapper,Article>  {
//    @Override
//    public Article selectById(Object id) {
//        throw new BaseException("Test Global Exception !");
//        return super.selectById(id);
//    }

    /*@Autowired
    private ArticleMapper articleDao;
    public int deleteByPrimaryKey(Object o) {
        return articleDao.deleteByPrimaryKey(o);
    }

    public int delete(Article article) {
        return articleDao.delete(article);
    }

    public int insert(Article article) {
        return articleDao.insert(article);
    }

    public int insertSelective(Article article) {
        return 0;
    }

    public boolean existsWithPrimaryKey(Object o) {
        return false;
    }

    public List<Article> selectAll() {
        return articleDao.selectAll();
    }

    public Article selectByPrimaryKey(Object o) {
        return articleDao.selectByPrimaryKey(o);
    }

    public int selectCount(Article article) {
        return 0;
    }

    public List<Article> select(Article article) {
        return articleDao.select(article);
    }

    public Article selectOne(Article article) {
        return null;
    }

    public int updateByPrimaryKey(Article article) {
        return articleDao.updateByPrimaryKey(article);
    }

    public int updateByPrimaryKeySelective(Article article) {
        return articleDao.updateByPrimaryKeySelective(article);
    }

    public int deleteByExample(Object o) {
        return 0;
    }

    public List<Article> selectByExample(Object o) {
        return null;
    }

    public int selectCountByExample(Object o) {
        return 0;
    }

    public int updateByExample(Article article, Object o) {
        return 0;
    }

    public int updateByExampleSelective(Article article, Object o) {
        return 0;
    }

    public List<Article> selectByExampleAndRowBounds(Object o, RowBounds rowBounds) {
        return null;
    }*/
}
